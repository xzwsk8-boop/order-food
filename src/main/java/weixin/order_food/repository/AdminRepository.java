package weixin.order_food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weixin.order_food.entity.Admin;

import java.util.Optional;

/**
 * 管理员 Repository
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    /**
     * 根据 OpenID 查询管理员
     */
    Optional<Admin> findByOpenid(String openid);
    
    /**
     * 判断 OpenID 是否存在
     */
    boolean existsByOpenid(String openid);
}
