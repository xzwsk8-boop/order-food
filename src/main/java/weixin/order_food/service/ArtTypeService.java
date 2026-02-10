package weixin.order_food.service;

import weixin.order_food.entity.ArtType;

import java.util.List;

/**
 * 画作类型服务接口
 */
public interface ArtTypeService {

    /**
     * 添加画作
     */
    ArtType createArtType(ArtType artType);

    /**
     * 获取所有上架的画作列表
     */
    List<ArtType> getActiveArtTypes();

    /**
     * 获取所有画作（包括下架，后台用，预留）
     */
    List<ArtType> getAllArtTypes();

    /**
     * 删除画作（物理删除）
     */
    void deleteArtType(Integer id);
}
