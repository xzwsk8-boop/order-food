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

        // 如果前端没有传价格，我们从服务项目中获取并填充
        if (appointment.getPrice() == null && appointment.getServiceId() != null) {
            Optional<weixin.order_food.barber.entity.ServiceItem> serviceOpt = serviceItemRepository.findById(appointment.getServiceId());
            if (serviceOpt.isPresent()) {
                appointment.setPrice(serviceOpt.get().getPrice());
            }
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
    public List<weixin.order_food.barber.dto.AppointmentDTO> getBarberAppointmentsWithDetails(Long barberId) {
        List<Appointment> appointments = appointmentRepository.findByBarberIdOrderByAppointmentDateDescStartTimeDesc(barberId);
        
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
    @Transactional(rollbackFor = Exception.class)
    public Appointment updateAppointment(Long id, Appointment updateData) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("预约订单不存在，ID: " + id));

        // 检查是否修改了可能引起冲突的字段
        boolean timeOrBarberChanged = false;
        if (updateData.getBarberId() != null && !updateData.getBarberId().equals(existing.getBarberId())) {
            existing.setBarberId(updateData.getBarberId());
            timeOrBarberChanged = true;
        }
        if (updateData.getAppointmentDate() != null && !updateData.getAppointmentDate().equals(existing.getAppointmentDate())) {
            existing.setAppointmentDate(updateData.getAppointmentDate());
            timeOrBarberChanged = true;
        }
        if (updateData.getStartTime() != null && !updateData.getStartTime().equals(existing.getStartTime())) {
            existing.setStartTime(updateData.getStartTime());
            timeOrBarberChanged = true;
        }
        if (updateData.getEndTime() != null && !updateData.getEndTime().equals(existing.getEndTime())) {
            existing.setEndTime(updateData.getEndTime());
            timeOrBarberChanged = true;
        }

        // 如果时间和理发师发生变化，需要重新检测冲突
        if (timeOrBarberChanged) {
            long conflictCount = appointmentRepository.countConflictingAppointmentsExcludingId(
                    existing.getBarberId(),
                    existing.getAppointmentDate(),
                    existing.getStartTime(),
                    existing.getEndTime(),
                    id
            );
            if (conflictCount > 0) {
                throw new RuntimeException("修改后的时间段与该理发师的其他预约冲突，请重新选择时间");
            }
        }

        // 更新服务项目
        if (updateData.getServiceId() != null && !updateData.getServiceId().equals(existing.getServiceId())) {
            existing.setServiceId(updateData.getServiceId());
            // 如果只修改了服务没有显式指定价格，重新根据服务拉取价格
            if (updateData.getPrice() == null) {
                Optional<weixin.order_food.barber.entity.ServiceItem> serviceOpt = serviceItemRepository.findById(existing.getServiceId());
                serviceOpt.ifPresent(serviceItem -> existing.setPrice(serviceItem.getPrice()));
            }
        }

        // 显式更新价格
        if (updateData.getPrice() != null) {
            existing.setPrice(updateData.getPrice());
        }

        // 更新备注
        if (updateData.getRemark() != null) {
            existing.setRemark(updateData.getRemark());
        }
        
        // 更新电话
        if (updateData.getPhone() != null) {
            existing.setPhone(updateData.getPhone());
        }

        return appointmentRepository.save(existing);
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
        
        // 修复时区问题，强制使用东八区（北京时间）获取当前日期和时间
        java.time.ZoneId zoneId = java.time.ZoneId.of("Asia/Shanghai");
        LocalDate today = LocalDate.now(zoneId);
        LocalTime now = LocalTime.now(zoneId);

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
