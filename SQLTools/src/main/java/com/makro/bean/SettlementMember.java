package com.makro.bean;

public class SettlementMember {
	private int id;
	private String userNo;
	private int period;
	private String remarks;
	private String createId;
	private int createTime;
	private int isValid;
	private int isDel;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	public int getIsValid() {
		return isValid;
	}
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public SettlementMember(int id, String userNo, int period, String remarks, String createId, int createTime,
			int isValid, int isDel) {
		super();
		this.id = id;
		this.userNo = userNo;
		this.period = period;
		this.remarks = remarks;
		this.createId = createId;
		this.createTime = createTime;
		this.isValid = isValid;
		this.isDel = isDel;
	}
	public SettlementMember() {
		super();
	}
	@Override
	public String toString() {
		return "SettlementMember [id=" + id + ", userNo=" + userNo + ", period=" + period + ", remarks=" + remarks
				+ ", createId=" + createId + ", createTime=" + createTime + ", isValid=" + isValid + ", isDel=" + isDel
				+ "]";
	}
}
