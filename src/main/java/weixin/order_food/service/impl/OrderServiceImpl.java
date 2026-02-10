package weixin.order_food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import weixin.order_food.entity.Order;
import weixin.order_food.repository.OrderRepository;
import weixin.order_food.service.OrderService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 订单服务实现
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        // 生成订单号: 时间戳 + 随机数
        if (order.getOrderSn() == null) {
            String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String randomStr = String.format("%04d", new Random().nextInt(10000));
            order.setOrderSn(timeStr + randomStr);
        }
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getOrders(String userOpenid, Pageable pageable) {
        if (userOpenid != null && !userOpenid.isEmpty()) {
            // 普通用户：只能看自己的
            return orderRepository.findByUserOpenidOrderByCreatedAtDesc(userOpenid, pageable);
        } else {
            // 管理员/未指定用户：查看所有（实际场景需配合权限校验）
            return orderRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }
}
