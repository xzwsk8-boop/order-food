package weixin.order_food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weixin.order_food.exception.WechatException;
import weixin.order_food.service.WxService;
import weixin.order_food.service.dto.WechatSessionResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序登录控制器
 */
@RestController
@RequestMapping("/api/wechat")
public class WechatController {
    
    @Autowired
    private WxService wxService;
    
    /**
     * 小程序登录接口
     * 
     * @param code 小程序登录时获取的临时登录凭证
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String code) {
        try {
            // 调用微信接口获取用户信息
            WechatSessionResponse response = wxService.codeToSession(code);
            
            // 构建返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("openid", response.getOpenid());
            result.put("sessionKey", response.getSessionKey());
            
            // 这里可以添加业务逻辑，比如生成 JWT token、创建或更新用户信息等
            // String token = generateToken(response.getOpenid());
            // result.put("token", token);
            
            return ResponseEntity.ok(result);
            
        } catch (WechatException e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("errorCode", e.getErrorCode());
            errorResult.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResult);
        } catch (Exception e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("message", "系统错误：" + e.getMessage());
            return ResponseEntity.status(500).body(errorResult);
        }
    }
    
    /**
     * 仅获取 openid 的简化接口
     */
    @GetMapping("/openid")
    public ResponseEntity<Map<String, Object>> getOpenId(@RequestParam String code) {
        try {
            String openid = wxService.getOpenId(code);
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("openid", openid);
            return ResponseEntity.ok(result);
        } catch (WechatException e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResult);
        }
    }
}