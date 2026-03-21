package weixin.order_food.barber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import weixin.order_food.barber.entity.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // 查询指定用户的预约记录，按日期和时间降序排列
    List<Appointment> findByUserIdOrderByAppointmentDateDescStartTimeDesc(Long userId);

    // 查询理发师某天的预约安排，按时间升序排列，用于排班展示
    List<Appointment> findByBarberIdAndAppointmentDateOrderByStartTimeAsc(Long barberId, LocalDate appointmentDate);

    // 查询理发师某天处于特定状态（如待服务）的预约
    List<Appointment> findByBarberIdAndAppointmentDateAndStatusOrderByStartTimeAsc(Long barberId, LocalDate appointmentDate, Integer status);

    // 查询指定理发师在指定日期和时间段内的有效预约数（状态为 0-待服务 或 1-已完成，排除取消等状态），用于冲突检测
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.barberId = :barberId AND a.appointmentDate = :appointmentDate AND a.startTime < :endTime AND a.endTime > :startTime AND a.status IN (0, 1)")
    long countConflictingAppointments(@Param("barberId") Long barberId, 
                                      @Param("appointmentDate") LocalDate appointmentDate, 
                                      @Param("startTime") LocalTime startTime, 
                                      @Param("endTime") LocalTime endTime);
}
