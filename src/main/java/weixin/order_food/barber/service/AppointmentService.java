package weixin.order_food.barber.service;

import weixin.order_food.barber.dto.TimeSlot;
import weixin.order_food.barber.entity.Appointment;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    /**
     * 创建预约订单
     */
    Appointment createAppointment(Appointment appointment);

    /**
     * 查询指定用户的预约记录 (包含服务名称等附加信息)
     */
    List<weixin.order_food.barber.dto.AppointmentDTO> getUserAppointmentsWithDetails(Long userId);

    /**
     * 查询理发师某天的日程安排
     */
    List<Appointment> getBarberSchedule(Long barberId, LocalDate date);

    /**
     * 更新预约订单状态
     * @param appointmentId 预约单ID
     * @param status 新状态 (0-待服务, 1-已完成, 2-已取消, 3-爽约)
     */
    Appointment updateAppointmentStatus(Long appointmentId, Integer status);

    /**
     * 获取理发师在指定日期的可用时间段
     */
    List<TimeSlot> getAvailableSlots(Long barberId, LocalDate date);
}
