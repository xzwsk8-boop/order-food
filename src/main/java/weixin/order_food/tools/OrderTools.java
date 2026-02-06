package weixin.order_food.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weixin.order_food.entity.Drinks;
import weixin.order_food.entity.Orders;
import weixin.order_food.repository.DrinksRepository;
import weixin.order_food.repository.OrdersRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * 订单相关工具
 */
@Component
public class OrderTools {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private DrinksRepository drinksRepository;

    /**
     * 下单工具
     * @param tableNum 桌号
     * @param drinkName 饮品名称
     * @param quantity 数量 (目前简单实现，只计算总价，不存储明细)
     */
    @Tool(description = "用户下单工具，需要提供桌号和饮品名称")
    public String placeOrder(String tableNum, String drinkName, int quantity) {
        List<Drinks> drinks = drinksRepository.findByName(drinkName);
        if (drinks.isEmpty()) {
            return "下单失败：找不到饮品 " + drinkName;
        }

        Drinks drink = drinks.get(0);
        if (drink.getStock() < quantity) {
            return "下单失败：" + drinkName + " 库存不足，剩余 " + drink.getStock();
        }

        // 扣减库存 (简单实现，未加锁)
        drink.setStock(drink.getStock() - quantity);
        drinksRepository.save(drink);

        // 创建订单
        Orders order = new Orders();
        order.setOrderId(UUID.randomUUID().toString());
        order.setTableNum(tableNum);
        order.setTotalPrice(drink.getPrice().multiply(new BigDecimal(quantity)));
        order.setStatus("待制作");
        
        ordersRepository.save(order);

        return "下单成功！订单号：" + order.getOrderId() + "，已通知吧台制作。";
    }
}
