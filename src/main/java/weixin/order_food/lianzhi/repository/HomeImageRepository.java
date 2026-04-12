package weixin.order_food.lianzhi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weixin.order_food.lianzhi.entity.HomeImage;

import java.util.List;

@Repository
public interface HomeImageRepository extends JpaRepository<HomeImage, Long> {

    // 查找所有启用的轮播图，并按 sortOrder 升序排列（数字越小越靠前）
    List<HomeImage> findByIsActiveOrderBySortOrderAsc(Integer isActive);
}