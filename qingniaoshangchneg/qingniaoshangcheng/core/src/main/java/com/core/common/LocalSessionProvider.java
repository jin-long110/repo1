package com.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//本地session的实现
public class LocalSessionProvider implements SessionProvider{
    //存储数据
    @Override
    public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, String value) {
request.getSession().setAttribute(name,value);
    }
//取数据
    @Override
    public String getAttribute(HttpServletRequest request, HttpServletResponse response, String name) {
        HttpSession session = request.getSession(false);
        if (session!=null){
            return (String) session.getAttribute(name);
        }else {
            return null;
        }

    }


//获取sessionid
    @Override
    public String getSessionId(HttpServletRequest request, HttpServletResponse response) {

        return   request.getSession().getId();
    }
}
