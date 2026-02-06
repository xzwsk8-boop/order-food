package weixin.order_food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weixin.order_food.entity.Orders;

import java.util.List;

/**
 * 订单 Repository
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {
    
    /**
     * 根据桌号查询订单
     */
    List<Orders> findByTableNum(String tableNum);

    /**
     * 根据状态查询订单
     */
    List<Orders> findByStatus(String status);
}
