package weixin.order_food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.common.Result;
import weixin.order_food.entity.Order;
import weixin.order_food.service.OrderService;

/**
 * 订单接口
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单列表
     * @param openid 用户 OpenID（可选）。如果提供则只返回该用户的订单；如果不提供且有管理员权限，则返回所有。
     * @param page 页码，默认 0
     * @param size 每页大小，默认 10
     */
    @GetMapping("/getList")
    public Result<Page<Order>> getList(
            @RequestParam(required = false) String openid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderService.getOrders(openid, pageable);
        return Result.success(orderPage);
    }

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<Order> create(@RequestBody Order order) {
        // 简单校验
        if (order.getUserOpenid() == null || order.getUserOpenid().isEmpty()) {
            return Result.error("用户 OpenID 不能为空");
        }
        if (order.getArtTypeId() == null) {
            return Result.error("必须选择画作类型");
        }
        if (order.getAddress() == null || order.getAddress().isEmpty()) {
            return Result.error("收货地址不能为空");
        }

        // 设置初始状态
        order.setStatus(0); // 待付款

        Order created = orderService.createOrder(order);
        return Result.success(created);
    }
    
    /**
     * 获取订单详情
     */
    @GetMapping("/{id}")
    public Result<Order> getDetail(@PathVariable Integer id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return Result.success(order);
        } else {
            return Result.error("订单不存在");
        }
    }
}
