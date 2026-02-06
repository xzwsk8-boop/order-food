package weixin.order_food.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.entity.Drinks;
import weixin.order_food.service.DrinksService;

/**
 * 酒水饮料管理控制器
 */
@RestController
@RequestMapping("/admin/drinks")
public class DrinksController {

    @Autowired
    private DrinksService drinksService;

    /**
     * 分页获取酒水列表
     * @param page 页码，从0开始
     * @param size 每页数量
     * @return 分页结果
     */
    @GetMapping
    public ResponseEntity<Page<Drinks>> getDrinksByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Drinks> drinksPage = drinksService.getDrinksByPage(pageable);
        return ResponseEntity.ok(drinksPage);
    }

    /**
     * 根据ID获取酒水详情
     * @param id 酒水ID
     * @return 酒水详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Drinks> getDrinkById(@PathVariable Long id) {
        Drinks drink = drinksService.getDrinkById(id);
        if (drink != null) {
            return ResponseEntity.ok(drink);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 添加新酒水
     * @param drink 酒水信息
     * @return 创建后的酒水信息
     */
    @PostMapping
    public ResponseEntity<Drinks> createDrink(@RequestBody Drinks drink) {
        Drinks newDrink = drinksService.saveDrink(drink);
        return ResponseEntity.ok(newDrink);
    }

    /**
     * 更新酒水信息
     * @param id 酒水ID
     * @param drinkDetails 更新的酒水详情
     * @return 更新后的酒水信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Drinks> updateDrink(@PathVariable Long id, @RequestBody Drinks drinkDetails) {
        Drinks existingDrink = drinksService.getDrinkById(id);
        if (existingDrink != null) {
            existingDrink.setName(drinkDetails.getName());
            existingDrink.setPrice(drinkDetails.getPrice());
            existingDrink.setCategory(drinkDetails.getCategory());
            existingDrink.setTags(drinkDetails.getTags());
            existingDrink.setStock(drinkDetails.getStock());
            existingDrink.setDescription(drinkDetails.getDescription());
            
            Drinks updatedDrink = drinksService.saveDrink(existingDrink);
            return ResponseEntity.ok(updatedDrink);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 删除酒水
     * @param id 酒水ID
     * @return 无内容
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable Long id) {
        Drinks existingDrink = drinksService.getDrinkById(id);
        if (existingDrink != null) {
            drinksService.deleteDrink(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
