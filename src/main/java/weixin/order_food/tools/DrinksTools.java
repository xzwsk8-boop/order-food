package weixin.order_food.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weixin.order_food.entity.Drinks;
import weixin.order_food.repository.DrinksRepository;

import java.util.List;

/**
 * 饮品相关工具
 */
@Component
public class DrinksTools {

    @Autowired
    private DrinksRepository drinksRepository;

    /**
     * 获取酒单工具：从 MySQL 读取 stock > 0 的数据
     */
    @Tool(description = "获取当前有货的酒水列表")
    public List<Drinks> getAvailableDrinks() {
        return drinksRepository.findByStockGreaterThan(0);
    }
}
