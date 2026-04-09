package weixin.order_food.barber.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_item")
public class ServiceItem {

    @Id
    @GeneratedValue(generator = "sixteen-digit-id")
    @org.hibernate.annotations.GenericGenerator(name = "sixteen-digit-id", strategy = "weixin.order_food.barber.util.SixteenDigitIdGenerator")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "duration", nullable = false, columnDefinition = "INT DEFAULT 30")
    private Integer duration = 30;

    @Column(name = "category", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'HAIRCUT'")
    private String category = "HAIRCUT";

    @Column(name = "icon_url", length = 255)
    private String iconUrl;

    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    public ServiceItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
