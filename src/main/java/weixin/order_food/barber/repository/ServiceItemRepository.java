package weixin.order_food.barber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weixin.order_food.barber.entity.ServiceItem;

import java.util.List;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    
    // 按分类查询服务项目
    List<ServiceItem> findByCategory(String category);
}
