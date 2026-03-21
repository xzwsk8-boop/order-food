package weixin.order_food.barber.service;

import weixin.order_food.barber.entity.Barber;
import java.util.List;

public interface BarberService {
    /**
     * 获取所有在职的理发师（小程序端展示用）
     */
    List<Barber> getActiveBarbers();

    /**
     * 获取所有理发师（管理员端展示用）
     */
    List<Barber> getAllBarbers();

    /**
     * 根据ID获取理发师详情
     */
    Barber getBarberById(Long id);

    /**
     * 添加理发师
     */
    Barber addBarber(Barber barber);

    /**
     * 更新理发师信息
     */
    Barber updateBarber(Barber barber);

    /**
     * 删除理发师（物理删除或将其设为不在职，这里示例物理删除）
     */
    void deleteBarber(Long id);
}