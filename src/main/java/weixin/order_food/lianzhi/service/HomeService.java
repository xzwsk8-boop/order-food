package weixin.order_food.lianzhi.service;

import weixin.order_food.lianzhi.entity.HomeImage;

import java.util.List;

public interface HomeService {
    
    /**
     * 添加首页图片
     */
    HomeImage addHomeImage(HomeImage homeImage);

    /**
     * 修改首页图片信息
     */
    HomeImage updateHomeImage(Long id, HomeImage homeImage);

    /**
     * 删除首页图片
     */
    void deleteHomeImage(Long id);

    /**
     * 获取所有首页图片（后台管理用，包含已禁用）
     */
    List<HomeImage> getAllHomeImages();

    /**
     * 获取连枝酒馆首页图片连接列表（如轮播图、展示图等）
     * 
     * @return 包含微信云存储图片链接的列表
     */
    List<String> getHomeImageUrls();
}