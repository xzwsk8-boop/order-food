package weixin.order_food.barber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.common.Result;
import weixin.order_food.barber.entity.User;
import weixin.order_food.barber.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 微信登录/注册接口
     * 前端获取到微信openid后调用此接口
     */
    @PostMapping("/login")
    public Result<User> login(@RequestParam String openid) {
        if (openid == null || openid.trim().isEmpty()) {
            return Result.error("Openid不能为空");
        }
        User user = userService.loginOrRegister(openid);
        return Result.success(user);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestParam String openid) {
        User user = userService.getUserByOpenid(openid);
        if (user != null) {
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }

    /**
     * 更新用户信息（如授权获取头像昵称后）
     */
    @PutMapping("/update")
    public Result<User> updateUserInfo(@RequestBody User user) {
        if (user.getOpenid() == null) {
            return Result.error("Openid不能为空");
        }
        try {
            User updatedUser = userService.updateUserInfo(user);
            return Result.success(updatedUser);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}