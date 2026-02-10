package weixin.order_food.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weixin.order_food.entity.Order;

import java.util.List;

/**
 * 订单 Repository
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * 根据 OpenID 查询用户订单，按创建时间倒序
     */
    Page<Order> findByUserOpenidOrderByCreatedAtDesc(String userOpenid, Pageable pageable);

    /**
     * 管理员查询所有订单，按创建时间倒序
     */
    Page<Order> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
