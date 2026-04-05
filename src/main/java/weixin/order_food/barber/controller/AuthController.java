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
            // 先将微信返回的结果作为 String 接收，以避免 RestTemplate 对 content-type text/plain 的解析错误
            String responseStr = restTemplate.getForObject(url, String.class);
            
            // 手动使用 Jackson 解析 JSON 字符串
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String, Object> response = mapper.readValue(responseStr, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});

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
