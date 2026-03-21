package weixin.order_food.barber.service;

import weixin.order_food.barber.entity.User;

public interface UserService {
    /**
     * 微信登录/注册
     * 如果用户存在则返回信息，不存在则创建新用户
     */
    User loginOrRegister(String openid);
    
    /**
     * 更新用户信息
     */
    User updateUserInfo(User user);
    
    /**
     * 根据openid获取用户信息
     */
    User getUserByOpenid(String openid);
}