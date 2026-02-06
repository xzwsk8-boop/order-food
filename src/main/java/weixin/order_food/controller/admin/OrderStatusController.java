package weixin.order_food.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.order_food.entity.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单状态管理控制器
 */
@RestController
@RequestMapping("/admin/order-status")
public class OrderStatusController {

    /**
     * 获取所有订单状态
     * @return 订单状态列表
     */
    @GetMapping
    public List<Map<String, String>> getAllOrderStatus() {
        List<Map<String, String>> statusList = new ArrayList<>();
        for (OrderStatus status : OrderStatus.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("code", status.name());
            map.put("description", status.getDescription());
            statusList.add(map);
        }
        return statusList;
    }
}
