package com.core.service.impl;

import com.core.common.MD5Utils;
import com.core.dao.user.UserMapper;
import com.core.pojo.user.User;
import com.core.pojo.user.UserCriteria;
import com.core.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserMapper userMapper;

    //通过账户和密码进行查询
    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        //需要对密码进行MD5加密
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(MD5Utils.md5(password));
        List<User> users = userMapper.selectByExample(userCriteria);
        return users.get(0);
    }
}
