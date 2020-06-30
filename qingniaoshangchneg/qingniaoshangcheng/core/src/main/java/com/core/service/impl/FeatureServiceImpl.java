package com.core.service.impl;

import java.util.List;

import com.core.dao.product.FeatureMapper;
import com.core.pojo.product.Feature;
import com.core.pojo.product.FeatureCriteria;
import com.core.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {
	
	@Autowired
	FeatureMapper featureMapper;
	
	@Override
	public List<Feature> selectFeatureByFeatureCriteria(FeatureCriteria featureCriteria) {
		return featureMapper.selectByExample(featureCriteria);
	}

}
