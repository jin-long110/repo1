package com.core.service;

import com.core.pojo.user.User;
import org.springframework.stereotype.Service;

public interface LoginService {
    public User getUserByUsernameAndPassword(String username,String password);
}
