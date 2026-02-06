package weixin.order_food.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import weixin.order_food.entity.Drinks;

import java.util.List;

/**
 * 酒水饮料服务接口
 */
public interface DrinksService {
    
    /**
     * 获取所有酒水
     */
    List<Drinks> getAllDrinks();
    
    /**
     * 分页获取酒水
     */
    Page<Drinks> getDrinksByPage(Pageable pageable);
    
    /**
     * 根据ID获取酒水
     */
    Drinks getDrinkById(Long id);
    
    /**
     * 保存或更新酒水
     */
    Drinks saveDrink(Drinks drink);
    
    /**
     * 删除酒水
     */
    void deleteDrink(Long id);
    
    /**
     * 根据分类获取酒水
     */
    List<Drinks> getDrinksByCategory(String category);
}
