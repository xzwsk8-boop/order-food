package weixin.order_food.barber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weixin.order_food.barber.entity.Barber;

import java.util.List;
import java.util.Optional;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
    List<Barber> findByIsActive(Integer isActive);
    
    Optional<Barber> findByOpenid(String openid);
}