package weixin.order_food.barber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.common.Result;
import weixin.order_food.barber.entity.Barber;
import weixin.order_food.barber.service.BarberService;

import java.util.List;

@RestController
@RequestMapping("/api/barbers")
public class BarberController {

    @Autowired
    private BarberService barberService;

    /**
     * 获取所有在职理发师列表（小程序端用）
     */
    @GetMapping("/active")
    public Result<List<Barber>> getActiveBarbers() {
        return Result.success(barberService.getActiveBarbers());
    }

    /**
     * 获取所有理发师列表（管理员端用）
     */
    @GetMapping("/list")
    public Result<List<Barber>> getAllBarbers() {
        return Result.success(barberService.getAllBarbers());
    }

    /**
     * 获取理发师详情
     */
    @GetMapping("/{id}")
    public Result<Barber> getBarberById(@PathVariable Long id) {
        Barber barber = barberService.getBarberById(id);
        if (barber != null) {
            return Result.success(barber);
        }
        return Result.error("理发师不存在");
    }

    /**
     * 添加理发师（管理员端用）
     */
    @PostMapping("/add")
    public Result<Barber> addBarber(@RequestBody Barber barber) {
        if (barber.getName() == null || barber.getName().isEmpty()) {
            return Result.error("理发师姓名不能为空");
        }
        if (barber.getAvatar() == null || barber.getAvatar().isEmpty()) {
            return Result.error("理发师照片不能为空");
        }
        return Result.success(barberService.addBarber(barber));
    }

    /**
     * 更新理发师信息（管理员端用）
     */
    @PutMapping("/update")
    public Result<Barber> updateBarber(@RequestBody Barber barber) {
        if (barber.getId() == null) {
            return Result.error("理发师ID不能为空");
        }
        try {
            return Result.success(barberService.updateBarber(barber));
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除理发师（管理员端用）
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteBarber(@PathVariable Long id) {
        barberService.deleteBarber(id);
        return Result.success();
    }
}