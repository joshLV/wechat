package com.bingosoft.core.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.models.dto.JsSignatureDto;
import com.bingosoft.models.rest.dto.OBFreeQryOutDataOutputDto;
import com.bingosoft.models.rest.dto.RealTimeFeeOutputDto;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.models.rest.dto.SPFeeQueryOutputDto;
import com.bingosoft.models.rest.dto.ShortAddModeOutputDto;
import com.bingosoft.utils.crypt.SignUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@Service
public class WebRestServiceImpl implements IWebRestService {

	//10.113.38.75:10000
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	SimpleDateFormat sdf_month = new SimpleDateFormat("yyyyMM");

	@Override
	public RestResponseOutputDto<RealTimeFeeOutputDto> sYdscOrdQry(String phoneNo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("phone_no", phoneNo);
		map.put("id_type", "01");
		map.put("opr_numb", "");
		map.put("opr_source", "");
		map.put("login_no", "");
		String httpUrl = String.format("http://%s/rest/1.0/sYdscOrdQry%s", "10.113.38.75:10000",
				SignUtils.setHttpGetReqParam(map));
		JsSignatureDto json = new JsSignatureDto();

		try {
			System.out.println(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			System.out.println(result);
			json = JSON.parseObject(result, JsSignatureDto.class);

		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public RestResponseOutputDto<SPFeeQueryOutputDto> sPFeeQuery(String phoneNo) {
		// TODO Auto-generated method stub
		// 余额查询接口(sPFeeQuery)
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("login_no", "");
		map.put("phone_no", phoneNo);
		map.put("msg_flag", 0);
		RestResponseOutputDto<SPFeeQueryOutputDto> json = new RestResponseOutputDto<SPFeeQueryOutputDto>();
		String httpUrl = String.format("http://%s/rest/1.0/sPFeeQuery%s", "10.113.38.75:10000",
				SignUtils.setHttpGetReqParam(map));
		try {

			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			System.out.println(result);
			// json = JSON.parseObject(result, new
			// TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>(){});
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>() {
			});
			System.out.println(json.getOutData().getPREPAY_FEE());
		} catch (Exception e) {
			System.out.println(e.getMessage() + e.getStackTrace());
		}
		return json;
	}

	@Override
	public RestResponseOutputDto<RealTimeFeeOutputDto> qryRealTimeFee(String phoneNo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("phone_no", phoneNo);
		map.put("login_no", "");
		map.put("msg_flag", 2);
		String httpUrl = String.format("http://%s/rest/1.0/qryRealTimeFee%s", "10.113.38.75:10000",
				SignUtils.setHttpGetReqParam(map));
		RestResponseOutputDto<RealTimeFeeOutputDto> json = new RestResponseOutputDto<RealTimeFeeOutputDto>();

		try {
			System.out.println(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			System.out.println(result);
			json = JSON.parseObject(result, RestResponseOutputDto.class);

		} catch (Exception e) {

		}
		return json;
	}

	public RestResponseOutputDto<RealTimeFeeOutputDto> qryBalanceOfAccount(String phoneNo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("phone_no", phoneNo);
		map.put("login_no", "");
		map.put("msg_flag", 2);
		String httpUrl = String.format("http://%s/rest/1.0/qryBalanceOfAccount%s", "10.113.38.75:10000",
				SignUtils.setHttpGetReqParam(map));
		RestResponseOutputDto<RealTimeFeeOutputDto> json = new RestResponseOutputDto<RealTimeFeeOutputDto>();

		try {
			System.out.println(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			System.out.println(result);
			json = JSON.parseObject(result, RestResponseOutputDto.class);

		} catch (Exception e) {

		}
		return json;
	}

	@Override
	public RestResponseOutputDto<ShortAddModeOutputDto> sShortAddMode(String phoneNo, String prod_id) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("phone_no", phoneNo);
		map.put("login_no", "");
		map.put("operate_type", "A");
		map.put("prod_prcid", prod_id);
		// map.put("service_no", "oc0069");
		map.put("service_no", "ob0014");
		String httpUrl = String.format("http://%s/rest/1.0/sShortAddMode%s", "10.113.38.75:10000",
				SignUtils.setHttpGetReqParam(map));
		RestResponseOutputDto<ShortAddModeOutputDto> json = new RestResponseOutputDto<ShortAddModeOutputDto>();

		try {
			System.out.println(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			System.out.println(result);
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<ShortAddModeOutputDto>>() {
			});

		} catch (Exception e) {

		}
		// return json;
		return json;
	}

	@Override
	public RestResponseOutputDto<OBFreeQryOutDataOutputDto> sOBFreeQry(String phoneNo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("login_no", "");
		map.put("phone_no", phoneNo);
		map.put("year_month", sdf_month.format(new Date()));
		map.put("gprs_flag", "1");
		RestResponseOutputDto<OBFreeQryOutDataOutputDto> json = new RestResponseOutputDto<OBFreeQryOutDataOutputDto>();
		String httpUrl = String.format("http://%s/rest/1.0/sOBFreeQry%s", "10.113.38.75:10000",
				SignUtils.setHttpGetReqParam(map));
		try {

			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			System.out.println(result);
			// json = JSON.parseObject(result, new
			// TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>(){});
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<OBFreeQryOutDataOutputDto>>() {
			});
			System.out.println(json.getOutData().getGPRS_INFO().size());
		} catch (Exception e) {
			System.out.println(e.getMessage() + e.getStackTrace());
		}
		return json;
	}

}
