package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.model.Color;

import java.util.List;

public interface ColorService {
    List<Color> getAllColors();
    Color getColorById(Long id);
    Color save(Color color);
    Color update(Color color, Long id);
    void delete(Long id);
}
