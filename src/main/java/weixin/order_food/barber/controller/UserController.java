package weixin.order_food.barber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import weixin.order_food.common.Result;
import weixin.order_food.barber.entity.User;
import weixin.order_food.barber.service.UserService;
import weixin.order_food.config.WechatConfig;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private weixin.order_food.utils.JwtUtils jwtUtils;

    @Autowired
    private weixin.order_food.barber.repository.BarberRepository barberRepository;

    /**
     * 通过微信 code 换取 openid 接口
     */
    @GetMapping("/getOpenid")
    public Result<weixin.order_food.barber.dto.LoginResponse> getOpenidByCode(@RequestParam String code) {
        if (code == null || code.trim().isEmpty()) {
            return Result.error("Code不能为空");
        }
        
        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                wechatConfig.getAppId(),
                wechatConfig.getAppSecret(),
                code
        );

        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response == null || (response.containsKey("errcode") && (Integer) response.get("errcode") != 0)) {
                return Result.error("微信接口调用失败: " + response.get("errmsg"));
            }
            
            String openid = (String) response.get("openid");
            if (openid == null) {
                return Result.error("获取 openid 失败");
            }
            
            // 自动注册或登录普通用户
            User user = userService.loginOrRegister(openid);
            
            // 判断该用户是否是理发师(店员)
            weixin.order_food.barber.entity.Barber barber = barberRepository.findByOpenid(openid).orElse(null);
            boolean isBarber = (barber != null);
            Long barberId = isBarber ? barber.getId() : null;

            // 生成 JWT
            String token = jwtUtils.generateToken(user.getId(), openid);

            // 封装返回包含 token, userId, isBarber 和 barberId 的对象
            weixin.order_food.barber.dto.LoginResponse loginResponse = new weixin.order_food.barber.dto.LoginResponse(token, user.getId(), isBarber, barberId);
            
            return Result.success(loginResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器内部错误: " + e.getMessage());
        }
    }

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