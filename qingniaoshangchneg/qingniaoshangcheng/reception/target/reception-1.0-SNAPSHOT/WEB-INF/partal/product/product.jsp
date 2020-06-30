<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expijsutils" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <title>青鸟运动购物平台-电子商城</title>
    <link rel="stylesheet" href="jsutils/css/style.css"/>
    <script src="jsutils/js/jquery.js"></script>
    <script src="jsutils/js/com.js"></script>
    <script type="text/javascript">
        var gname = '${pname}';
        var gbrandId = '${brandId}';
        var gprice = '${price}';

        function searchName() {
            var pname = $("#pname").val();
            //页面跳转
            window.location.href = "/index.html?pname=" + pname;
        }

        //通过品牌查询
        function changeBrand(brandId) {
            if (gname != '' && gprice != '') {
                window.location.href = "/index.html?brandId=" + brandId + "&pname=" + gname + "&price" + gprice;
                return;
            }
            if (gname != '') {
                window.location.href = "/index.html?brandId=" + brandId + "&pname=" + gname;
                return;
            }
            if (gprice != '') {
                window.location.href = "/index.html?brandId=" + brandId + "&price=" + gprice;
                return;
            }
            window.location.href = "/index.html?brandId=" + brandId;

        }

        //通过价格查询
        function changePrice(price) {
            if (gname != '' && gbrandId != '') {
                window.location.href = "/index.html?gbrandId=" + gbrandId + "&pname=" + gname + "&price" + price;
                return;
            }
            if (gname != '') {
                window.location.href = "/index.html?price=" + price + "&pname=" + gname;
                return;
            }
            if (gbrandId != '') {
                window.location.href = "/index.html?brandId=" + gbrandId + "&price=" + price;
                return;
            }
            window.location.href = "/index.html?price=" + price;
        }

        //取消已选条件
        function clearCondition(key) {
            if (key == '品牌:') {
                if (gprice != '') {
                    window.location.href = "/index.html?pname=" + gname + "&price=" + gprice;
                    return;
                } else {
                    window.location.href = "/index.html?pname=" + gname;
                }
            } else {
                if (key == '价格') {
                    if (gbrandId != '') {
                        window.location.href = "/index.html?pname=" + gname + "&brandId=" + gbrandId;
                        return;
                    } else {
                        window.location.href = "/index.html?pname=" + gname;
                    }
                }
            }
        }
        //登陆
        function login() {
window.location.href="/login.html?url="+encodeURIComponent(window.location.href);
        }
    </script>
</head>
<body>
<div class="bar">
    <div class="bar_w">
        <p class="l">
		<span class="l">
			收藏本网站！北京<a href="#" title="更换">[更换]</a>
		</span>
        </p>
        <ul class="r uls">
            <li class="dev">您好,欢迎来到青鸟运动购物平台！</li>
            <c:if test="${!isLogin}">
            <li class="dev"><a href="javascript:void(0)" onclick="login()" title="登陆">[登陆]</a></li>
            <li class="dev"><a href="javascript:void(0)" onclick="register()" title="免费注册">[免费注册]</a></li>
            </c:if>
            <li class="dev"><a href="javascript:void(0)" onclick="logout()" title="退出">[退出]</a></li>
            <li class="dev"><a href="javascript:void(0)" onclick="myOrder()" title="我的订单">我的订单</a></li>
            <li class="dev"><a href="#" title="在线客服">在线客服</a></li>
            <li class="dev after"><a href="#" title="English">English</a></li>
        </ul>
    </div>
