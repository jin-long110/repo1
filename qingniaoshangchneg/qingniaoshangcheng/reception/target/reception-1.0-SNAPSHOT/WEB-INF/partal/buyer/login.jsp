<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登录_青鸟运动购物平台</title>
<link rel="stylesheet" href="/jsutils/css/style.css" />
<script src="/jsutils/js/jquery.js"></script>
<script src="/jsutils/js/com.js"></script>
<%--	<script type="text/javascript">--%>
<%--		function denglu() {--%>
<%--			alert($("#errorName").val())--%>
<%--		}--%>
<%--	</script>--%>
</head>
<body>
<div class="bar"><div class="bar_w">
	<p class="l">
		<span class="l">
			收藏本网站！北京<a href="#" title="更换">[更换]</a>
		</span>
	</p>
	<ul class="r uls">
	<li class="dev">您好,欢迎来到青鸟运动购物平台！</li>
	<li class="dev"><a href="#" title="在线客服">在线客服</a></li>
	<li class="dev after"><a href="#" title="English">English</a></li>
	</ul>
</div></div>
<div class="w loc">
	<div class="h-title" id="logo">
		<div class="h-logo l"><img src="/jsutils/img/pic/logo-3.png"/></div>
		<div class="l" style="margin: 13px 10px;font-size: 20px;font-family: '微软雅黑';letter-spacing: 2px">欢迎登录</div>
	</div>
</div>
<div class="sign">
	<div class="l ad420x205"><a href="#" title="title"><img src="/jsutils/qingniao/img/admin/welcome.jpg" width="400" height="400"/></a></div>
	<div class="r">
		<h2 title="登录青鸟运动购物平台">登录青鸟运动购物平台</h2>
		<form id="jvForm" action="/login.html" method="post">
			<input type="hidden" name="url" value="${url}"/>
			<ul class="uls form">
			<li id="errorName" class="errorTip" style="display:none" >${error}</li>
			<li><label for="username">用户名：</label>
				<span class="bg_text">
					<input type="text" id="username" name="username" maxLength="100" />
				</span>
			</li>
			<li><label for="password">密　码：</label>
				<span class="bg_text">
					<input type="password" id="password" name="password" maxLength="32" />
				</span>
			</li>
			<li><%--@declare id="captcha"--%><label for="captcha">验证码：</label>
				<span class="bg_text small">
					<input type="text" id="code" name="code" maxLength="7"/>
				</span>
				<img src="/login/getCode.html" onclick="this.src='/login/getCode.html?d='+new Date()" class="code" alt="换一张" /><a href="javascript:void(0);" onclick="$('.code').attr('src','/login/getCode.html?d='+new Date())" title="换一张">换一张</a></li>
			<li><%--@declare id=""--%><label for="">&nbsp;</label><input type="submit" value="登 录"  class="hand btn66x23"/><a href="#" title="忘记密码？">忘记密码？</a></li>
			</ul>
		</form>
	</div>
</div>
</body>
</html>