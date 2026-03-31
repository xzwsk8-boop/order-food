package weixin.order_food.barber.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.order_food.barber.entity.ServiceItem;
import weixin.order_food.barber.repository.ServiceItemRepository;
import weixin.order_food.barber.service.ServiceItemService;

import java.util.List;

@Service
public class ServiceItemServiceImpl implements ServiceItemService {

    @Autowired
    private ServiceItemRepository serviceItemRepository;

    @Override
    public List<ServiceItem> getAllServiceItems() {
        return serviceItemRepository.findAll();
    }

    @Override
    public List<ServiceItem> getServiceItemsByCategory(String category) {
        return serviceItemRepository.findByCategory(category);
    }

    @Override
    public ServiceItem createServiceItem(ServiceItem serviceItem) {
        return serviceItemRepository.save(serviceItem);
    }

    @Override
    public ServiceItem updateServiceItem(ServiceItem serviceItem) {
        if (serviceItem.getId() == null) {
            throw new IllegalArgumentException("更新服务项目时 ID 不能为空");
        }
        if (!serviceItemRepository.existsById(serviceItem.getId())) {
            throw new IllegalArgumentException("服务项目不存在，ID: " + serviceItem.getId());
        }
        return serviceItemRepository.save(serviceItem);
    }

    @Override
    public void deleteServiceItem(Long id) {
        if (!serviceItemRepository.existsById(id)) {
            throw new IllegalArgumentException("服务项目不存在，ID: " + id);
        }
        serviceItemRepository.deleteById(id);
    }
}
