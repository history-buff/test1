<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的购物车</title>
<%@include file="inc/common_head.jsp"%>
<script type="text/javascript">
function changeCartNum(buynum,pid){
	ajax({
		url: "${path}/cartServlet?method=changeCartNum", // 请求路径
		method: "post", // 请求方式
		params:"buynum="+buynum+"&pid="+pid,
		async: true, // 同步还是异步，默认true
		callback: function(result) {
			document.getElementById("xiaoji"+pid).innerHTML=result.split("-")[0];
			document.getElementById("totalshop").innerHTML=result.split("-")[1];
			document.getElementById("totalsave").innerHTML=result.split("-")[2];
			
			},
		cache: false// 是否使用缓存
	});
	
}
</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block table">
		<div class="AreaR">
			<div class="block box">
				<div class="blank"></div>
				<div id="ur_here">
					当前位置: <a href="index.jsp">首页</a><code>&gt;</code>我的购物车
				</div>
			</div>
			<div class="blank"></div>
			<div class="box">
				<div class="box_1">
					<div class="userCenterBox boxCenterList clearfix"
						style="_height:1%;">
						<h5><span>我的购物车</span></h5>
						<table width="100%" align="center" border="0" cellpadding="5"
							cellspacing="1" bgcolor="#dddddd">
							<tr>
								<th bgcolor="#ffffff">商品名称</th>
								<th bgcolor="#ffffff">市场价</th>
								<th bgcolor="#ffffff">本店价</th>
								<th bgcolor="#ffffff">购买数量</th>
								<th bgcolor="#ffffff">小计</th>
								<th bgcolor="#ffffff" width="160px">操作</th>
							</tr>
								<!-- 设置变量接受 商品市场的总价 以及 estore商城总价 -->
							<c:set var="totalshopprice" value="0.0"></c:set>
							<c:set var="totalmarketprice" value="0.0"></c:set>
							<c:forEach var="product" items="${cart}">
								<tr>
								<td bgcolor="#ffffff" align="center" style="width:300px;">
									<!-- 商品图片 -->
									<a href="javascript:;" target="_blank">
										<img style="width:80px; height:80px;"
										src="http://localhost:8080/myday20_supermarket/${product.pimage}"
										border="0" title="佳洁士全优7效牙膏+漱口水装" />
									</a><br />
									<!-- 商品名称 -->
									<a href="javascript:;" target="_blank" class="f6">${product.pname}</a>
								</td>
								<td align="center" bgcolor="#ffffff">${product.market_price}</td>
								<td align="center" bgcolor="#ffffff">${product.shop_price}</td>
								<td align="center" bgcolor="#ffffff">
									<input id="my${product.pid}" onblur="changeCartNum(this.value,'${product.pid}');" value="${product.buynum}" size="4" class="inputBg" style="text-align:center;" />
								</td>
								<td id="xiaoji${product.pid}" align="center" bgcolor="#ffffff">${product.shop_price*product.buynum}元</td>
								<td align="center" bgcolor="#ffffff">
									<a href="javascript:;" class="f6">删除</a>
								</td>
							</tr>
								<!-- 每一次循环 累加一次 市场总价和estore商城总价 pageContext.setAttribute("totalestoreprice","0.0") -->
							<c:set var="totalshopprice" value="${product.shop_price*product.buynum+totalshopprice}"></c:set>
							<c:set var="totalmarketprice" value="${product.market_price*product.buynum+totalmarketprice}"></c:set>
							
							</c:forEach>
							
						
							<tr>
								<td colspan="6" style="text-align:right;padding-right:10px;font-size:25px;">
									购物金额小计&nbsp;<font id="totalshop" color="red">${totalshopprice}</font>元，
									共为您节省了&nbsp;<font id="totalsave" color="red">${totalmarketprice-totalshopprice}</font>元
									<a href="${path}/orderServlet?method=orderList"><input value="去结算" type="button" class="btn" /></a>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="blank"></div>
		<div class="blank5"></div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>
