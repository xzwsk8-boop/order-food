package weixin.order_food.barber.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weixin.order_food.barber.entity.Appointment;
import weixin.order_food.barber.repository.AppointmentRepository;
import weixin.order_food.barber.service.AppointmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

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
    public List<Appointment> getUserAppointments(Long userId) {
        return appointmentRepository.findByUserIdOrderByAppointmentDateDescStartTimeDesc(userId);
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
}
