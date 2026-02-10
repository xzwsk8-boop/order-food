package weixin.order_food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weixin.order_food.entity.ArtType;

import java.util.List;

/**
 * 画作类型 Repository
 */
@Repository
public interface ArtTypeRepository extends JpaRepository<ArtType, Integer> {

    /**
     * 获取所有上架的画作，按排序权重降序，创建时间倒序排列
     */
    List<ArtType> findByIsOnSaleOrderBySortOrderDescCreatedAtDesc(Integer isOnSale);
}
