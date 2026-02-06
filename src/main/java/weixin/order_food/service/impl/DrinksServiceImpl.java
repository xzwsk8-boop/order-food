package weixin.order_food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import weixin.order_food.entity.Drinks;
import weixin.order_food.repository.DrinksRepository;
import weixin.order_food.service.DrinksService;

import java.util.List;

/**
 * 酒水饮料服务实现类
 */
@Service
public class DrinksServiceImpl implements DrinksService {

    @Autowired
    private DrinksRepository drinksRepository;

    @Override
    public List<Drinks> getAllDrinks() {
        return drinksRepository.findAll();
    }

    @Override
    public Page<Drinks> getDrinksByPage(Pageable pageable) {
        return drinksRepository.findAll(pageable);
    }

    @Override
    public Drinks getDrinkById(Long id) {
        return drinksRepository.findById(id).orElse(null);
    }

    @Override
    public Drinks saveDrink(Drinks drink) {
        return drinksRepository.save(drink);
    }

    @Override
    public void deleteDrink(Long id) {
        drinksRepository.deleteById(id);
    }

    @Override
    public List<Drinks> getDrinksByCategory(String category) {
        return drinksRepository.findByCategory(category);
    }
}
