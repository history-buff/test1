package cn.itcast.estore.domain;

import java.sql.Timestamp;
import java.util.List;

public class Order {
	private String id;// uuid生成主键
	private int uid;
	private double totalprice;
	private String address;
	private int status = 0;// 0 未支付 1 已经支付
	private Timestamp createtime;

	private String acceptperson;// 收货人名称
	private String telephone;// 收货人电话
	private String postcode;

	private List<OrderItem> orderItems;// 封装该订单对应的商品信息

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getAcceptperson() {
		return acceptperson;
	}

	public void setAcceptperson(String acceptperson) {
		this.acceptperson = acceptperson;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}
