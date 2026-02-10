package weixin.order_food.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Entity
@Table(name = "orders", indexes = {
    @Index(name = "idx_user_openid", columnList = "user_openid"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单编号(业务流水号)
     */
    @Column(name = "order_sn", nullable = false, unique = true, length = 50)
    private String orderSn;

    /**
     * 用户微信唯一标识
     */
    @Column(name = "user_openid", nullable = false, length = 64)
    private String userOpenid;

    /**
     * 关联画作ID
     */
    @Column(name = "art_type_id")
    private Integer artTypeId;

    /**
     * 下单时画作名称
     */
    @Column(name = "art_title", length = 100)
    private String artTitle;

    /**
     * 实付金额
     */
    @Column(name = "actual_price", nullable = false)
    private BigDecimal actualPrice;

    /**
     * 用户上传照片路径(JSON数组存储)
     */
    @Column(name = "user_images", columnDefinition = "json")
    private String userImages;

    /**
     * 用户需求描述
     */
    @Column(name = "user_remark", columnDefinition = "TEXT")
    private String userRemark;

    /**
     * 收货人姓名
     */
    @Column(name = "contact_name", nullable = false, length = 50)
    private String contactName;

    /**
     * 收货人电话
     */
    @Column(name = "contact_phone", nullable = false, length = 20)
    private String contactPhone;

    /**
     * 详细收货地址
     */
    @Column(nullable = false)
    private String address;

    /**
     * 状态: 0待付款, 1待绘画, 2已发货, 3已完成, 4已取消
     */
    @Column(columnDefinition = "TINYINT DEFAULT 0")
    private Integer status = 0;

    /**
     * 快递单号
     */
    @Column(name = "tracking_no", length = 100)
    private String trackingNo;

    /**
     * 创建时间
     */
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    public Order() {
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getUserOpenid() {
        return userOpenid;
    }

    public void setUserOpenid(String userOpenid) {
        this.userOpenid = userOpenid;
    }

    public Integer getArtTypeId() {
        return artTypeId;
    }

    public void setArtTypeId(Integer artTypeId) {
        this.artTypeId = artTypeId;
    }

    public String getArtTitle() {
        return artTitle;
    }

    public void setArtTitle(String artTitle) {
        this.artTitle = artTitle;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getUserImages() {
        return userImages;
    }

    public void setUserImages(String userImages) {
        this.userImages = userImages;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
