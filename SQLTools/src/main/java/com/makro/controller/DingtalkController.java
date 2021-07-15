package com.makro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.makro.bean.DingTalkProcess;
import com.makro.bean.DingTalkProcessStatus;
import com.makro.config.DataSourceConstants;
import com.makro.config.DingTalkConstant;
import com.makro.config.DynamicDataSourceContextHolder;
import com.makro.config.LogFilter;
import com.makro.service.DingTalkService;
import com.makro.utils.DingTalkEncryptor;
import com.makro.utils.HttpUtils;
import com.makro.utils.StrUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉回调接口
 * @author Thierry.Zhuang
 *
 */
@RestController
public class DingtalkController {
    private static final String TASKCHANGE = "bpms_task_change"; // 审批任务开始，结束，转交
    private static final String INSTANCECHANGE = "bpms_instance_change"; // bpms_instance_change  审批实例开始，结束
    // private static final String CONTRACT_PROCODE = "PROC-93362503-6C36-41EF-BF43-A1C4152C9E45"; // 流程ID在钉钉流程编辑界面的URL中获取
    private static Logger logger = LoggerFactory.getLogger(DingtalkController.class);
    
    @Autowired
	private DingTalkService dingTalkService;
    
    @Value("${efurl}")
    private String efurl;
    
    @Value("${CorpId}")
    private String corpId;
    
    @Value("${AppKey}")
    private String appKey;
    
    @Value("${AppSecret}")
    private String appSecret;
    
    @Value("${ContractPriceId}")
    private String CONTRACT_PROCODE;
    
    @Value("${CreditId}")
    private String CRD_PROCODE;
    
    @Value("${SettlementId}")
    private String Settlement_PROCODE;
    
    //TODO MKActiveId?
    
