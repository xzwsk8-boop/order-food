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

@RestController
@RequestMapping("/admin/drinks")
public class DrinksController {

    @Autowired
    private DrinksService drinksService;

    @GetMapping
    public ResponseEntity<Page<Drinks>> getDrinksByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Drinks> drinksPage = drinksService.getDrinksByPage(pageable);
        return ResponseEntity.ok(drinksPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drinks> getDrinkById(@PathVariable Integer id) {
        Drinks drink = drinksService.getDrinkById(id);
        if (drink != null) {
            return ResponseEntity.ok(drink);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Drinks> createDrink(@RequestBody Drinks drink) {
        Drinks savedDrink = drinksService.saveDrink(drink);
        return ResponseEntity.ok(savedDrink);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drinks> updateDrink(@PathVariable Integer id, @RequestBody Drinks drinkDetails) {
        Drinks drink = drinksService.getDrinkById(id);
        if (drink != null) {
            drink.setName(drinkDetails.getName());
            drink.setPrice(drinkDetails.getPrice());
            drink.setDescription(drinkDetails.getDescription());
            drink.setImageUrl(drinkDetails.getImageUrl());
            drink.setCategory(drinkDetails.getCategory());
            drink.setStock(drinkDetails.getStock());
            // createdAt is usually not updated
            
            Drinks updatedDrink = drinksService.saveDrink(drink);
            return ResponseEntity.ok(updatedDrink);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable Integer id) {
        Drinks drink = drinksService.getDrinkById(id);
        if (drink != null) {
            drinksService.deleteDrink(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
