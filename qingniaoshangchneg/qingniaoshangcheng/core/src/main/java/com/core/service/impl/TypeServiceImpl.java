package com.core.service.impl;

import java.util.List;

import com.core.dao.product.TypeMapper;
import com.core.pojo.product.Type;
import com.core.pojo.product.TypeCriteria;
import com.core.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TypeServiceImpl implements TypeService {

	@Autowired
	TypeMapper typeMapper;

	@Override
	public List<Type> selectTypeByTypeCriteria(TypeCriteria typeCriteria) {
		return typeMapper.selectByExample(typeCriteria);
	}
}
