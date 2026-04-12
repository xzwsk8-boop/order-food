package weixin.order_food.lianzhi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.order_food.lianzhi.entity.HomeImage;
import weixin.order_food.lianzhi.repository.HomeImageRepository;
import weixin.order_food.lianzhi.service.HomeService;

import java.util.List;
import java.util.stream.Collectors;

@Service("lianzhiHomeService")
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeImageRepository homeImageRepository;

    @Override
    public HomeImage addHomeImage(HomeImage homeImage) {
        return homeImageRepository.save(homeImage);
    }

    @Override
    public HomeImage updateHomeImage(Long id, HomeImage updateData) {
        HomeImage existing = homeImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("首页图片不存在，ID: " + id));

        if (updateData.getImageUrl() != null) {
            existing.setImageUrl(updateData.getImageUrl());
        }
        if (updateData.getSortOrder() != null) {
            existing.setSortOrder(updateData.getSortOrder());
        }
        if (updateData.getIsActive() != null) {
            existing.setIsActive(updateData.getIsActive());
        }

        return homeImageRepository.save(existing);
    }

    @Override
    public void deleteHomeImage(Long id) {
        homeImageRepository.deleteById(id);
    }

    @Override
    public List<HomeImage> getAllHomeImages() {
        // 返回所有数据，并按 sortOrder 升序排列
        return homeImageRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC, "sortOrder"));
    }

    @Override
    public List<String> getHomeImageUrls() {
        // 从数据库中查询所有启用的轮播图，按排序字段升序排列
        List<HomeImage> images = homeImageRepository.findByIsActiveOrderBySortOrderAsc(1);
        
        // 提取图片链接并返回
        return images.stream()
                .map(HomeImage::getImageUrl)
                .collect(Collectors.toList());
    }
}