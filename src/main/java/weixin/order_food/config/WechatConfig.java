package weixin.order_food.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置类
 */
@Component
@ConfigurationProperties(prefix = "wechat.miniapp")
public class WechatConfig {
    
    /**
     * 小程序 appId
     */
    private String appId;
    
    /**
     * 小程序 appSecret
     */
    private String appSecret;
    
    public String getAppId() {
        return appId;
    }
    
    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    public String getAppSecret() {
        return appSecret;
    }
    
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}