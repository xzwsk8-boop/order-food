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
}
