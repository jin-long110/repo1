package com.core.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    LocalSessionProvider localSessionProvider;

    // 进入handle之前进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //判断访问路径是否需要拦截
        String usernmae = localSessionProvider.getAttribute(request, response, Constants.USER_NAME);
        String url = request.getParameter("url");
        //获得访问的uri
        String requestURI = request.getRequestURI();
        //判断是否访问的购买页面
        if (requestURI.startsWith("/buy")) {
            if (usernmae != null) {
                //设置一个标记表示用户已经登录
                request.setAttribute("isLogin", true);
            } else {
                //没有登录
                request.setAttribute("isLogin", false);
                //重定向都登录页面

                if (url != null) {
                    response.sendRedirect("/login.html?url=" + url);
                    return false;    //不能进入handle
                } else {
                    response.sendRedirect("/login.html");
                    return false;    //不能进入handle
                }

            }
        } else {
            //不需要拦截的
            if (usernmae != null) {
                request.setAttribute("isLogin", true);
            } else {
                request.setAttribute("isLogin", false);
            }
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
