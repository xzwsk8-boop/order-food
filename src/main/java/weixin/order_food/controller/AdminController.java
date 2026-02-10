package weixin.order_food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.common.Result;
import weixin.order_food.entity.Admin;
import weixin.order_food.service.AdminService;

import java.util.List;
import java.util.Map;

/**
 * 管理员权限接口
 */
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 检查当前用户是否为管理员
     */
    @GetMapping("/check")
    public Result<Map<String, Boolean>> checkAdmin(@RequestParam String openid) {
        boolean isAdmin = adminService.isAdmin(openid);
        return Result.success(Map.of("isAdmin", isAdmin));
    }

    /**
     * 获取管理员列表
     */
    @GetMapping("/list")
    public Result<List<Admin>> getList() {
        List<Admin> list = adminService.getAllAdmins();
        return Result.success(list);
    }

    /**
     * 添加管理员
     */
    @PostMapping("/add")
    public Result<Admin> add(@RequestBody Admin admin) {
        if (admin.getOpenid() == null || admin.getOpenid().trim().isEmpty()) {
            return Result.error("OpenID 不能为空");
        }
        try {
            Admin created = adminService.addAdmin(admin);
            return Result.success(created);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除管理员
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        adminService.deleteAdmin(id);
        return Result.success();
    }
}
