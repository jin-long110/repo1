package com.core.service;

import java.util.List;

import com.core.pojo.product.Color;
import com.core.pojo.product.ColorCriteria;

public interface ColorService {
	public List<Color> selectColorByColorCriteria(ColorCriteria colorCriteria);
}