</div>
<div class="w loc">
    <div class="h-title">
        <div class="h-logo"><a href="http://localhost:8080"><img src="jsutils/img/pic/logo-3.png"/></a></div>
        <div class="h-search">
            <input type="text" placeholder="商品名称" id="pname" value="${pname }"/>
            <div class="h-se-btn">
                <a href="javascript:void(0)" onclick="searchName();">搜索</a>
            </div>
        </div>
    </div>
    <dl id="cart" class="cart r">
        <dt><a href="#" title="结算">结算</a>购物车:<b id="">5</b>件</dt>
        <dd class="hidden">
            <p class="alg_c hidden">购物车中还没有商品，赶紧选购吧！</p>
            <h3 title="最新加入的商品">最新加入的商品</h3>
            <ul class="uls">
                <li>
                    <a href="#" title="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣">
                        <img src="jsutils/img/pic/shangpingdemo.jpg" alt="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <p class="dt">
                        <a href="#" title="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣">安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣 </a>
                    </p>
                    <p class="dd">
                        <b><var>¥169</var><span>x1</span></b>
                        <a href="javascript:void(0);" title="删除" class="del">删除</a>
                    </p>
                </li>
                <li>
                    <a href="#" title="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣">
                        <img src="jsutils/img/pic/shangpingdemo.jpg" alt="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <p class="dt">
                        <a href="#" title="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣">安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣 </a>
                    </p>
                    <p class="dd">
                        <b><var>¥169</var><span>x1</span></b>
                        <a href="javascript:void(0);" title="删除" class="del">删除</a>
                    </p>
                </li>
                <li>
                    <a href="#" title="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣">
                        <img src="jsutils/img/pic/shangpingdemo.jpg" alt="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <p class="dt">
                        <a href="#" title="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣">安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣 </a>
                    </p>
                    <p class="dd">
                        <b><var>¥169</var><span>x1</span></b>
                        <a href="javascript:void(0);" title="删除" class="del">删除</a>
                    </p>
                </li>
                <li>
                    <a href="#" title="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣">
                        <img src="jsutils/img/pic/shangpingdemo.jpg" alt="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <p class="dt">
                        <a href="#" title="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣">安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣 </a>
                    </p>
                    <p class="dd">
                        <b><var>¥169</var><span>x1</span></b>
                        <a href="javascript:void(0);" title="删除" class="del">删除</a>
                    </p>
                </li>
                <li>
                    <a href="#" title="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣">
                        <img src="jsutils/img/pic/shangpingdemo.jpg" alt="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <p class="dt">
                        <a href="#" title="安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣">安踏t恤男短袖 2017夏季新款圆领透气黑色速干大logo运动休闲上衣 </a>
                    </p>
                    <p class="dd">
                        <b><var>¥169</var><span>x1</span></b>
                        <a href="javascript:void(0);" title="删除" class="del">删除</a>
                    </p>
                </li>
            </ul>
            <div>
                <p>共<b>5</b>件商品&nbsp;&nbsp;&nbsp;&nbsp;共计<b class="f20">¥640.00</b></p>
                <a href="#" title="去购物车结算" class="inb btn120x30c">去购物车结算</a>
            </div>
        </dd>
    </dl>
</div>

