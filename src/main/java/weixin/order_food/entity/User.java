package weixin.order_food.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户实体类
 */
@TableName("users")
public class User extends BaseEntity {
    
    /**
     * 微信 openid
     */
    @TableField("openid")
    private String openid;
    
    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;
    
    /**
     * 用户头像
     */
    @TableField("avatar_url")
    private String avatarUrl;
    
    /**
     * 性别 0-未知 1-男性 2-女性
     */
    @TableField("gender")
    private Integer gender;
    
    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;
    
    // 构造函数
    public User() {}
    
    public User(String openid) {
        this.openid = openid;
    }
    
    // Getter 和 Setter 方法
    public String getOpenid() {
        return openid;
    }
    
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }
    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    
    public Integer getGender() {
        return gender;
    }
    
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime=" + getCreateTime() +
                ", updateTime=" + getUpdateTime() +
                ", deleted=" + getDeleted() +
                '}';
    }
}