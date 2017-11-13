<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${path}/js/ajax.js"></script>
<div id="Top">
	<div class="tops"></div>
	<div class=" block header_bg" style="margin-bottom:0px;">
		<div class="top_nav_header">
			<div class="top_nav">
				<script type="text/javascript">
					//初始化主菜单
					function sw_nav(obj, tag) {
						var DisSub = document.getElementById("DisSub_" + obj);
						var HandleLI = document.getElementById("HandleLI_"
								+ obj);
						if (tag == 1) {
							DisSub.style.display = "block";
						} else {
							DisSub.style.display = "none";
						}
					}
					ajax({
						url: "http://localhost:8080/myday20_supermarket/ajaxAllCartgoryServlet", // 请求路径
						method: "post", // 请求方式
						async: true, // 同步还是异步，默认true
						callback: function(result) {
							var data=JSON.parse(result);
							var mydiv=document.getElementById("div");
							for ( var i in data) {
								var a=document.createElement("a");
								a.setAttribute("href", "javascript:;")
								a.setAttribute("onclick", "findProductByCategory('"+data[i].cid+"')");
								a.innerHTML=data[i].cname;
								mydiv.appendChild(a);							
							}					
						},
						cache: false// 是否使用缓存
					});
				</script>
				<div class="block">
					<div class="f_l left_login">
						<script type="text/javascript" src="js/transport.js"></script>
						<script type="text/javascript" src="js/utils.js"></script>
						<font id="ECS_MEMBERZONE"> 欢迎光临本店，<a href="login.jsp">登录</a>
							| <a href="register.jsp">注册</a>
						</font>
					</div>
					<ul class="top_bav_l">
						<li class="top_sc">&nbsp;&nbsp;<a href="javascript:void(0);"
							onclick="AddFavorite('我的网站',location.href)">收藏本站</a></li>
						<li>&nbsp;&nbsp;&nbsp;关注我们：</li>
						<li style="border:none" class="menuPopup"
							onMouseOver="sw_nav(1,1);" onMouseOut="sw_nav(1,0);"><a
							id="HandleLI_1" href="javascript:;" title="微博" class="attention"></a>
							<div id=DisSub_1 class="top_nav_box top_weibo">
								<a href="http://weibo.com/itcast?is_hot=1" target="_blank"
									title="新浪微博" class="top_weibo"></a> <a
									href="http://t.qq.com/myjayen" target="_blank" title="QQ微博"
									class="top_qq"></a>
							</div></li>
						<li class="menuPopup" onMouseOver="sw_nav(2,1);"
							onMouseOut="sw_nav(2,0);"><a id="HandleLI_2"
							href="javascript:;" title="微信" class="top_weixin"></a>
							<div id="DisSub_2" class="weixinBox" style="display: none;">
								<table width="100%" cellpadding=0 cellspacing=0 align="center">
									<tr>
										<td><img src="images/wx_cz.jpg"
											style="width:90px; height:90px;background:#0000CC" width="90"
											height="90"></td>
										<td><img src="images/wx_hm.jpg"
											style="width:90px; height:90px;background:#0000CC" width="90"
											height="90"></td>
									</tr>
									<tr>
										<td>传智播客官方微信</td>
										<td>黑马程序员官方微信</td>
									</tr>
								</table>
							</div></li>
					</ul>
					<div class="header_r">
						<a href="${path}/orderServlet?method=findMyOrderInfo">我的订单</a>
						<a href="${path}/productServlet?method=findAll">商品列表</a>
					</div>
				</div>
			</div>
		</div>
		<div class="clear_f"></div>
		<div class="header_top logo_wrap clearfix">
			<a class="logo_new" href="javascript:;"><img
				src="themes/ecmoban_jumei/images/logo.gif" /></a>
			<div class="ser_n">
				<form id="searchForm" class="searchBox clearfix" name="searchForm"
					onsubmit="return false;" method="get" action="">
					<span class="ipt1"><input name="keywords"
						placeholder="请输入您想购买的商品" type="text" id="keyword" value=""
						class="searchKey" /></span> <span class="ipt2"><input
						type="submit" name="imageField" class="fm_hd_btm_shbx_bttn"
						value="搜  索"></span>
				</form>
				<div class="clear_f"></div>
				<ul class="searchType none_f"></ul>
			</div>
			<ul class="cart_info">
				<li id="ECS_CARTINFO">
					<div class="top_cart">
						<img src="themes/ecmoban_jumei/images/cart.gif" /> <span
							class="carts_num none_f"><a href="cart.jsp" title="查看购物车">0</a></span>
						<a href="${path}/cartServlet?method=findAll" class="shopborder">去购物车结算</a>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
<div style="clear:both"></div>
<div class="menu_box clearfix">
	<%
		String uri = request.getRequestURI();
		boolean isIndex = uri.equals(request.getContextPath() + "/")
				|| uri.indexOf("index.jsp") > -1;
		String width = isIndex ? "1200px;" : "840px;";
		String className = isIndex ? "cur" : "";
	%>
	<div class="block" style="width:<%=width%>">
		<div class="menu" id="div">
			<a id="menu_index" href="index.jsp" class="<%=className%>">首页</a> 

		</div>
	</div>
</div>
<link href="themes/ecmoban_qq/images/qq.css" rel="stylesheet"
	type="text/css" />
<div class="QQbox" id="divQQbox" style="width: 170px; ">
	<div class="Qlist" id="divOnline" onmouseout="hideMsgBox(event);"
		style="display: none; " onmouseover="OnlineOver();">
		<div class="t"></div>
		<div class="infobox">
			我们营业的时间<br>9:00-18:00
		</div>
		<div class="con">
			<ul>
				<li><a href="javascript:;" target="_blank"><img
						src="images/button_old_41.gif" height="16" border="0" alt="QQ" />&nbsp;&nbsp;666666666</a></li>
				<li><a href="javascript:;" target="_blank"><img
						src="images/button_old_41.gif" height="16" border="0" alt="QQ" />&nbsp;&nbsp;888888888</a></li>
				<li><a href="javascript:;" target="_blank"><img
						src="images/button_old_41.gif" height="16" border="0" alt="QQ" />&nbsp;&nbsp;999999999</a></li>
				<li>服务热线: 88888888</li>
			</ul>
		</div>
		<div class="b"></div>
	</div>
	<div id="divMenu" onmouseover="OnlineOver();" style="display: block; ">
		<img src="themes/ecmoban_qq/images/qq_1.gif" class="press" alt="在线咨询">
	</div>
</div>