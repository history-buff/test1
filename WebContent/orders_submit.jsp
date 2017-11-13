<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<title>提订单</title>
<%@include file="inc/common_head.jsp"%>
</head>
<script type="text/javascript">
//为了将省市区的表示值拿到
	function go(){
		var data=document.getElementById("province").selectedOptions[0].text+document.getElementById("city").selectedOptions[0].text
		+document.getElementById("district").selectedOptions[0].text;
		document.getElementById("ssq").value=data;
		return true;
	}

</script>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block clearfix"><div class="AreaR">
	<div class="block box"><div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href="index.jsp">首页</a><code>&gt;</code>购物流程
		</div>
	</div><div class="blank"></div><div class="box"><div class="box_1">
	<div class="userCenterBox boxCenterList clearfix" style="_height:1%;">
	<form action="${path}/orderServlet?method=generateOrder" method="post" onsubmit="return go();">
		<!---------收货人信息开始---------->
		<h5><span>收货人信息</span></h5>
		<table width="100%" align="center" border="0" cellpadding="5"
			cellspacing="1" bgcolor="#dddddd">
			<tr>
				<td bgcolor="#ffffff" align="right" width="120px">区域信息：</td>
				<td bgcolor="#ffffff">
					<!-- 省 -->
					<input type="hidden" id="ssq" name="ssq">
					<select id="province" name="province" onchange="change(value,city);">
						<option value="none">-- 请选择省 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<!-- 市 -->
					<select id="city" name="city" onchange="change(value,district);">
						<option value="none">-- 请选择市 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<!-- 县(区) -->
					<select id="district" name="district">
						<option value="none">-- 请选择县(区) --</option>
					</select>
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">详细地址：</td>
				<td bgcolor="#ffffff">
					<input style="width:347px;" id="detailAddress" name="detailAddress"/>
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">邮政编码：</td>
				<td bgcolor="#ffffff"><input id="postcode" name="postcode"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">收货人姓名：</td>
				<td bgcolor="#ffffff"><input id="name" name="acceptPerson"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">联系电话：</td>
				<td bgcolor="#ffffff"><input id="phone" name="phone"/></td>
			</tr>
		</table>
		<!---------收货人信息结束---------->
		
		<!----------商品列表开始----------->
		<div class="blank"></div>
		<h5><span>商品列表</span></h5>
		<table width="100%" border="0" cellpadding="5" cellspacing="1"
			bgcolor="#dddddd">
			<tr>
				<th width="30%" align="center">商品名称</th>
				<th width="22%" align="center">市场价格</th>
				<th width="22%" align="center">商品价格</th>
				<th width="15%" align="center">购买数量</th>
				<th align="center">小计</th>
			</tr>
			<c:set var="totalprice" value="0.0"></c:set>
			<c:forEach var="p" items="${products}">
				<tr>
				<td>
					<a href="javascript:;" class="f6">${p.pname}</a>
				</td>
				<td>${p.market_price }元</td>
				<td>${p.shop_price}元</td>
				<td align="center">${p.buynum}</td>
				<td>${p.shop_price*p.buynum}元</td>
				<c:set var="totalprice" value="${totalprice+p.shop_price*p.buynum}"></c:set>
				
			</tr>
			</c:forEach>
			
			
			<tr>
				<td colspan="5" style="text-align:right;padding-right:10px;font-size:25px;">
					商品总价&nbsp;<font color="red">&yen;${totalprice}</font>元
					<input type="hidden" name="totalprice" value="${totalprice}">
					<input type="submit" value="提交订单" class="btn" />
				</td>
			</tr>
		</table>
		<!----------商品列表结束----------->
	</form>
	</div></div></div></div></div>
	<%@include file="inc/footer.jsp"%>
</body>
<script type="text/javascript">
change(0,province);
function change(pid,target){
	target.length=1;
	district.length=1;
	ajax({
		url: "${path}/loadCityServlet?method=loadCity&pid="+pid, // 请求路径
		method: "post", // 请求方式
		async: true, // 同步还是异步，默认true
		callback: function(result) {
			//alert(result);
		 	if(result==null){
				return;
			}
			 var arr=JSON.parse(result);
			 for (var i = 0; i < arr.length; i++) {
					var opt = document.createElement("option");
					opt.value = arr[i].id;
					opt.innerHTML = arr[i].name;
					target.appendChild(opt);
				}

			
			 },
		cache: false// 是否使用缓存
		});
	
	
}
</script>
</html>