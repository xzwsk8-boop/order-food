package weixin.order_food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.order_food.entity.User;
import weixin.order_food.repository.UserRepository;
import weixin.order_food.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User getUserByOpenid(String openid) {
        return userRepository.findByOpenid(openid).orElse(null);
    }
    
    @Override
    public User saveOrUpdateUserByOpenid(String openid, String nickname, String avatarUrl, Integer gender) {
        User existingUser = this.getUserByOpenid(openid);
        
        if (existingUser != null) {
            // 更新用户信息
            existingUser.setNickname(nickname);
            existingUser.setAvatarUrl(avatarUrl);
            existingUser.setGender(gender);
            existingUser.setUpdateTime(LocalDateTime.now());
            return userRepository.save(existingUser);
        } else {
            // 创建新用户
            User newUser = new User();
            newUser.setOpenid(openid);
            newUser.setNickname(nickname);
            newUser.setAvatarUrl(avatarUrl);
            newUser.setGender(gender);
            return userRepository.save(newUser);
        }
    }
    
    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findAllActiveUsers();
    }
    
    @Override
    public boolean existsUserByOpenid(String openid) {
        return userRepository.countByOpenid(openid) > 0;
    }
    
    @Override
    public User bindPhone(String openid, String phone) {
        User user = this.getUserByOpenid(openid);
        if (user != null) {
            user.setPhone(phone);
            user.setUpdateTime(LocalDateTime.now());
            return userRepository.save(user);
        }
        return null;
    }
    
    @Override
    public boolean deleteUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setDeleted(1);
            user.setUpdateTime(LocalDateTime.now());
            userRepository.save(user);
            return true;
        }
        return false;
    }
}