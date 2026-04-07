package weixin.order_food.barber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.barber.dto.TimeSlot;
import weixin.order_food.barber.entity.Appointment;
import weixin.order_food.barber.service.AppointmentService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/barber/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * 用户提交预约
     */
    @PostMapping("/create")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment created = appointmentService.createAppointment(appointment);
        return ResponseEntity.ok(created);
    }

    /**
     * 获取指定用户的预约记录 (包含服务项目名称)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<weixin.order_food.barber.dto.AppointmentDTO>> getUserAppointments(@PathVariable Long userId) {
        List<weixin.order_food.barber.dto.AppointmentDTO> appointments = appointmentService.getUserAppointmentsWithDetails(userId);
        return ResponseEntity.ok(appointments);
    }

    /**
     * 获取理发师指定日期的预约排班情况（用于前端禁用已被预约的时间段）
     * @param date 格式: yyyy-MM-dd
     */
    @GetMapping("/barber/{barberId}/schedule")
    public ResponseEntity<List<Appointment>> getBarberSchedule(
            @PathVariable Long barberId,
            @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Appointment> schedule = appointmentService.getBarberSchedule(barberId, localDate);
        return ResponseEntity.ok(schedule);
    }

    /**
     * 更改预约状态 (例如：用户取消、理发师核销完成等)
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(
            @PathVariable Long id,
            @RequestParam Integer status) {
        Appointment updated = appointmentService.updateAppointmentStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    /**
     * 修改预约订单信息 (例如：修改预约时间、理发师、服务项目等)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment appointmentDetails) {
        Appointment updated = appointmentService.updateAppointment(id, appointmentDetails);
        return ResponseEntity.ok(updated);
    }

    /**
     * 获取理发师指定日期的可用时间段（前端展示可选时间）
     * @param barberId 理发师ID
     * @param date 日期，格式: yyyy-MM-dd
     */
    @GetMapping("/available-slots")
    public ResponseEntity<List<TimeSlot>> getAvailableSlots(
            @RequestParam Long barberId,
            @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<TimeSlot> availableSlots = appointmentService.getAvailableSlots(barberId, localDate);
        return ResponseEntity.ok(availableSlots);
    }
}
