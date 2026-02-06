package weixin.order_food.entity;

/**
 * 订单状态枚举
 */
public enum OrderStatus {
    PENDING("待制作"),
    SERVED("已上桌"),
    CANCELLED("已取消");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
