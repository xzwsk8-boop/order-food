package weixin.order_food.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import weixin.order_food.entity.User;

import java.util.List;

/**
 * 用户 Mapper 接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 根据 openid 查询用户
     */
    @Select("SELECT * FROM users WHERE openid = #{openid} AND deleted = 0")
    User selectByOpenid(@Param("openid") String openid);
    
    /**
     * 根据手机号查询用户
     */
    @Select("SELECT * FROM users WHERE phone = #{phone} AND deleted = 0")
    User selectByPhone(@Param("phone") String phone);
    
    /**
     * 查询所有未删除用户
     */
    @Select("SELECT * FROM users WHERE deleted = 0")
    List<User> selectAllActiveUsers();
    
    /**
     * 检查 openid 是否存在
     */
    @Select("SELECT COUNT(*) FROM users WHERE openid = #{openid} AND deleted = 0")
    int countByOpenid(@Param("openid") String openid);
    
    /**
     * 统计未删除用户数量
     */
    @Select("SELECT COUNT(*) FROM users WHERE deleted = 0")
    int countActiveUsers();
}