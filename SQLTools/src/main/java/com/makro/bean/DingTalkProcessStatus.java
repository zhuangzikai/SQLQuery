package com.makro.bean;
/**
 * 记录钉钉返回的流程审批信息
 * @author Thierry.Zhuang
 *
 */
public class DingTalkProcessStatus {
	private int id;
	private String uuid;
	private String processInstanceId;
	private Long finishTime;
	private String eventType;
	private String businessId;
	private String title;
	private String processType;
	private String processResult;
	private Long createTime;
	private String processCode;
	private String proFlag;
	public DingTalkProcessStatus(int id, String uuid, String processInstanceId, Long finishTime, String eventType,
			String businessId, String title, String processType, String processResult, Long createTime,
			String processCode, String proFlag) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.processInstanceId = processInstanceId;
		this.finishTime = finishTime;
		this.eventType = eventType;
		this.businessId = businessId;
		this.title = title;
		this.processType = processType;
		this.processResult = processResult;
		this.createTime = createTime;
		this.processCode = processCode;
		this.proFlag = proFlag;
	}
	public DingTalkProcessStatus(String uuid, String processInstanceId, Long finishTime, String eventType,
			String businessId, String title, String processType, String processResult, Long createTime,
			String processCode, String proFlag) {
		super();
		this.uuid = uuid;
		this.processInstanceId = processInstanceId;
		this.finishTime = finishTime;
		this.eventType = eventType;
		this.businessId = businessId;
		this.title = title;
		this.processType = processType;
		this.processResult = processResult;
		this.createTime = createTime;
		this.processCode = processCode;
		this.proFlag = proFlag;
	}
	public DingTalkProcessStatus() {

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getProcessResult() {
		return processResult;
	}
	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getProFlag() {
		return proFlag;
	}
	public void setProFlag(String proFlag) {
		this.proFlag = proFlag;
	}
	@Override
	public String toString() {
		return "DingTalkProcessStatus [id=" + id + ", uuid=" + uuid + ", processInstanceId=" + processInstanceId
				+ ", finishTime=" + finishTime + ", eventType=" + eventType + ", businessId=" + businessId + ", title="
				+ title + ", processType=" + processType + ", processResult=" + processResult + ", createTime="
				+ createTime + ", processCode=" + processCode + ", proFlag=" + proFlag + "]";
	}
	
}
