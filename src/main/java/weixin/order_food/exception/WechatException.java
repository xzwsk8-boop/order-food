package weixin.order_food.exception;

/**
 * 微信相关业务异常
 */
public class WechatException extends RuntimeException {
    
    private Integer errorCode;
    
    public WechatException(String message) {
        super(message);
    }
    
    public WechatException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public WechatException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public Integer getErrorCode() {
        return errorCode;
    }
}