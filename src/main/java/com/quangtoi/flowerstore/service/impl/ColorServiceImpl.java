package com.quangtoi.flowerstore.service.impl;

import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Color;
import com.quangtoi.flowerstore.repository.ColorRepository;
import com.quangtoi.flowerstore.service.ColorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {
    private ColorRepository colorRepository;

    @Override
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    @Override
    public Color getColorById(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color", "id", id));
    }

    @Override
    public Color save(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public Color update(Color color, Long id) {
        Color colorSaved =  colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color", "id", id));
        colorSaved.setColor(color.getColor());
        return colorRepository.save(colorSaved);
    }

    @Override
    public void delete(Long id) {
        Color colorSaved =  colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color", "id", id));
        colorRepository.delete(colorSaved);
    }
}
