package weixin.order_food.barber.service;

import weixin.order_food.barber.entity.ServiceItem;
import java.util.List;

public interface ServiceItemService {
    
    /**
     * 获取所有服务项目
     */
    List<ServiceItem> getAllServiceItems();

    /**
     * 根据分类获取服务项目
     * @param category 分类名称，如 HAIRCUT
     */
    List<ServiceItem> getServiceItemsByCategory(String category);

    /**
     * 新增服务项目
     */
    ServiceItem createServiceItem(ServiceItem serviceItem);

    /**
     * 修改服务项目
     */
    ServiceItem updateServiceItem(ServiceItem serviceItem);

    /**
     * 删除服务项目
     */
    void deleteServiceItem(Long id);
}