<div class="w ofc">
    <div class="l wl">
        <h2 class="h2 h2_l"><em title="销量排行榜">销量排行榜</em><cite></cite></h2>
        <div class="box bg_white">
            <ul class="uls x_50x50">
                <li>
                    <var class="sfont">1</var>
                    <a href="#" title="" target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
                <li>
                    <var class="sfont">2</var>
                    <a href="#" title="" target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
                <li>
                    <var class="sfont">3</var>
                    <a href="#" title="" target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
                <li>
                    <a href="#" title="" target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
                <li>
                    <a href="#" title="" target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
                <li>
                    <a href="#" title="" target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
            </ul>
        </div>

        <h2 class="h2 h2_l mt"><em title="我的浏览记录">我的浏览记录</em><cite></cite></h2>
        <div class="box bg_white">
            <ul class="uls x_50x50">
                <li>
                    <a href="#" title=" " target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气黑色速干大logo运动休闲上衣" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
                <li>
                    <a href="#" title=" " target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
                <li>
                    <a href="#" title=" " target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
                <li>
                    <a href="#" title=" " target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
                <li>
                    <a href="#" title=" " target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
                <li>
                    <a href="#" title=" " target="_blank" class="pic">
                        <img src="jsutils/img/pic/xiaotubiao.png" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <!-- dt 8个文字+... -->
                        <dt><a href="#" title="2017夏季新款圆领透气" target="_blank">2017夏季新款圆领透气</a></dt>
                        <dd class="orange">￥120 ~ ￥150</dd>
                    </dl>
                </li>
            </ul>
        </div>

        <h2 class="h2 h2_l mt"><em title="商家精选">商家精选</em><cite></cite></h2>
        <img src="jsutils/img/pic/ad200x75.jpg" alt=""/>
    </div>
    <div class="r wr">
        <h2 class="h2 h2_r rel"><samp></samp><em title="热卖推荐">&nbsp;&nbsp;&nbsp;热卖推荐</em></h2>
        <div class="box bg_white">
            <ul class="uls i_150x150 x4_150x150">
                <li>
                    <a href="productDetail.jsp" title=" " target="_blank" class="pic">
                        <img src="jsutils/img/pic/shangpingdemo.jpg" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <dt><a href="productDetail.jsp" title="2017夏季新款圆领透气黑色速干大logo运动休闲上衣" target="_blank">2017夏季新款圆领透气黑色速干大logo运动休闲上衣</a>
                        </dt>
                        <dd class="h40">2017夏季新款圆领透气黑色速干大logo运动休闲上衣</dd>
                        <dd class="orange">￥169 ~ ￥119</dd>
                        <dd>
                            <a href="productDetail.jsp" title="立即抢购" class="inb btn70x21 mr">立即抢购</a>
                        </dd>
                    </dl>
                </li>
                <li>
                    <a href="productDetail.jsp" title=" " target="_blank" class="pic">
                        <img src="jsutils/img/pic/shangpingdemo.jpg" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <dt><a href="productDetail.jsp" title="2017夏季新款圆领透气黑色速干大logo运动休闲上衣" target="_blank">2017夏季新款圆领透气黑色速干大logo运动休闲上衣</a>
                        </dt>
                        <dd class="h40">2017夏季新款圆领透气黑色速干大logo运动休闲上衣</dd>
                        <dd class="orange">￥169 ~ ￥119</dd>
                        <dd>
                            <a href="productDetail.jsp" title="立即抢购" class="inb btn70x21 mr">立即抢购</a>
                        </dd>
                    </dl>
                </li>
                <li>
                    <a href="productDetail.jsp" title=" " target="_blank" class="pic">
                        <img src="jsutils/img/pic/shangpingdemo.jpg" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <dt><a href="productDetail.jsp" title="2017夏季新款圆领透气黑色速干大logo运动休闲上衣" target="_blank">2017夏季新款圆领透气黑色速干大logo运动休闲上衣</a>
                        </dt>
                        <dd class="h40">2017夏季新款圆领透气黑色速干大logo运动休闲上衣</dd>
                        <dd class="orange">￥169 ~ ￥119</dd>
                        <dd>
                            <a href="productDetail.jsp" title="立即抢购" class="inb btn70x21 mr">立即抢购</a>
                        </dd>
                    </dl>
                </li>
                <li>
                    <a href="productDetail.jsp" title=" " target="_blank" class="pic">
                        <img src="jsutils/img/pic/shangpingdemo.jpg" alt="2017夏季新款圆领透气黑色速干大logo运动休闲上衣"/></a>
                    <dl>
                        <dt><a href="productDetail.jsp" title="2017夏季新款圆领透气黑色速干大logo运动休闲上衣" target="_blank">2017夏季新款圆领透气黑色速干大logo运动休闲上衣</a>
                        </dt>
                        <dd class="h40">2017夏季新款圆领透气黑色速干大logo运动休闲上衣</dd>
                        <dd class="orange">￥169 ~ ￥119</dd>
                        <dd>
                            <a href="productDetail.jsp" title="立即抢购" class="inb btn70x21 mr">立即抢购</a>
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>

        <h2 class="h2 h2_filter mt"><em title="商品筛选">商品筛选</em><cite><a href="javascript:void(0);" id="filterRest"
                                                                       title="重置筛选条件">重置筛选条件</a></cite></h2>
        <ul class="uls filter">
            <c:if test="${flag}">
                <li><label>已选条件：</label>
                    <p class="sel">
                            <%--                    遍历map集合--%>
                        <c:forEach items="${condition}" var="c">
                            <a href="javascript:void(0);" onclick="clearCondition('${c.key}')">
                                <em>${c.key}</em>${c.value}
                                <cite title="关闭此筛选条件">X</cite></a>
                        </c:forEach>
                    </p>
                </li>
            </c:if>
            <c:if test="${empty brandId}">
                <li><b>品牌：</b>
                    <p>
                        <a href="javascript:void(0);" title="不限" class="here">不限</a>
                        <c:forEach items="${brands}" var="brand">
                            <a href="javascript:void(0);" onclick="changeBrand('${brand.id}')"
                               title="${brand.name}">${brand.name}</a>
                        </c:forEach>
                    </p>
                </li>
            </c:if>
            <c:if test="${empty price}">
                <li><b>价格：</b>
                    <p>
                        <a href="javascript:void(0);" title="不限" class="here">不限</a>
                        <a href="javascript:void(0);" onclick="changePrice('0-79')" title="1-99">0-79</a>
                        <a href="javascript:void(0);" onclick="changePrice('80-199')" title="100-199">80-199</a>
                        <a href="javascript:void(0);" onclick="changePrice('200-299')" title="200-499">200-299</a>
                        <a href="javascript:void(0);" onclick="changePrice('300-499')" title="200-499">300-499</a>
                        <a href="javascript:void(0);" onclick="changePrice('500-599')" title="200-499">500-599</a>
                        <a href="javascript:void(0);" onclick="changePrice('600以上')" title="200-499">600以上</a>
                    </p>
                </li>
            </c:if>
            <li><b>类型：</b>
                <p>
                    <a href="javascript:void(0);" title="不限" class="here">不限</a>
                    <a href="javascript:void(0);" title="瑜伽服">运动短袖</a>
                    <a href="javascript:void(0);" title="瑜伽铺巾">运动长裤</a>
                    <a href="javascript:void(0);" title="瑜伽垫">运动外套</a>
                    <a href="javascript:void(0);" title="舞蹈鞋服">网球服</a>
                    <a href="javascript:void(0);" title="瑜伽辅助">运动袜子</a>
                </p>
            </li>
            <li><b>材质：</b>
                <p>
                    <span><a href="javascript:void(0);" title="不限" class="here">不限</a></span>
                    <span><a href="javascript:void(0);" title="锦纶">锦纶</a></span>
                    <span><a href="javascript:void(0);" title="纤维">竹纤维</a></span>
                    <span><a href="javascript:void(0);" title="涤纶">涤纶</a></span>
                    <span><a href="javascript:void(0);" title="棉麻">棉麻</a></span>
                    <span><a href="javascript:void(0);" title="纯棉">纯棉</a></span>
                </p>
            </li>

            <li><b>适用人群：</b>
                <p>
                    <a href="javascript:void(0);" title="不限" class="here">不限</a>
                    <a href="javascript:void(0);" title="男士">男士</a>
                    <a href="javascript:void(0);" title="女士">女士</a>
                    <a href="javascript:void(0);" title="儿童">儿童</a>
                    <a href="javascript:void(0);" title="中性">中性</a>
                </p>
            </li>
        </ul>
        <div class="sort_type">
            <a href="javascript:void(0);" title="销量" class="sales">销量</a>
            <a href="javascript:void(0);" title="价格" class="price">价格</a>
            <a href="javascript:void(0);" title="上架时间" class="time">上架时间</a>
        </div>
        <div class="mt ofc">
            <ul class="uls i_150x150 x4_150x150b">
                <c:forEach items="${pageInfo.list}" var="product">
                    <li>
                        <a href="http://localhost:8087/html/product/${product.id}.html" title="t恤" target="_blank" class="pic"><img
                                src="${product.img.imgUrl}"
                                alt="t恤"/></a>
                        <dl>
                            <!-- dt 10个文字+... -->
                            <dt><a href="javascript:void(0)" title="${product.name }"
                                   onclick="window.open('/product/detail.html?id=${product.id}')"
                            >${product.name }</a></dt>
                            <!-- dt 25个文字+... -->
                            <dd class="h40">${product.name }</dd>
                            <dd class="orange">￥${product.price }</dd>
                            <dd>北京有货</dd>
                            <dd>
                                <a href="#" title="加入购物车" class="inb btn70x21 mr">加入购物车</a>
                            </dd>
                        </dl>
                        <img src="jsutils/img/pic/hot.gif" alt="热门" class="type"/>
                    </li>
                </c:forEach>
            </ul>
            <div class="page pb15">
						<span class="r inb_a page_b">
							<c:forEach items="${pageInfo.pageView }" var="page">
                                ${page}
                            </c:forEach>
						</span>
            </div>
        </div>
    </div>
