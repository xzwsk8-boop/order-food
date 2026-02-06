package weixin.order_food.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * AI 日志实体类
 */
@Entity
@Table(name = "AI_Logs", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id")
})
public class AiLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    /**
     * 识别出的意图：如 点餐、询价、闲聊
     */
    @Column(length = 50)
    private String intent;

    /**
     * 存储对话数组：[{"role":"user","content":"..."},{"role":"assistant",...}]
     * 使用 String 存储 JSON 字符串
     */
    @Column(name = "chat_history", columnDefinition = "json")
    private String chatHistory;

    /**
     * 创建时间
     */
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // 构造函数
    public AiLogs() {
    }

    public AiLogs(String userId, String intent, String chatHistory) {
        this.userId = userId;
        this.intent = intent;
        this.chatHistory = chatHistory;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(String chatHistory) {
        this.chatHistory = chatHistory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AiLogs{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", intent='" + intent + '\'' +
                ", chatHistory='" + chatHistory + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
