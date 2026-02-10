package weixin.order_food.service;

import weixin.order_food.entity.Admin;
import java.util.List;

/**
 * 管理员服务接口
 */
public interface AdminService {

    /**
     * 检查是否为管理员
     */
    boolean isAdmin(String openid);

    /**
     * 添加管理员
     */
    Admin addAdmin(Admin admin);

    /**
     * 删除管理员
     */
    void deleteAdmin(Integer id);

    /**
     * 获取所有管理员
     */
    List<Admin> getAllAdmins();
}
