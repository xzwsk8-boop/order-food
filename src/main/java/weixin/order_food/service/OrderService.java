package weixin.order_food.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import weixin.order_food.entity.Order;

/**
 * 订单服务接口
 */
public interface OrderService {

    /**
     * 创建订单
     */
    Order createOrder(Order order);

    /**
     * 分页查询订单
     * @param userOpenid 用户 OpenID，如果为 null 则查询所有（管理员权限）
     * @param pageable 分页参数
     */
    Page<Order> getOrders(String userOpenid, Pageable pageable);

    /**
     * 根据 ID 获取订单
     */
    Order getOrderById(Integer id);
}
