package com.makro.bean;
/**
 * 钉钉实例ID和业务系统单据ID对应关系
 * @author Thierry.Zhuang
 *
 */
public class DingTalkProcess {
	private String processInstanceId;
	private String busid;
	private String busType; // 目前只有一种协议价，暂不分类
	private String dtTitle; // 标题里有UAT标志位
	private int isValid;
	
	public String getDtTitle() {
		return dtTitle;
	}
	public void setDtTitle(String dtTitle) {
		this.dtTitle = dtTitle;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getBusid() {
		return busid;
	}
	public void setBusid(String busid) {
		this.busid = busid;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public int getIsValid() {
		return isValid;
	}
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	public DingTalkProcess(String processInstanceId, String busid, String busType, String dtTitle, int isValid) {
		super();
		this.processInstanceId = processInstanceId;
		this.busid = busid;
		this.busType = busType;
		this.dtTitle = dtTitle;
		this.isValid = isValid;
	}
	public DingTalkProcess() {
		
	}
	@Override
	public String toString() {
		return "DingTalkProcess [processInstanceId=" + processInstanceId + ", busid=" + busid + ", busType=" + busType
				+ ", dtTitle=" + dtTitle + "]";
	}
}
