package weixin.order_food.barber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.barber.entity.ServiceItem;
import weixin.order_food.barber.service.ServiceItemService;

import java.util.List;

@RestController
@RequestMapping("/api/barber/services")
public class ServiceItemController {

    @Autowired
    private ServiceItemService serviceItemService;

    /**
     * 获取所有服务项目
     */
    @GetMapping("/list")
    public ResponseEntity<List<ServiceItem>> getAllServices() {
        List<ServiceItem> services = serviceItemService.getAllServiceItems();
        return ResponseEntity.ok(services);
    }

    /**
     * 根据分类获取服务项目
     * @param category 分类，如：HAIRCUT
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ServiceItem>> getServicesByCategory(@PathVariable String category) {
        List<ServiceItem> services = serviceItemService.getServiceItemsByCategory(category);
        return ResponseEntity.ok(services);
    }
}
