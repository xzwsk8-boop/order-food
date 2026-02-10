package weixin.order_food.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.order_food.entity.ArtType;
import weixin.order_food.repository.ArtTypeRepository;
import weixin.order_food.service.ArtTypeService;

import java.util.List;

/**
 * 画作类型服务实现
 */
@Service
public class ArtTypeServiceImpl implements ArtTypeService {

    @Autowired
    private ArtTypeRepository artTypeRepository;

    @Override
    public ArtType createArtType(ArtType artType) {
        return artTypeRepository.save(artType);
    }

    @Override
    public List<ArtType> getActiveArtTypes() {
        // 1 表示上架
        return artTypeRepository.findByIsOnSaleOrderBySortOrderDescCreatedAtDesc(1);
    }

    @Override
    public List<ArtType> getAllArtTypes() {
        return artTypeRepository.findAll();
    }

    @Override
    public void deleteArtType(Integer id) {
        artTypeRepository.deleteById(id);
    }
}
