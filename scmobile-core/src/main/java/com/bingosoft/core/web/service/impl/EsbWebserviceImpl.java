package com.bingosoft.core.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bingosoft.core.web.service.IEsbWebservice;
import com.bingosoft.models.dto.UserGoodsQryDto;
import com.bingosoft.models.dto.UserOrdQryDto;
import com.bingosoft.models.entities.EsbHeaderBody;
import com.bingosoft.models.entities.EsbResponseRoot;
import com.bingosoft.models.entities.EsbRoot;
import com.bingosoft.models.entities.EsbRouting;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.utils.crypt.SignUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EsbWebserviceImpl implements IEsbWebservice {

	private static final Logger logger = LoggerFactory.getLogger(EsbWebserviceImpl.class);
	
	String base = "223.87.14.70:18084/esbWS/rest";

	@Override
	public void sUserGoodsQry(String phoneNo) {
		// TODO Auto-generated method stub
		String httpUrl = String.format("http://%s/sUserGoodsQry", base);
		EsbResponseRoot json = new EsbResponseRoot();

		try {
			EsbRoot<UserGoodsQryDto> postJson = new EsbRoot<UserGoodsQryDto>();
			UserGoodsQryDto dto = new UserGoodsQryDto();
			dto.setUserMobile(phoneNo);
			EsbRouting routing=new EsbRouting();
			routing.setROUTE_VALUE(phoneNo);
			EsbHeaderBody header=new EsbHeaderBody();
			header.setROUTING(routing);
			postJson.setBODY(dto);
			postJson.setHEADER(header);
			Map<String,Object> root=new HashMap<String,Object>();
			root.put("ROOT", postJson);
			logger.info(httpUrl);
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();//new Gson();
			String postBody = gson.toJson(root);//JSON.toJSONString(postJson);
		    logger.info(postBody);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl)
					.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody)).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			json = JSON.parseObject(result, new TypeReference<EsbResponseRoot>() {
			});

		} catch (Exception e) {
			logger.error(httpUrl);
			logger.error("sUserGoodsQry:" + e.getMessage() + e.getStackTrace());
		}
	}

	@Override
	public void sUserOrdQry(String phoneNo) {
		// TODO Auto-generated method stub
		String httpUrl = String.format("http://%s/sUserOrdQry", base);
		RestResponseOutputDto<Object> json = new RestResponseOutputDto<Object>();

		try {
			EsbRoot<UserOrdQryDto> postJson = new EsbRoot<UserOrdQryDto>();
			UserOrdQryDto dto = new UserOrdQryDto();
			EsbRouting routing=new EsbRouting();
			EsbHeaderBody header=new EsbHeaderBody();
			header.setROUTING(routing);
			routing.setROUTE_VALUE(phoneNo);
			//dto.setSERVICE_NO(phoneNo);
			dto.setPHONE_NO(phoneNo);
			postJson.setBODY(dto);
			postJson.setHEADER(header);
			Map<String,Object> root=new HashMap<String,Object>();
//			Map<String,Object> headerMap=new HashMap<String,Object>();
//			Map<String,Object> rootMap=new HashMap<String,Object>();
//			Map<String,Object> routingMap=new HashMap<String,Object>();
//			headerMap.put("POOL_ID", "31");
//			headerMap.put("CONTACT_ID", "412013120310345678901234561");
//			headerMap.put("CHANNEL_ID", "12");
//			headerMap.put("USERNAME", "mmmmmm5");
//			headerMap.put("PASSWORD", "LWwySLxHcD8=");
//			headerMap.put("ENV_ID", "10.105.16.42");
//			headerMap.put("USERNAME", "mmmmmm5");
			
			root.put("ROOT", postJson);
//			routingMap.put("ROUTE_KEY", "10");
//			routingMap.put("ROUTE_VALUE",phoneNo);
//			headerMap.put("ROUTING", routingMap);
//			Map<String,Object> bodyMap=new HashMap<String,Object>();
//			bodyMap.put("LOGIN_NO", "a18610");
//			bodyMap.put("PHONE_NO", phoneNo);
//			bodyMap.put("WORN_SERV_CODE", "sQryFamWlanCfm");
//			Map<String,String> commonInfoMap=new HashMap<String,String>();
//			commonInfoMap.put("PROVINCE_GROUP", "10008");
//			bodyMap.put("COMMON_INFO", commonInfoMap);
//			rootMap.put("HEADER", headerMap);
//			rootMap.put("BODY", bodyMap);
//			root.put("ROOT", rootMap);
			logger.info(httpUrl);
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();//new Gson();
			String postBody = gson.toJson(root);//JSON.toJSONString(postJson);
		    logger.info(postBody);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl)
					.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody)).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<Object>>() {
			});

		} catch (Exception e) {
			logger.error(httpUrl);
			logger.error("sUserGoodsQry:" + e.getMessage() + e.getStackTrace());
		}
	}

}
