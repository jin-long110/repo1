package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.dao.product.ColorMapper;
import com.core.pojo.product.Color;
import com.core.pojo.product.ColorCriteria;
import com.core.service.ColorService;

@Service
@Transactional
public class ColorServiceImpl implements ColorService {
	@Autowired
	ColorMapper colorMapper;

	@Override
	public List<Color> selectColorByColorCriteria(ColorCriteria colorCriteria) {
		return colorMapper.selectByExample(colorCriteria);
	}
	
}
