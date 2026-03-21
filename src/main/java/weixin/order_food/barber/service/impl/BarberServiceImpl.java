package weixin.order_food.barber.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.order_food.barber.entity.Barber;
import weixin.order_food.barber.repository.BarberRepository;
import weixin.order_food.barber.service.BarberService;

import java.util.List;

@Service
public class BarberServiceImpl implements BarberService {

    @Autowired
    private BarberRepository barberRepository;

    @Override
    public List<Barber> getActiveBarbers() {
        return barberRepository.findByIsActive(1);
    }

    @Override
    public List<Barber> getAllBarbers() {
        return barberRepository.findAll();
    }

    @Override
    public Barber getBarberById(Long id) {
        return barberRepository.findById(id).orElse(null);
    }

    @Override
    public Barber addBarber(Barber barber) {
        if (barber.getIsActive() == null) {
            barber.setIsActive(1);
        }
        return barberRepository.save(barber);
    }

    @Override
    public Barber updateBarber(Barber barber) {
        return barberRepository.findById(barber.getId()).map(existing -> {
            if (barber.getName() != null) existing.setName(barber.getName());
            if (barber.getTitle() != null) existing.setTitle(barber.getTitle());
            if (barber.getAvatar() != null) existing.setAvatar(barber.getAvatar());
            if (barber.getBio() != null) existing.setBio(barber.getBio());
            if (barber.getPortfolio() != null) existing.setPortfolio(barber.getPortfolio());
            if (barber.getIsActive() != null) existing.setIsActive(barber.getIsActive());
            return barberRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("理发师不存在"));
    }

    @Override
    public void deleteBarber(Long id) {
        barberRepository.deleteById(id);
    }
}