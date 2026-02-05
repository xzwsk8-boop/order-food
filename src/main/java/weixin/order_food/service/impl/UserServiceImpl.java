package weixin.order_food.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import weixin.order_food.entity.User;
import weixin.order_food.mapper.UserMapper;
import weixin.order_food.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Override
    public User getUserByOpenid(String openid) {
        return this.baseMapper.selectByOpenid(openid);
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
            this.updateById(existingUser);
            return existingUser;
        } else {
            // 创建新用户
            User newUser = new User();
            newUser.setOpenid(openid);
            newUser.setNickname(nickname);
            newUser.setAvatarUrl(avatarUrl);
            newUser.setGender(gender);
            this.save(newUser);
            return newUser;
        }
    }
    
    @Override
    public List<User> getAllActiveUsers() {
        return this.baseMapper.selectAllActiveUsers();
    }
    
    @Override
    public boolean existsUserByOpenid(String openid) {
        return this.baseMapper.countByOpenid(openid) > 0;
    }
    
    @Override
    public User bindPhone(String openid, String phone) {
        User user = this.getUserByOpenid(openid);
        if (user != null) {
            user.setPhone(phone);
            user.setUpdateTime(LocalDateTime.now());
            this.updateById(user);
            return user;
        }
        return null;
    }
    
    @Override
    public boolean deleteUser(Long userId) {
        return this.removeById(userId);
    }
}