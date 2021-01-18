package com.makro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCalendarListRequest.DateTime;
import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.makro.bean.User;
import com.makro.config.DingTalkConstant;
import com.makro.utils.DingTalkEncryptor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RestController
public class DingtalkController {
	/**
	 * 先写死下：
	 */
	private static final String ACCESS_TOKEN = "851df29f17b830439e643f9899a72e72";
	/**
     * 创建应用，验证回调URL创建有效事件（第一次保存回调URL之前）
     */
	// bpms_task_change	  审批任务开始，结束，转交
	// bpms_instance_change  审批实例开始，结束
	
    private static final String TASKCHANGE = "bpms_task_change";
    private static final String INSTANCECHANGE = "bpms_instance_change";


    @PostMapping(value = "dingCallback")
    public Object dingCallback(
        @RequestParam(value = "signature") String signature,
        @RequestParam(value = "timestamp") Long timestamp,
        @RequestParam(value = "nonce") String nonce,
        @RequestBody(required = false) JSONObject  body
    ) {
        String params = "signature:" + signature + " timeStamp:" + timestamp + " nonce:" + nonce + " body:" + body;
        try {
        	System.out.print("--------------------------------------------------------------\n");
            System.out.print("begin callback:" + params);
        	System.out.print("--------------------------------------------------------------\n");
            DingTalkEncryptor dingTalkEncryptor = 
            		new DingTalkEncryptor(DingTalkConstant.TOKEN, 
            				DingTalkConstant.ENCODING_AES_KEY, DingTalkConstant.CORPID);

            // 从post请求的body中获取回调信息的加密数据进行解密处理
            if (body != null) {
            	String encrypt = body.getString("encrypt");
            	if (encrypt != null) {
            		 String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp.toString(), nonce, encrypt);
                     System.out.println("CallBack的内容：" + plainText);
            		 JSONObject callBackContent = JSON.parseObject(plainText);
                     // 根据回调事件类型做不同的业务处理
                     String eventType = callBackContent.getString("EventType");
                     if (TASKCHANGE.equals(eventType)) {
                         System.out.print("审批任务开始、结束、转移: " + plainText);
                     } else if (INSTANCECHANGE.equals(eventType)) {
                         System.out.print("审批实例开始、结束: " + plainText);
                     } else if ("check_url".equals(eventType)) {
                         System.out.println("Check_url成功！！！");
                     }
            	}
            }
            
            // 返回success的加密信息表示回调处理成功
            return dingTalkEncryptor.getEncryptedMap("success", timestamp, nonce);
        } catch (Exception e) {
        	e.printStackTrace();
            //失败的情况，应用的开发者应该通过告警感知，并干预修复
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
	 */}
