package weixin.order_food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import weixin.order_food.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * 用户 Repository 接口
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据 openid 查询用户
     */
    @Query("SELECT u FROM User u WHERE u.openid = :openid AND u.deleted = 0")
    Optional<User> findByOpenid(@Param("openid") String openid);
    
    /**
     * 根据手机号查询用户
     */
    @Query("SELECT u FROM User u WHERE u.phone = :phone AND u.deleted = 0")
    Optional<User> findByPhone(@Param("phone") String phone);
    
    /**
     * 查询所有未删除用户
     */
    @Query("SELECT u FROM User u WHERE u.deleted = 0")
    List<User> findAllActiveUsers();
    
    /**
     * 检查 openid 是否存在
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.openid = :openid AND u.deleted = 0")
    long countByOpenid(@Param("openid") String openid);
    
    /**
     * 统计未删除用户数量
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.deleted = 0")
    long countActiveUsers();
}