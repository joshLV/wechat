package com.bingosoft.core.web.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.bingosoft.common.config.UrlConfig;
import com.bingosoft.core.web.service.IOperationService;
import com.bingosoft.models.dto.FansInfoByOauthDto;
import com.bingosoft.models.dto.JsSignatureDto;
import com.bingosoft.models.dto.MobileDto;
import com.bingosoft.models.dto.TemplateMessageInputDto;
import com.bingosoft.utils.serialize.JsonUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Service
public class OperationServiceImpl implements IOperationService {

	private Logger logger = LoggerFactory.getLogger(getClass());



	@Autowired
	UrlConfig urlConfig; // 注入UrlConfig实例

	@Override
	public String getUserInfoByPhone(String accountId, String phone) {
		// TODO Auto-generated method stub
		String url = String.format("http://{url}/operation/api/out/getMobile?accountId={accountId}&phone={phone}", accountId,phone);
		//String json = restTemplate.getForEntity(url, String.class, urlConfig.getDeploy(), accountId, phone).getBody();
		Request request = new Request.Builder().url(url).build();
		String result = "";
        Response response;
		try {
			response = new OkHttpClient().newCall(request).execute();
			result = response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		return result;
	}

	@Override
	public MobileDto getMobile(String openId) {

		// TODO Auto-generated method stub
		MobileDto json = new MobileDto();
		try {
			String url = "http://{url}/operation/api/out/getMobile?openId= {openId}";
			String httpUrl = String.format("http://%s/operation/api/out/getMobile?openId=%s", urlConfig.getDeploy(),
					openId);
			logger.debug(httpUrl);
			//String response = HttpsUtil.httpsRequestToString(httpUrl, "GET", null);
			Request request = new Request.Builder().url(url).build();
            Response response = new OkHttpClient().newCall(request).execute();
            String result = response.body().string();
			json = JSON.parseObject(result, MobileDto.class);
			// MobileDto json = restTemplate
			// .getForEntity(url, MobileDto.class,urlConfig.getDeploy(),openId)
			// .getBody();
		} catch (Exception e) {

		}
		return json;
	}

	@Override
	public FansInfoByOauthDto getFansInfoByOauth2(String accountId, String code) {
		// TODO Auto-generated method stub
		// String url = "http://{url}/operation/api/out/getFansInfoByOauth2?accountId=
		// {accountId}&phone= {phone}";
		// FansInfoByOauthDto json = restTemplate
		// .getForEntity(url,
		// FansInfoByOauthDto.class,urlConfig.getDeploy(),accountId,code)
		// .getBody();
		// return json;
		FansInfoByOauthDto json = new FansInfoByOauthDto();
		try {
			String url = "http://{url}/operation/api/out/getMobile?openId= {openId}";
			String httpUrl = String.format("http://%s/operation/api/out/getFansInfoByOauth2?accountId=%s&code=%s",
					urlConfig.getDeploy(), accountId, code);
			logger.debug(httpUrl);
			Request request = new Request.Builder().url(httpUrl).build();
            Response response = new OkHttpClient().newCall(request).execute();
            String result = response.body().string();
            //LOGGER.debug("testokhttp成功，url:'{}'，result:'{}'", url, result);
            //return result;
			//String response = HttpsUtil.httpsRequestToString(httpUrl, "GET", null);
			json = JSON.parseObject(result, FansInfoByOauthDto.class);

		} catch (Exception e) {
			logger.error(e.getStackTrace().toString());
			logger.error(e.getMessage());
		}
		return json;
	}

	@Override
	public String fansInfo(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MobileDto sendCaptcha(String openId, String telNum) {
		// TODO Auto-generated method stub
		String url=String.format("http://%s/operation/api/out/sendCaptcha?openId=%s&telNum=%s",urlConfig.getDeploy(),openId ,telNum);
//		JSONObject json = restTemplate.getForEntity(
//				urlConfig.getDeploy() + "operation/api/out/sendCaptcha?openId=" + openId + "&telNum=" + telNum,
//				JSONObject.class).getBody();
//		return json.toJavaObject(MobileDto.class);
		MobileDto json=new MobileDto();
		try {
			
			//String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(url).build();
            Response response = new OkHttpClient().newCall(request).execute();
            String result = response.body().string();
			json = JSON.parseObject(result, MobileDto.class);

		} catch (Exception e) {

		}
		return json;
	}

	@Override
	public MobileDto telbindbycaptcha(String openId, String telNum, String captcha) {
		// TODO Auto-generated method stub
//		JSONObject json = restTemplate.getForEntity(urlConfig.getDeploy() + "operation/api/out/telbindbycaptcha?openId="
//				+ openId + "&telNum=" + telNum + "&captcha=" + captcha, JSONObject.class).getBody();
//		return json.toJavaObject(MobileDto.class);
		String url=String.format("http://%s/operation/api/out/telbindbycaptcha?openId=%s&telNum=%s&captcha=%s",urlConfig.getDeploy(),openId ,telNum,captcha);
		MobileDto json=new MobileDto();
		try {
			
			//String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(url).build();
            Response response = new OkHttpClient().newCall(request).execute();
            String result = response.body().string();
			json = JSON.parseObject(result, MobileDto.class);

		} catch (Exception e) {

		}
		return json;
	}

	@Override
	public JsSignatureDto getJsSignature(String url) {
		// TODO Auto-generated method stub
		String httpUrl=String.format("http://%s/operation/api/out/getJsSignature?accountId=%s&url=%s",urlConfig.getDeploy(),urlConfig.getAccountId() ,url);
		JsSignatureDto json=new JsSignatureDto();
		
		try {
			
			//String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
            Response response = new OkHttpClient().newCall(request).execute();
            String result = response.body().string();
			json = JSON.parseObject(result, JsSignatureDto.class);
            json.setAppId(urlConfig.getAppId());
            logger.info(result);
		} catch (Exception e) {

		}
		return json;
	}

	@Override
	public MobileDto templateMessage(TemplateMessageInputDto input) {
		// TODO Auto-generated method stub
		String httpUrl=String.format("http://%s/operation/api/out/templateMessage",urlConfig.getDeploy());
		MobileDto rstDto=null;
		try {

			String json=JsonUtils.toJson(input);
			logger.info(json);
			Request request = new Request.Builder().url(httpUrl).post(RequestBody.create(  
                    MediaType.parse("application/json; charset=utf-8"),  
                    json)).build();
            Response response = new OkHttpClient().newCall(request).execute();
            String result = response.body().string();
            rstDto = JSON.parseObject(result, MobileDto.class);
            
            logger.info(result);
		} catch (Exception e) {

		}
		return rstDto;
	}

}
