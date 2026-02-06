package weixin.order_food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weixin.order_food.entity.Drinks;

import java.util.List;

/**
 * 饮品 Repository
 */
@Repository
public interface DrinksRepository extends JpaRepository<Drinks, Integer> {

    /**
     * 根据分类查找饮品
     */
    List<Drinks> findByCategory(String category);

    /**
     * 根据库存状态查找饮品
     */
    List<Drinks> findByStockStatus(Integer stockStatus);

    /**
     * 查找库存大于指定数量的饮品
     */
    List<Drinks> findByStockGreaterThan(Integer stock);

    /**
     * 根据名称查找饮品
     */
    List<Drinks> findByName(String name);
}
