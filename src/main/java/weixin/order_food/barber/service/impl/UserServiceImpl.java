package weixin.order_food.barber.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.order_food.barber.entity.User;
import weixin.order_food.barber.repository.UserRepository;
import weixin.order_food.barber.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loginOrRegister(String openid) {
        return userRepository.findByOpenid(openid).orElseGet(() -> {
            User newUser = new User();
            newUser.setOpenid(openid);
            // 初始默认昵称可以基于openid截取或者设为默认值
            newUser.setNickname("微信用户"); 
            return userRepository.save(newUser);
        });
    }

    @Override
    public User updateUserInfo(User user) {
        return userRepository.findByOpenid(user.getOpenid()).map(existingUser -> {
            if (user.getNickname() != null) existingUser.setNickname(user.getNickname());
            if (user.getAvatarUrl() != null) existingUser.setAvatarUrl(user.getAvatarUrl());
            if (user.getPhone() != null) existingUser.setPhone(user.getPhone());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByOpenid(String openid) {
        return userRepository.findByOpenid(openid).orElse(null);
    }
}