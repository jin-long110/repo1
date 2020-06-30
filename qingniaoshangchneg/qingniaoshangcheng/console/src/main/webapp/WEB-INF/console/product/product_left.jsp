<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>product-left</title>
<link href="/jsutils/qingniao/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="/jsutils/common/css/theme.css" rel="stylesheet" type="text/css"/>
<link href="/jsutils/common/css/jquery.validate.css" rel="stylesheet" type="text/css"/>
<link href="/jsutils/common/css/jquery.treeview.css" rel="stylesheet" type="text/css"/>
<link href="/jsutils/common/css/jquery.ui.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="/jsutils/fckeditor/fckeditor.js"></script>
<script src="/jsutils/common/js/jquery.js" type="text/javascript"></script>
<script src="/jsutils/common/js/jquery.ext.js" type="text/javascript"></script>
<script src="/jsutils/common/js/jquery.form.js" type="text/javascript"></script>
<script src="/jsutils/common/js/qingniao.js" type="text/javascript"></script>
<script src="/jsutils/qingniao/js/admin.js" type="text/javascript"></script>
</head>
<body class="lbody">
<div class="left">
<%@ include file="/WEB-INF/console/date.jsp" %>
     <ul class="w-lful">
		<li><a href="product/list.do" target="rightFrame">商品管理</a></li>
		<li><a href="brand/list.do" target="rightFrame">品牌管理</a></li>
     </ul>
</div>
</body>
</html>