package weixin.order_food.barber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import weixin.order_food.barber.dto.LoginRequest;
import weixin.order_food.barber.dto.LoginResponse;
import weixin.order_food.barber.entity.User;
import weixin.order_food.barber.service.UserService;
import weixin.order_food.config.WechatConfig;
import weixin.order_food.utils.JwtUtils;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private weixin.order_food.barber.repository.BarberRepository barberRepository;

    // 为了简单起见，这里直接实例化 RestTemplate，在实际项目中建议作为 Bean 注入
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/wx-login")
    public ResponseEntity<?> wxLogin(@RequestBody LoginRequest loginRequest) {
        String code = loginRequest.getCode();
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().body("code 不能为空");
        }

        // 构建请求微信接口的 URL
        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                wechatConfig.getAppId(),
                wechatConfig.getAppSecret(),
                code
        );

        try {
            // 调用微信接口获取 openid 和 session_key
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null || response.containsKey("errcode") && (Integer) response.get("errcode") != 0) {
                return ResponseEntity.status(500).body("微信登录失败: " + response);
            }

            String openid = (String) response.get("openid");
            if (openid == null) {
                return ResponseEntity.status(500).body("获取 openid 失败");
            }

            // 登录或注册用户
            User user = userService.loginOrRegister(openid);
            
            // 判断该用户是否是理发师(店员)
            weixin.order_food.barber.entity.Barber barber = barberRepository.findByOpenid(openid).orElse(null);
            boolean isBarber = (barber != null);
            Long barberId = isBarber ? barber.getId() : null;

            // 生成 JWT
            String token = jwtUtils.generateToken(user.getId(), openid);

            // 返回 token 和 userId 给前端
            return ResponseEntity.ok(new LoginResponse(token, user.getId(), isBarber, barberId));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("服务器内部错误: " + e.getMessage());
        }
    }
}
