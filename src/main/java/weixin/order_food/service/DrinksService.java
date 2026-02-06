package weixin.order_food.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import weixin.order_food.entity.Drinks;

import java.util.List;

public interface DrinksService {

    Drinks saveDrink(Drinks drink);

    void deleteDrink(Integer id);

    Drinks getDrinkById(Integer id);

    List<Drinks> getAllDrinks();

    /**
     * 分页查询
     */
    Page<Drinks> getDrinksByPage(Pageable pageable);
}
