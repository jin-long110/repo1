<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/console/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>qingniao-left</title>
</head>
<body class="lbody">
<div class="left">
<%@ include file="/WEB-INF/console/date.jsp" %>
     <ul class="w-lful">
     	<li><a href="javascirpt:void(0)" target="rightFrame">个人资料</a></li>
      	<li><a href="right.jsp" target="rightFrame">会员注册</a></li>
     	<li><a href="right.jsp" target="rightFrame">商品统计</a></li>
     	<li><a href="right.jsp" target="rightFrame">订单统计</a></li>
     	<li><a href="right.jsp" target="rightFrame">留言统计</a></li>
     	<li><a href="right.jsp" target="rightFrame">内容管理</a></li>
     	<li><a href="right.jsp" target="rightFrame">内容分类管理</a></li>
     	<li><a href="right.jsp" target="rightFrame">用户管理中心</a></li>
     </ul>
</div>
</body>
</html>