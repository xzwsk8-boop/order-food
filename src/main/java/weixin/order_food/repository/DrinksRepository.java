package weixin.order_food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weixin.order_food.entity.Drinks;

import java.util.List;

/**
 * 酒水饮料 Repository
 */
@Repository
public interface DrinksRepository extends JpaRepository<Drinks, Long> {
    
    /**
     * 根据分类查询
     */
    List<Drinks> findByCategory(String category);
}
