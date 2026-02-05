package weixin.order_food.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信 code2Session 接口响应结果
 */
public class WechatSessionResponse {
    
    /**
     * 用户唯一标识
     */
    @JsonProperty("openid")
    private String openid;
    
    /**
     * 会话密钥
     */
    @JsonProperty("session_key")
    private String sessionKey;
    
    /**
     * 用户在开放平台的唯一标识符
     */
    @JsonProperty("unionid")
    private String unionid;
    
    /**
     * 错误码
     */
    @JsonProperty("errcode")
    private Integer errcode;
    
    /**
     * 错误信息
     */
    @JsonProperty("errmsg")
    private String errmsg;
    
    // 构造函数
    public WechatSessionResponse() {}
    
    // Getter 和 Setter 方法
    public String getOpenid() {
        return openid;
    }
    
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    
    public String getSessionKey() {
        return sessionKey;
    }
    
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
    
    public String getUnionid() {
        return unionid;
    }
    
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
    
    public Integer getErrcode() {
        return errcode;
    }
    
    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }
    
    public String getErrmsg() {
        return errmsg;
    }
    
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    
    /**
     * 判断是否请求成功
     */
    public boolean isSuccess() {
        return errcode == null || errcode == 0;
    }
    
    @Override
    public String toString() {
        return "WechatSessionResponse{" +
                "openid='" + openid + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", unionid='" + unionid + '\'' +
                ", errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}