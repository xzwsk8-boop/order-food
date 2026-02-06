package weixin.order_food.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.entity.Orders;
import weixin.order_food.entity.OrderStatus;
import weixin.order_food.service.OrdersService;

/**
 * 订单管理控制器
 */
@RestController
@RequestMapping("/admin/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 分页获取订单列表
     * @param page 页码，从0开始
     * @param size 每页数量
     * @return 分页结果
     */
    @GetMapping
    public ResponseEntity<Page<Orders>> getOrdersByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Orders> ordersPage = ordersService.getOrdersByPage(pageable);
        return ResponseEntity.ok(ordersPage);
    }

    /**
     * 根据ID获取订单详情
     * @param orderId 订单ID
     * @return 订单详情
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable String orderId) {
        Orders order = ordersService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 状态（PENDING, SERVED, CANCELLED）
     * @return 更新后的订单信息
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
        Orders updatedOrder = ordersService.updateOrderStatus(orderId, status);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
