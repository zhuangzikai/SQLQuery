package com.makro.bean;

import java.sql.Date;

public class SalesDetails {
	private int id;
	private String memberCode;
	private String invoiceNo;
	private String onlineOrderNo;
	private String couponOrderNo;
	private String barcode;
	private String code;
	private String catid;
	private String goodsName;
	private String unit;
	private double price;
	private double qty;
	private double amount;
	private double discount;
	private Date orderTime;
	private String orderType;
	private String channel;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getOnlineOrderNo() {
		return onlineOrderNo;
	}
	public void setOnlineOrderNo(String onlineOrderNo) {
		this.onlineOrderNo = onlineOrderNo;
	}
	public String getCouponOrderNo() {
		return couponOrderNo;
	}
	public void setCouponOrderNo(String couponOrderNo) {
		this.couponOrderNo = couponOrderNo;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCatid() {
		return catid;
	}
	public void setCatid(String catid) {
		this.catid = catid;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public SalesDetails(int id, String memberCode, String invoiceNo, String onlineOrderNo, String couponOrderNo,
			String barcode, String code, String catid, String goodsName, String unit, double price, double qty,
			double amount, double discount, Date orderTime, String orderType, String channel) {
		super();
		this.id = id;
		this.memberCode = memberCode;
		this.invoiceNo = invoiceNo;
		this.onlineOrderNo = onlineOrderNo;
		this.couponOrderNo = couponOrderNo;
		this.barcode = barcode;
		this.code = code;
		this.catid = catid;
		this.goodsName = goodsName;
		this.unit = unit;
		this.price = price;
		this.qty = qty;
		this.amount = amount;
		this.discount = discount;
		this.orderTime = orderTime;
		this.orderType = orderType;
		this.channel = channel;
	}
	public SalesDetails() {
		
	}
	@Override
	public String toString() {
		return "SalesDetails [id=" + id + ", memberCode=" + memberCode + ", invoiceNo=" + invoiceNo + ", onlineOrderNo="
				+ onlineOrderNo + ", couponOrderNo=" + couponOrderNo + ", barcode=" + barcode + ", code=" + code
				+ ", catid=" + catid + ", goodsName=" + goodsName + ", unit=" + unit + ", price=" + price + ", qty="
				+ qty + ", amount=" + amount + ", discount=" + discount + ", orderTime=" + orderTime + ", orderType="
				+ orderType + ", channel=" + channel + "]";
	}
	
	
}
