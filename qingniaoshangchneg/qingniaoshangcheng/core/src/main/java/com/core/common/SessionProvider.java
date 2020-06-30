package com.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SessionProvider接口
 * 提供远程和本地session储存的实现
 */

public interface SessionProvider {
    public void setAttribute(HttpServletRequest request, HttpServletResponse response,String name,String value);
    public String getAttribute(HttpServletRequest request, HttpServletResponse response,String name);
    public String getSessionId(HttpServletRequest request, HttpServletResponse response);
}
