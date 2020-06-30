package com.core.service;

import java.util.List;

import com.core.pojo.product.Feature;
import com.core.pojo.product.FeatureCriteria;

public interface FeatureService {
	public List<Feature> selectFeatureByFeatureCriteria(FeatureCriteria featureCriteria);
}
