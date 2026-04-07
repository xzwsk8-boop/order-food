package weixin.order_food.barber.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weixin.order_food.barber.dto.TimeSlot;
import weixin.order_food.barber.entity.Appointment;
import weixin.order_food.barber.repository.AppointmentRepository;
import weixin.order_food.barber.service.AppointmentService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private weixin.order_food.barber.repository.ServiceItemRepository serviceItemRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Appointment createAppointment(Appointment appointment) {
        // 原子性检查：时间冲突检测
        long conflictCount = appointmentRepository.countConflictingAppointments(
                appointment.getBarberId(),
                appointment.getAppointmentDate(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );

        if (conflictCount > 0) {
            throw new RuntimeException("该理发师在选定时间段内已有预约，请选择其他时间");
        }

        // 保存预约
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<weixin.order_food.barber.dto.AppointmentDTO> getUserAppointmentsWithDetails(Long userId) {
        List<Appointment> appointments = appointmentRepository.findByUserIdOrderByAppointmentDateDescStartTimeDesc(userId);
        
        List<weixin.order_food.barber.dto.AppointmentDTO> dtoList = new ArrayList<>();
        for (Appointment appt : appointments) {
            String serviceName = "未知服务";
            if (appt.getServiceId() != null) {
                Optional<weixin.order_food.barber.entity.ServiceItem> serviceOpt = serviceItemRepository.findById(appt.getServiceId());
                if (serviceOpt.isPresent()) {
                    serviceName = serviceOpt.get().getName();
                }
            }
            dtoList.add(new weixin.order_food.barber.dto.AppointmentDTO(appt, serviceName));
        }
        return dtoList;
    }

    @Override
    public List<Appointment> getBarberSchedule(Long barberId, LocalDate date) {
        // 返回理发师当天所有的预约情况（用于小程序端展示不可选的时间段）
        return appointmentRepository.findByBarberIdAndAppointmentDateOrderByStartTimeAsc(barberId, date);
    }

    @Override
    public Appointment updateAppointmentStatus(Long appointmentId, Integer status) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }
        throw new RuntimeException("预约订单不存在，ID: " + appointmentId);
    }

    @Override
    public List<TimeSlot> getAvailableSlots(Long barberId, LocalDate date) {
        // 假设理发师工作时间为 10:00 到 22:00
        LocalTime workStart = LocalTime.of(10, 0);
        LocalTime workEnd = LocalTime.of(22, 0);
        int slotMinutes = 30; // 每 30 分钟切分一个时段

        // 获取理发师当天所有的预约安排（包含各种状态）
        List<Appointment> existingAppointments = appointmentRepository
                .findByBarberIdAndAppointmentDateOrderByStartTimeAsc(barberId, date);

        List<TimeSlot> availableSlots = new ArrayList<>();
        LocalTime currentSlotStart = workStart;
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        while (!currentSlotStart.plusMinutes(slotMinutes).isAfter(workEnd)) {
            LocalTime currentSlotEnd = currentSlotStart.plusMinutes(slotMinutes);

            // 如果查询的是今天，并且该时段已经过去，则跳过
            // 使用 isBefore 判断，确保只返回当前时间之后的时间段
            if (date.equals(today) && currentSlotStart.isBefore(now)) {
                currentSlotStart = currentSlotEnd;
                continue;
            }

            boolean isOccupied = false;

            // 检查当前时段是否与已有预约冲突
            for (Appointment appt : existingAppointments) {
                // 仅当状态为 0-待服务 或 1-已完成 时，认为时段被占用
                if (appt.getStatus() != null && (appt.getStatus() == 0 || appt.getStatus() == 1)) {
                    // 时间冲突判断：新时段开始时间 < 预约结束时间 且 新时段结束时间 > 预约开始时间
                    if (currentSlotStart.isBefore(appt.getEndTime()) && currentSlotEnd.isAfter(appt.getStartTime())) {
                        isOccupied = true;
                        break;
                    }
                }
            }

            if (!isOccupied) {
                availableSlots.add(new TimeSlot(currentSlotStart, currentSlotEnd));
            }

            currentSlotStart = currentSlotEnd;
        }

        return availableSlots;
    }
}