</div>
<div class="mode">
    <div class="tl"></div>
    <div class="tr"></div>
    <ul class="uls">
        <li class="first">
            <span class="guide"></span>
            <dl>
                <dt title="购物指南">购物指南</dt>
                <dd>
                    <a href="#" title="购物流程">购物流程</a>
                </dd>
                <dd>
                    <a href="#" title="购物流程">购物流程</a>
                </dd>
                <dd>
                    <a href="#" target="_blank" title="联系客服">联系客服</a>
                </dd>
                <dd>
                    <a href="#" target="_blank" title="联系客服">联系客服</a>
                </dd>
            </dl>
        </li>
        <li>
            <span class="way"></span>
            <dl>
                <dt title="支付方式">支付方式</dt>
                <dd>
                    <a href="#" title="货到付款">货到付款</a>
                </dd>
                <dd>
                    <a href="#" title="在线支付">在线支付</a>
                </dd>
                <dd>
                    <a href="#" title="分期付款">分期付款</a>
                </dd>
                <dd>
                    <a href="#" title="分期付款">分期付款</a>
                </dd>
            </dl>
        </li>
        <li>
            <span class="help"></span>
            <dl>
                <dt title="配送方式">配送方式</dt>
                <dd>
                    <a href="#" title="上门自提">上门自提</a>
                </dd>
                <dd>
                    <a href="#" title="上门自提">上门自提</a>
                </dd>
                <dd>
                    <a href="#" title="上门自提">上门自提</a>
                </dd>
                <dd>
                    <a href="#" title="上门自提">上门自提</a>
                </dd>
            </dl>
        </li>
        <li>
            <span class="service"></span>
            <dl>
                <dt title="售后服务">售后服务</dt>
                <dd>
                    <a href="#" target="_blank" title="售后策略">售后策略</a>
                </dd>
                <dd>
                    <a href="#" target="_blank" title="售后策略">售后策略</a>
                </dd>
                <dd>
                    <a href="#" target="_blank" title="售后策略">售后策略</a>
                </dd>
                <dd>
                    <a href="#" target="_blank" title="售后策略">售后策略</a>
                </dd>
            </dl>
        </li>
        <li>
            <span class="problem"></span>
            <dl>
                <dt title="特色服务">特色服务</dt>
                <dd>
                    <a href="#" target="_blank" title="手机淘宝">手机淘宝</a>
                </dd>
                <dd>
                    <a href="#" target="_blank" title="手机淘宝">手机淘宝</a>
                </dd>
                <dd>
                    <a href="#" target="_blank" title="手机淘宝">手机淘宝</a>
                </dd>
                <dd>
                    <a href="#" target="_blank" title="手机淘宝">手机淘宝</a>
                </dd>
            </dl>
        </li>
    </ul>
</div>
</body>

</html>