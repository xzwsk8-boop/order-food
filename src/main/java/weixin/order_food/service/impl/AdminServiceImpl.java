package weixin.order_food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.order_food.entity.Admin;
import weixin.order_food.repository.AdminRepository;
import weixin.order_food.service.AdminService;

import java.util.List;

/**
 * 管理员服务实现
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public boolean isAdmin(String openid) {
        if (openid == null || openid.isEmpty()) {
            return false;
        }
        return adminRepository.existsByOpenid(openid);
    }

    @Override
    public Admin addAdmin(Admin admin) {
        // 简单去重检查
        if (adminRepository.existsByOpenid(admin.getOpenid())) {
            throw new RuntimeException("该用户已经是管理员");
        }
        return adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Integer id) {
        adminRepository.deleteById(id);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}
