package com.core.service;

import java.util.List;

import com.core.pojo.product.Type;
import com.core.pojo.product.TypeCriteria;

public interface TypeService {
	public List<Type> selectTypeByTypeCriteria(TypeCriteria typeCriteria);
}
