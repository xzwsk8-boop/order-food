package weixin.order_food.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import weixin.order_food.config.WechatConfig;
import weixin.order_food.exception.WechatException;
import weixin.order_food.service.dto.WechatSessionResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序服务类
 * 实现 code2Session 接口，用于换取用户 openid
 */
@Service
public class WxService {
    
    private static final Logger logger = LoggerFactory.getLogger(WxService.class);
    
    private final WechatConfig wechatConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    // 微信 code2Session 接口地址
    private static final String CODE_TO_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";
    
    @Autowired
    public WxService(WechatConfig wechatConfig, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.wechatConfig = wechatConfig;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    /**
     * 使用 code 换取 openid 和 session_key
     * 
     * @param code 小程序登录时获取的临时登录凭证
     * @return WechatSessionResponse 包含 openid、session_key 等信息
     * @throws WechatException 微信接口调用异常
     */
    public WechatSessionResponse codeToSession(String code) {
        // 参数校验
        if (code == null || code.trim().isEmpty()) {
            throw new WechatException("code 不能为空");
        }
        
        if (wechatConfig.getAppId() == null || wechatConfig.getAppSecret() == null) {
            throw new WechatException("请先配置微信小程序的 appId 和 appSecret");
        }
        
        try {
            // 构建请求参数
            Map<String, String> params = new HashMap<>();
            params.put("appid", wechatConfig.getAppId());
            params.put("secret", wechatConfig.getAppSecret());
            params.put("js_code", code);
            params.put("grant_type", "authorization_code");
            
            logger.info("调用微信 code2Session 接口，code: {}", code);
            
            // 发送 GET 请求
            StringBuilder urlBuilder = new StringBuilder(CODE_TO_SESSION_URL);
            urlBuilder.append("?appid=").append(params.get("appid"))
                     .append("&secret=").append(params.get("secret"))
                     .append("&js_code=").append(params.get("js_code"))
                     .append("&grant_type=").append(params.get("grant_type"));
            
            ResponseEntity<String> response = restTemplate.getForEntity(urlBuilder.toString(), String.class);
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new WechatException("微信接口调用失败，状态码：" + response.getStatusCode());
            }
            
            // 解析响应结果
            String responseBody = response.getBody();
            logger.debug("微信接口响应: {}", responseBody);
            
            WechatSessionResponse sessionResponse = objectMapper.readValue(responseBody, WechatSessionResponse.class);
            
            // 检查是否有错误码
            if (!sessionResponse.isSuccess()) {
                String errorMsg = String.format("微信接口返回错误，错误码：%d，错误信息：%s", 
                    sessionResponse.getErrcode(), sessionResponse.getErrmsg());
                logger.error(errorMsg);
                throw new WechatException(sessionResponse.getErrcode(), errorMsg);
            }
            
            logger.info("成功获取用户 openid: {}", sessionResponse.getOpenid());
            return sessionResponse;
            
        } catch (RestClientException e) {
            logger.error("调用微信接口网络异常", e);
            throw new WechatException("网络请求异常：" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("处理微信接口响应异常", e);
            throw new WechatException("处理响应异常：" + e.getMessage(), e);
        }
    }
    
    /**
     * 仅获取用户 openid（简化方法）
     * 
     * @param code 小程序登录时获取的临时登录凭证
     * @return 用户 openid
     * @throws WechatException 微信接口调用异常
     */
    public String getOpenId(String code) {
        WechatSessionResponse response = codeToSession(code);
        return response.getOpenid();
    }
    
    /**
     * 获取 session key
     * 
     * @param code 小程序登录时获取的临时登录凭证
     * @return session key
     * @throws WechatException 微信接口调用异常
     */
    public String getSessionKey(String code) {
        WechatSessionResponse response = codeToSession(code);
        return response.getSessionKey();
    }
}