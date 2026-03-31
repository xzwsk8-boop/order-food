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

    /**
     * 新增服务项目
     */
    @PostMapping("/add")
    public ResponseEntity<?> createServiceItem(@RequestBody ServiceItem serviceItem) {
        try {
            ServiceItem createdItem = serviceItemService.createServiceItem(serviceItem);
            return ResponseEntity.ok(createdItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 修改服务项目
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateServiceItem(@RequestBody ServiceItem serviceItem) {
        try {
            ServiceItem updatedItem = serviceItemService.updateServiceItem(serviceItem);
            return ResponseEntity.ok(updatedItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("服务器内部错误: " + e.getMessage());
        }
    }

    /**
     * 删除服务项目
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteServiceItem(@PathVariable Long id) {
        try {
            serviceItemService.deleteServiceItem(id);
            return ResponseEntity.ok("删除成功");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("服务器内部错误: " + e.getMessage());
        }
    }
}
