package weixin.order_food.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Entity
@Table(name = "Orders")
public class Orders {

    /**
     * 订单ID
     */
    @Id
    @Column(name = "order_id", length = 50)
    private String orderId;

    /**
     * 桌号
     */
    @Column(name = "table_num", nullable = false, length = 20)
    private String tableNum;

    /**
     * 总价格
     */
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    /**
     * 状态：待制作, 已上桌, 已取消
     * 对应数据库 ENUM('待制作', '已上桌', '已取消')
     */
    @Column(name = "status", columnDefinition = "ENUM('待制作', '已上桌', '已取消') DEFAULT '待制作'")
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // 构造函数
    public Orders() {
    }

    public Orders(String orderId, String tableNum, BigDecimal totalPrice, String status) {
        this.orderId = orderId;
        this.tableNum = tableNum;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // Getter 和 Setter 方法
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", tableNum='" + tableNum + '\'' +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
