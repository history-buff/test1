<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选购中心</title>
<%@include file="inc/common_head.jsp"%>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block box">
		<div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href="index.jsp">首页</a>
			<code>&gt;</code>
			商品列表
		</div>
	</div>
	<div class="blank"></div>
	<div class="block clearfix">
		<div class="AreaR">
			<div class="box">
				<div class="box_1">
					<h3>
						<span>商品列表</span>
					</h3>
					<!-- 商品列表开始 -->
					<div class="clearfix goodsBox" style="border:none; ">
						<!-- 第1个商品 -->
						<c:forEach var="p" items="${products}">		
							<div class="goodsItem" style="padding: 10px 4px 15px 1px;">
								<a href="${path}/productServlet?method=findProductDatail&pid=${p.pid}"><img class="goodsimg"
									src="http://localhost:8080/myday20_supermarket/${p.pimage}" />
								</a><br />
								<p style=" height:20px; overflow:hidden;">
									<a href="${path}/productServlet?method=findProductDatail&pid=${p.pid}" title="">${p.pname}</a>
								</p>
								市场价：<font class="market">${p.market_price}元</font><br /> 本店价：<font class="f1">${p.shop_price}元
								</font>
							</div>
						</c:forEach>
					</div>
					<!-- 商品列表结束 -->
				</div>
			</div>
		</div>
	</div>
	<%@include file="inc/footer.jsp"%>
	<script type="text/javascript">
		window.onload = function() {
			fixpng();
		}
	</script>
</body>
</html>