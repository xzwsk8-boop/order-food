package weixin.order_food.service;

import weixin.order_food.entity.Orders;
import weixin.order_food.entity.OrderStatus;

import java.util.List;

/**
 * 订单服务接口
 */
public interface OrdersService {
    
    /**
     * 创建订单
     */
    Orders createOrder(Orders order);
    
    /**
     * 根据ID获取订单
     */
    Orders getOrderById(String orderId);
    
    /**
     * 更新订单状态
     */
    Orders updateOrderStatus(String orderId, OrderStatus status);
    
    /**
     * 获取所有订单
     */
    List<Orders> getAllOrders();
    
    /**
     * 根据桌号获取订单
     */
    List<Orders> getOrdersByTableNum(String tableNum);
    
    /**
     * 根据状态获取订单
     */
    List<Orders> getOrdersByStatus(OrderStatus status);
}
