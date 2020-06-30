package com.core.service.impl;

import com.core.dao.product.ImgMapper;
import com.core.pojo.product.Img;
import com.core.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ImgServiceImpl implements ImgService {
	@Autowired
	ImgMapper imgMapper;

	@Override
	public void insertSelective(Img record) {
		imgMapper.insertSelective(record);
	}
	
	
}