    /**
     * 回调接口，应用里所有的回调都会经过这里，不仅仅包含流程。
     * 流程里分任务和实例，每个审批环节提交都有任务状态变更，审批开始结束会有实例状态变更。
     * @param signature 消息体签名
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @param body 字符串加密值
     * @return success加密值
     */
    @PostMapping(value = "dingCallback")
    @LogFilter("审批流程回调接口")
    public Object dingCallback(
        @RequestParam(value = "signature") String signature,
        @RequestParam(value = "timestamp") Long timestamp,
        @RequestParam(value = "nonce") String nonce,
        @RequestBody(required = false) JSONObject  body
    ) {
        try {
            DingTalkEncryptor dingTalkEncryptor = 
            		new DingTalkEncryptor(DingTalkConstant.TOKEN, 
            				DingTalkConstant.ENCODING_AES_KEY, corpId);
            // 从post请求的body中获取回调信息的加密数据进行解密处理
            if (body != null) {
            	String encrypt = body.getString("encrypt");
            	if (encrypt != null) {
            		 String plainText = "";
            		 try {
            			 plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp.toString(), nonce, encrypt);
					} catch (com.makro.utils.DingTalkEncryptor.DingTalkEncryptException e) {
						logger.error("CorpId：", corpId + "\n");
						logger.error("解密报错：", e);
					}
            		 JSONObject callBackContent = JSON.parseObject(plainText);
            		 if (callBackContent != null) {
            			 DynamicDataSourceContextHolder.setContextKey(DataSourceConstants.DS_KEY_OA);
            			// 根据回调事件类型做不同的业务处理
                         String eventType = callBackContent.getString("EventType");
                         if (TASKCHANGE.equals(eventType)) {
                             
                         } else if (INSTANCECHANGE.equals(eventType)) {
                             logger.debug("审批实例开始、结束, 解密内容: " + plainText);
                             String processCode = callBackContent.getString("processCode");
                             String type = callBackContent.getString("type");
                             String result = callBackContent.getString("result");
                             String processInstanceId = callBackContent.getString("processInstanceId");
                             String businessId = callBackContent.getString("businessId");
                             String title = callBackContent.getString("title");
                             Long createTime = callBackContent.getLong("createTime");
                             Long finishTime = callBackContent.getLong("finishTime");
                             if (CONTRACT_PROCODE.equalsIgnoreCase(processCode)) {
                            	 // 协议价审批
                            	 DingTalkProcessStatus processStatus = new DingTalkProcessStatus(
                        				 StrUtils.getUUID(), processInstanceId, finishTime, eventType, businessId, title, type, 
                        				 result, createTime, processCode, "CP");
                            	 String interfaceStr = "/omc-work-webin/rest?method={billaudit}&ent_id={entid}";
                            	 String billaudit = "";
                            	 String entid = "0";
                            	 int isValid = 1; // 1-已提交，2-已否决，3-已通过，0-已取消
                            	 if ("finish".equalsIgnoreCase(type)) {
                            		 if ("agree".equalsIgnoreCase(result)) {
                            			 isValid = 3;
                            			 billaudit = "omc.work.event.billaudit";
                            			 logger.info("ProcessInstanceId: " + processInstanceId + "   协议价审批通过!");
                            		 } else if ("refuse".equalsIgnoreCase(result) || "terminate".equalsIgnoreCase(result)) {
                            			 isValid = 2;
                            			 billaudit = "omc.work.event.back";
                            			 logger.info("ProcessInstanceId: " + processInstanceId + "   协议价审批否决或撤回!");
                            		 } else {
                            			 logger.error("审批状态出错！");
                            		 }
                            		 try {
                            			 DingTalkProcess dtprocess = dingTalkService.getBusIdByInstanceId(processInstanceId);
                            			 if (dtprocess == null) {
                            				logger.error("未能找到Efuture协议价单据ID对应关系 processInstanceId: " + processInstanceId); 
                            				return "";
                            			 }
                            			 String busid = dtprocess.getBusid();
											/*
											 * String dtTitle = dtprocess.getDtTitle(); String authorizeUrl = ""; //
											 * E-future 协议价接口 if (dtTitle != null && dtTitle.startsWith("UAT")) { //
											 * BAS抽数计算没写UAT所以用标志位区分 authorizeUrl = "https://wkluat.makrochina.com.cn" +
											 * endURL; logger.info("UAT ing ..."); } else { authorizeUrl =
											 * "https://wklprd.makrochina.com.cn/" + endURL; }
											 */
                            			 JSONObject params = new JSONObject();
                            			 params.put("billno", busid);// E-future 协议价单据编号
                            			 if ("refuse".equalsIgnoreCase(result) || "terminate".equalsIgnoreCase(result)) {
                            				 params.put("memo", "请查看钉钉审批意见"); // E-future 意见备注 
                            			 }
                            			 // 请求E-future协议价接口
                            			 logger.info("\n接口请求信息:\nURL: " + efurl + interfaceStr + "\nServer:" + billaudit + "\nbillno:" + busid);
                            			 JSONObject rs = HttpUtils.sendPostRequest(efurl + interfaceStr, params, 
                            					 MediaType.TEXT_PLAIN, billaudit, entid);
                            			 logger.info("\n接口返回信息:\n" + rs.toJSONString());
                            			 String returncode = rs.getString("returncode");
                            			 if ("0".equals(returncode)) {
                            				// 返回成功，钉钉审批结果写库
                            				 dingTalkService.addDingTalkProcessStatus(processStatus); 
                            				 dingTalkService.updateDingTalkProcess(processInstanceId, isValid);
                            			 } else {
                            				 logger.error("返回失败未写库：" + returncode);
                            			 }
									} catch (Exception e) {
										logger.error("调用业务接口及写库报错：",e);
									}
                            	 } else if ("start".equalsIgnoreCase(type)) {
                            		 logger.info("ProcessInstanceId: " + processInstanceId + "   提交审批!");
                            	 } else {
                            		 logger.warn("ProcessInstanceId: " + processInstanceId + "   其他异常状态： " + type);
                            	 }
                             } else if (Settlement_PROCODE.equalsIgnoreCase(processCode)) {
                            	 // SFA结算周期审批
                            	 // 2、获取staffid
                            	 // 3、获取员工工号, 若不存在则插入staffid
                            	 // 4、操作时间
                            	 // 5、反查钉钉流程字段：会员号，会员名称、申请理由、周期
                            	 // 6、校验会员member_table，如不存在仍然插入，只是备注改为【会员不存在】
                            	 // 7、若存在settlte_member，则update，如不存在，则insert
                            	 
                            	 
                            	 
                             } else if (CRD_PROCODE.equalsIgnoreCase(processCode)) {
                            	 // TODO 授信审批
                             }
                         } else if ("check_url".equals(eventType)) {
                             logger.info("Check_url成功！！！ CorpId: " + corpId);
                         } 
            		 } else {
            			 logger.warn("解密失败，plainText cannot be json: signature: "+signature+", timestamp: "+timestamp+", nonce: "+nonce+", encrypt: "+encrypt);
            		 }
            	}
            }
           
            // 返回success的加密信息表示回调处理成功
            // return dingTalkEncryptor.getEncryptedMap("success", timestamp, nonce);
            Map<String, String> successMap = dingTalkEncryptor.getEncryptedMap("success");
			return successMap;
        } catch (Exception e) {
        	logger.error("未定义报错：", e);
        	Map<String, String> resultMap = new HashMap<String, String>();
        	resultMap.put("msg_signature", signature);
            resultMap.put("encrypt", "fail");
            resultMap.put("timeStamp", String.valueOf(timestamp));
            resultMap.put("nonce", nonce);
            return resultMap;
        }
    }
	/*
	 * public static void main(String[] args) throws Exception{ // 先删除企业已有的回调 //
	 * DingTalkClient client = new
	 * DefaultDingTalkClient("https://api.makrogo.com/dingtalk/eventreceive"); //
	 * OapiCallBackDeleteCallBackRequest request = new
	 * OapiCallBackDeleteCallBackRequest(); // request.setHttpMethod("GET"); //
	 * client.execute(request, ACCESS_TOKEN); // // // 重新为企业注册回调 // client = new
	 * DefaultDingTalkClient("https://api.makrogo.com/dingtalk/eventreceive"); //
	 * OapiCallBackRegisterCallBackRequest registerRequest = new
	 * OapiCallBackRegisterCallBackRequest(); //
	 * registerRequest.setUrl("https://api.makrogo.com/dingtalk/eventreceive"); //
	 * registerRequest.setAesKey(DingTalkConstant.ENCODING_AES_KEY); //
	 * registerRequest.setToken("123456"); //
	 * registerRequest.setCallBackTag(Arrays.asList("bpms_instance_change",
	 * "bpms_task_change")); // OapiCallBackRegisterCallBackResponse
	 * registerResponse = // client.execute(registerRequest,ACCESS_TOKEN); // if
	 * (registerResponse.isSuccess()) { // System.out.println("回调注册成功了！！！"); // }
	 * 
	 * 
	 * DingTalkEncryptor dingTalkEncryptor = new
	 * DingTalkEncryptor(DingTalkConstant.TOKEN, DingTalkConstant.ENCODING_AES_KEY,
	 * DingTalkConstant.SUITE_KEY); Map<String,String> map =
	 * dingTalkEncryptor.getEncryptedMap("success", Long.valueOf("1610504083202"),
	 * "123456"); //map.get("msg_signature"), map.get("timeStamp"),
	 * map.get("nonce"), map.get("encrypt") String msg =
	 * dingTalkEncryptor.getDecryptMsg( "c387a0d104e526953e4f26d657e0e22fd0834f92",
	 * "1610520892768", "4MJUxa2q",
	 * "96dxII2WVdTPpn8Pq0u+xihhsf/+5mXCAhdN6FI1b+YDbl0ywmImIx7CTqb6jAOo5SoF9Px3pl8DjNFmUjgeUO7wWXasZRtU4Qk5p18cXdn/v2C3U2dwnYpZAwD9aDdu"
	 * ); System.out.println("msg:  "+msg); }
	 */
    
}
