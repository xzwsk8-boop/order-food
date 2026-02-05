package weixin.order_food.service;


import weixin.order_food.entity.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 根据 openid 获取用户
     */
    User getUserByOpenid(String openid);
    
    /**
     * 根据 openid 创建或更新用户
     */
    User saveOrUpdateUserByOpenid(String openid, String nickname, String avatarUrl, Integer gender);
    
    /**
     * 获取所有未删除用户
     */
    List<User> getAllActiveUsers();
    
    /**
     * 检查用户是否存在
     */
    boolean existsUserByOpenid(String openid);
    
    /**
     * 绑定手机号
     */
    User bindPhone(String openid, String phone);
    
    /**
     * 逻辑删除用户
     */
    boolean deleteUser(Long userId);
}