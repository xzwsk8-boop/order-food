package weixin.order_food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import weixin.order_food.entity.Orders;
import weixin.order_food.entity.OrderStatus;
import weixin.order_food.repository.OrdersRepository;
import weixin.order_food.service.OrdersService;

import java.util.List;
import java.util.UUID;

/**
 * 订单服务实现类
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Orders createOrder(Orders order) {
        if (order.getOrderId() == null || order.getOrderId().isEmpty()) {
            order.setOrderId(UUID.randomUUID().toString());
        }
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PENDING.getDescription());
        }
        return ordersRepository.save(order);
    }

    @Override
    public Orders getOrderById(String orderId) {
        return ordersRepository.findById(orderId).orElse(null);
    }

    @Override
    public Orders updateOrderStatus(String orderId, OrderStatus status) {
        Orders order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(status.getDescription());
            return ordersRepository.save(order);
        }
        return null;
    }

    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public Page<Orders> getOrdersByPage(Pageable pageable) {
        return ordersRepository.findAll(pageable);
    }

    @Override
    public List<Orders> getOrdersByTableNum(String tableNum) {
        return ordersRepository.findByTableNum(tableNum);
    }

    @Override
    public List<Orders> getOrdersByStatus(OrderStatus status) {
        return ordersRepository.findByStatus(status.getDescription());
    }
}
