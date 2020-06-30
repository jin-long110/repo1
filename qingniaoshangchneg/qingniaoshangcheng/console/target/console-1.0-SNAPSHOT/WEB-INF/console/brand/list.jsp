<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/console/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>qingniao-list</title>
    <script type="text/javascript">
        function checkBox(ids, checked) {
            //全选与全不选
            $("input[name='" + ids + "']").attr("checked", checked);
        }

        //批量删除
        function batchDelete(pageNo,name,status) {
            var size = $("input[name='ids']:checked").size();
            if (size > 0) {
                //执行删除操作
                if (confirm("您能确定删除么")) {
                    $("#delForm").attr("action", "/brand/batchDelete.do?pageNo="+pageNo+"&amp;name="+name+"&amp;status="+status).submit();
                }
            }else {
                alert("请您选择你要删除的商品");
                return;
            }
        }

    </script>
</head>
<body>
<div class="box-positon">
    <div class="rpos">当前位置: 品牌管理 - 列表</div>
    <form class="ropt">
        <input class="add" type="button" value="添加" onclick="javascript:window.location.href='/brand/add.do'"/>
    </form>
    <div class="clear"></div>
</div>
<div class="body-box">
    <form action="/brand/list.do" method="post" style="padding-top:5px;">
        <input type="text" name="name" placeholder="请输入名称" value="${name }"/> &nbsp;&nbsp;&nbsp; 状态:
        <select name="status">
            <option value="1"
                    <c:if test="${status==1 }">selected="selected"</c:if> >在售
            </option>
            <option value="2"
                    <c:if test="${status==2 }">selected="selected"</c:if> >停售
            </option>
        </select>
        &nbsp;&nbsp;&nbsp;<input type="submit" class="query" value="查询"/>
    </form>
    <form id="delForm" method="post">
        <table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
            <thead class="pn-lthead">
            <tr>
                <th width="20"><input type="checkbox" onclick="checkBox('ids',this.checked)"/></th>
                <th>品牌名称</th>
                <th>LOGO</th>
                <th>描述</th>
                <th>官方网站</th>
                <th>状态</th>
                <th>选项</th>
            </tr>
            </thead>
            <tbody class="pn-ltbody">

            <!-- 				<tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
                                        <td><input type="checkbox" value="111" name="ids" /></td>
                                        <td align="center">夏款安踏短袖</td>
                                        <td align="center"><img width="40" height="40" src="/jsutils/qingniao/img/brand/anta.PNG" /></td>
                                        <td align="center">夏款安踏短袖纯棉</td>
                                        <td align="center">
                                            <a href="www.lining.com" target="_blank">
                                                <font color="green"><a target="_blank" href="http://www.anta.com">www.anta.com</a></font>

                                        </td>
                                        <td align="center">
                                            在售
                                        </td>
                                        <td align="center">
                                            <a class="pn-opt" href="#">修改</a>
                                            <a class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}"  href="#" >删除</a>
                                        </td>
                            </tr>
                                            <tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
                                        <td><input type="checkbox" value="111" name="ids" /></td>
                                        <td align="center">夏款安踏短袖</td>
                                        <td align="center"><img width="40" height="40" src="/jsutils/qingniao/img/brand/anta.PNG" /></td>
                                        <td align="center">夏款安踏短袖纯棉</td>
                                        <td align="center">
                                            <a href="www.lining.com" target="_blank">
                                                <font color="green"><a target="_blank" href="http://www.anta.com">www.anta.com</a></font>

                                        </td>
                                        <td align="center">
                                            在售
                                        </td>
                                        <td align="center">
                                            <a class="pn-opt" href="#">修改</a>
                                            <a class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}"  href="#" >删除</a>
                                        </td>
                            </tr>
                                            <tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
                                        <td><input type="checkbox" value="111" name="ids" /></td>
                                        <td align="center">夏款安踏短袖</td>
                                        <td align="center"><img width="40" height="40" src="/jsutils/qingniao/img/brand/anta.PNG" /></td>
                                        <td align="center">夏款安踏短袖纯棉</td>
                                        <td align="center">
                                            <a href="www.lining.com" target="_blank">
                                                <font color="green"><a target="_blank" href="http://www.anta.com">www.anta.com</a></font>

                                        </td>
                                        <td align="center">
                                            在售
                                        </td>
                                        <td align="center">
                                            <a class="pn-opt" href="#">修改</a>
                                            <a class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}"  href="#" >删除</a>
                                        </td>
                            </tr>
                                            <tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
                                        <td><input type="checkbox" value="111" name="ids" /></td>
                                        <td align="center">夏款安踏短袖</td>
                                        <td align="center"><img width="40" height="40" src="/jsutils/qingniao/img/brand/anta.PNG" /></td>
                                        <td align="center">夏款安踏短袖纯棉</td>
                                        <td align="center">
                                            <a href="www.lining.com" target="_blank">
                                                <font color="green"><a target="_blank" href="http://www.anta.com">www.anta.com</a></font>

                                        </td>
                                        <td align="center">
                                            在售
                                        </td>
                                        <td align="center">
                                            <a class="pn-opt" href="#">修改</a>
                                            <a class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}"  href="#" >删除</a>
                                        </td>
                            </tr>
                                            <tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
                                        <td><input type="checkbox" value="111" name="ids" /></td>
                                        <td align="center">夏款安踏短袖</td>
                                        <td align="center"><img width="40" height="40" src="/jsutils/qingniao/img/brand/anta.PNG" /></td>
                                        <td align="center">夏款安踏短袖纯棉</td>
                                        <td align="center">
                                            <a href="www.lining.com" target="_blank">
                                                <font color="green"><a target="_blank" href="http://www.anta.com">www.anta.com</a></font>

                                        </td>
                                        <td align="center">
                                            在售
                                        </td>
                                        <td align="center">
                                            <a class="pn-opt" href="#">修改</a>
                                            <a class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}"  href="#" >删除</a>
                                        </td>
                            </tr>
                                            <tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
                                        <td><input type="checkbox" value="111" name="ids" /></td>
                                        <td align="center">夏款安踏短袖</td>
                                        <td align="center"><img width="40" height="40" src="/jsutils/qingniao/img/brand/anta.PNG" /></td>
                                        <td align="center">夏款安踏短袖纯棉</td>
                                        <td align="center">
                                            <a href="www.lining.com" target="_blank">
                                                <font color="green"><a target="_blank" href="http://www.anta.com">www.anta.com</a></font>

                                        </td>
                                        <td align="center">
                                            在售
                                        </td>
                                        <td align="center">
                                            <a class="pn-opt" href="#">修改</a>
                                            <a class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}"  href="#" >删除</a>
                                        </td>
                            </tr> -->


            <c:forEach items="${pageInfo.list}" var="brand">
                <tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
                    <td><input type="checkbox" value="${brand.id }" name="ids"/></td>
                    <td align="center">${brand.name }</td>
                    <td align="center"><img width="40" height="40" src="${brand.imgUrl }"/></td>
                    <td align="center">${brand.description }</td>
                    <td align="center">
                        <a href="www.lining.com" target="_blank">
                            <font color="green"><a target="_blank" href="http://${brand.url }">${brand.url }</a></font>
                        </a>
                    </td>
                    <td align="center">
                        <c:if test="${brand.status==1}">在售</c:if>
                        <c:if test="${brand.status==2}">停售</c:if>
                    </td>
                    <td align="center">
                        <a class="pn-opt" href="/brand/edit.do?id=${brand.id }">修改</a>
                        <a class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}"
                           href="/brand/batchDelete.do?id=${brand.id }&pageNo=${pageNo}&name=${name}&status=${status}">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
    <div class="page pb15">
				<span class="r inb_a page_b">
					
					
					<c:forEach items="${pageInfo.pageView}" var="page">
                        ${page}
                    </c:forEach>
				</span>
    </div>
    <div style="margin-top:15px;"><input class="del-button" type="button" value="删除"
                                         onclick="batchDelete('${pageNo}','${name}','${status}');"/></div>
</div>
</body>

</html>