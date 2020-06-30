package com.core.service;

import com.core.pojo.user.Addr;

//收货地址和其他信息
public interface AddrService {
    Addr selectAddrByUsername(String username);
}
