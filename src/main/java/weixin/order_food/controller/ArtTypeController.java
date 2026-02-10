package weixin.order_food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.common.Result;
import weixin.order_food.entity.ArtType;
import weixin.order_food.service.ArtTypeService;

import java.util.List;

/**
 * 小程序端画作接口
 */
@RestController
@RequestMapping("/api/art-types")
public class ArtTypeController {

    @Autowired
    private ArtTypeService artTypeService;

    /**
     * 获取上架的画作列表
     */
    @GetMapping("/getList")
    public Result<List<ArtType>> getList() {
        List<ArtType> list = artTypeService.getActiveArtTypes();
        return Result.success(list);
    }

    /**
     * 添加画作
     * (注意：实际场景下这通常是管理员权限，但根据要求提供给小程序端)
     */
    @PostMapping("/add")
    public Result<ArtType> add(@RequestBody ArtType artType) {
        // 可以在这里进行简单的校验，例如 title 不能为空
        if (artType.getTitle() == null || artType.getTitle().trim().isEmpty()) {
            return Result.error("画作名称不能为空");
        }
        
        // 默认设置为上架
        if (artType.getIsOnSale() == null) {
            artType.setIsOnSale(1);
        }

        ArtType created = artTypeService.createArtType(artType);
        return Result.success(created);
    }

    /**
     * 删除画作
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        artTypeService.deleteArtType(id);
        return Result.success();
    }
}
