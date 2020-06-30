<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/console/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	
	<head>
		<title>qingniao-add</title>
		<script type="text/javascript">

			//图片上传，使用jQuery.form.js插件
			function ImgUpload() {
				// alert(1111)
				var opts = {
					url : "/upload/ImgtoFastDFS.do",
					type : "post",
					dataType : "json",
					success : function(data) {
						// alert("999999")
						// alert(data.url)
						// alert(data.path)
						$("#allimg").attr("src",data.url);
						$("#himg").val(data.path);
					}
				}
				//提交表单
				$("#brandForm").ajaxSubmit(opts);
			}
			
		</script>
	</head>
	<body>
		<div class="box-positon">
			<div class="rpos">当前位置: 品牌管理 - 添加</div>
			<form class="ropt">
				<input type="submit" onclick="" value="返回列表" class="return-button"/>
			</form>
			<div class="clear"></div>
		</div>
		<div class="body-box" style="float:right">
			<form id="brandForm" action="/brand/save.do" method="post" enctype="multipart/form-data">
				<table cellspacing="1" cellpadding="2" width="100%" border="0" class="pn-ftable">
					<tbody>
						<tr>
							<td width="20%" class="pn-flabel pn-flabel-h">
								<span class="pn-frequired">*</span> 品牌名称:
							</td>
							<td width="80%" class="pn-fcontent">
								<input type="text" class="required" name="name" maxlength="100" />
							</td>
						</tr>
						<tr>
							<td width="20%" class="pn-flabel pn-flabel-h">
								<span class="pn-frequired">*</span> 品牌LOGO:
							</td>
							<td width="80%" class="pn-fcontent">
								注:该尺寸图片必须为90x150。
							</td>
						</tr>
						<tr>
							<td width="20%" class="pn-flabel pn-flabel-h"></td>
							<td width="80%" class="pn-fcontent">
								<img width="100" height="100" id="allimg" />
								<input type="file" name="picfile" onchange="ImgUpload();" />
								<input type="hidden" name="logo"  id="himg" />	<!-- 回显图片的路径 -->
							</td>
						</tr>
						<tr>
							<td width="20%" class="pn-flabel pn-flabel-h">
								品牌描述:</td>
							<td width="80%" class="pn-fcontent">
								<input type="text" class="required" name="description" maxlength="80" size="60" />
							</td>
						</tr>

						<tr>
							<td width="20%" class="pn-flabel pn-flabel-h">
								官方网站:</td>
							<td width="80%" class="pn-fcontent">
								<input type="text" class="required" name="url" maxlength="80" size="60" />
							</td>
						</tr>
						<tr>
							<td width="20%" class="pn-flabel pn-flabel-h">
								品牌的状态:</td>
							<td width="80%" class="pn-fcontent">
								<input type="radio" name="status" value="1" checked="checked" />在售
								<input type="radio" name="status" value="2"  />停售
							</td>
						</tr>
					</tbody>
					<tbody>
						<tr>
							<td class="pn-fbutton" colspan="2">
								<input type="submit" class="submit" value="提交" /> &nbsp; <input type="reset" class="submit" value="重置" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</body>

</html>