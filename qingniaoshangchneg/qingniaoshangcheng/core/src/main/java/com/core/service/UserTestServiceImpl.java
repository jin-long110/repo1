package com.core.service;

import com.core.dao.UserTestMapper;
import com.core.pojo.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * userTest的service类
 * 测试事物
 * @author quan
 *
 */

@Service
@Transactional
public class UserTestServiceImpl {
	@Autowired
	UserTestMapper userTestMapper;
	
	public void insertUserTest(UserTest user) {
		userTestMapper.insertUserTest(user);
		int a = 1/0;
	}
}
