package com.core.service.impl;

import com.core.dao.user.AddrMapper;
import com.core.pojo.user.Addr;
import com.core.pojo.user.AddrCriteria;
import com.core.service.AddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AddrServiceimpl implements AddrService {
    @Autowired
    AddrMapper addrMapper;

    //通过用户名查寻用户地址
    @Override
    public Addr selectAddrByUsername(String username) {
        AddrCriteria addrCriteria = new AddrCriteria();
        addrCriteria.createCriteria().andUserIdEqualTo(username);
        List<Addr> addrs = addrMapper.selectByExample(addrCriteria);
        return addrs.get(0);
    }
}
