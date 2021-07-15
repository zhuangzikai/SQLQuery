package com.makro.utils;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpUtils {
	public static JSONObject sendPostRequest(String url, JSONObject params, MediaType type, Object... uriVariables){
        RestTemplate client = new RestTemplate();
        client.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(type);
        headers.setAccept(Collections.singletonList(type));
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(params.toString(), headers);
        //执行HTTP请求，将返回的结构使用Result类格式化
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class, uriVariables);
        String body = response.getBody();
        JSONObject json = JSONObject.parseObject(body);
        return json;
    }
//	public static void main(String[] args) {
//		JSONObject body = new JSONObject();
//		body.put("a","a"); // 验证SFA接口jdk问题
//		MediaType type = MediaType.APPLICATION_JSON;
//		JSONObject response = sendPostRequest("http://apiuat.makrogo.com/mkAPI/Interaction", body, type, body);
//		System.out.println(response.toJSONString());
//	}
}
