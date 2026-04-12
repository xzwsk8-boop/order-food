package weixin.order_food.lianzhi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.lianzhi.entity.HomeImage;
import weixin.order_food.lianzhi.service.HomeService;

import java.util.List;

@RestController("lianzhiHomeController")
@RequestMapping("/api/lianzhi/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    /**
     * 获取连枝酒馆首页图片连接列表（如轮播图）
     */
    @GetMapping("/images")
    public ResponseEntity<List<String>> getHomeImages() {
        List<String> imageUrls = homeService.getHomeImageUrls();
        return ResponseEntity.ok(imageUrls);
    }

    /**
     * 获取所有首页图片列表（后台管理用，包含完整实体信息）
     */
    @GetMapping("/images/all")
    public ResponseEntity<List<HomeImage>> getAllHomeImages() {
        List<HomeImage> images = homeService.getAllHomeImages();
        return ResponseEntity.ok(images);
    }

    /**
     * 添加首页图片（后台管理用）
     */
    @PostMapping("/images")
    public ResponseEntity<HomeImage> addHomeImage(@RequestBody HomeImage homeImage) {
        HomeImage created = homeService.addHomeImage(homeImage);
        return ResponseEntity.ok(created);
    }

    /**
     * 修改首页图片信息（后台管理用）
     */
    @PutMapping("/images/{id}")
    public ResponseEntity<HomeImage> updateHomeImage(
            @PathVariable Long id,
            @RequestBody HomeImage homeImage) {
        HomeImage updated = homeService.updateHomeImage(id, homeImage);
        return ResponseEntity.ok(updated);
    }

    /**
     * 删除首页图片（后台管理用）
     */
    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteHomeImage(@PathVariable Long id) {
        homeService.deleteHomeImage(id);
        return ResponseEntity.ok().build();
    }
}