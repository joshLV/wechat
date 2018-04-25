package com.bingosoft.core.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.models.config.ApiUrlConfig;
import com.bingosoft.models.dto.JsSignatureDto;
import com.bingosoft.models.dto.UpdateMainChargesOutputDto;
import com.bingosoft.models.rest.dto.MarkActHandleOutputDto;
import com.bingosoft.models.rest.dto.OBFreeQryOutDataOutputDto;
import com.bingosoft.models.rest.dto.RealTimeFeeOutputDto;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.models.rest.dto.SPFeeQueryOutputDto;
import com.bingosoft.models.rest.dto.ShortAddModeOutputDto;
import com.bingosoft.utils.crypt.SignUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class WebRestServiceImpl implements IWebRestService {

	private static final Logger logger = LoggerFactory.getLogger(WebRestServiceImpl.class);

//	@Autowired
//	ApiUrlConfig apiUrl;

	// 10.113.38.75:10000
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	SimpleDateFormat sdf_month = new SimpleDateFormat("yyyyMM");
	// String baseUrl = "10.105.4.50:58080";

	///////// String baseUrl = "10.113.38.75:10000";
	String baseUrl = "10.113.38.84:10000";

//	public WebRestServiceImpl() {
////		if (apiUrl != null)
////			this.baseUrl = apiUrl.getRestUrl();
//	}

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
		String httpUrl = String.format("http://%s/rest/1.0/sYdscOrdQry%s", baseUrl, SignUtils.setHttpGetReqParam(map));
		JsSignatureDto json = new JsSignatureDto();

		try {
			System.out.println(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS) // 连接超时
					.writeTimeout(15, TimeUnit.SECONDS) // 写超时
					.readTimeout(15, TimeUnit.SECONDS) // 读超时
					.build();
			// Response response = new OkHttpClient().newCall(request).execute();
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			System.out.println(result);
			json = JSON.parseObject(result, JsSignatureDto.class);

		} catch (Exception e) {
			logger.error("sYdscOrdQry:" + e.getMessage() + e.getStackTrace());
		}
		return null;
	}

	/**
	 * 查詢餘額
	 */
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
		String httpUrl = String.format("http://%s/rest/1.0/sPFeeQuery%s", baseUrl, SignUtils.setHttpGetReqParam(map));
		try {
			logger.info(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			// OkHttpClient client= new OkHttpClient.Builder()
			// .connectTimeout(65, TimeUnit.SECONDS) //连接超时
			// .writeTimeout(65, TimeUnit.SECONDS) //写超时
			// .readTimeout(65, TimeUnit.SECONDS) //读超时
			// .build();
			Response response = new OkHttpClient().newCall(request).execute();
			// Response response = client.newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			// json = JSON.parseObject(result, new
			// TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>(){});
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>() {
			});
			// System.out.println(json.getOutData().getPREPAY_FEE());
		} catch (Exception e) {
			logger.error("sPFeeQuery:" + e.getMessage() + e.getStackTrace());

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
		String httpUrl = String.format("http://%s/rest/1.0/qryRealTimeFee%s", baseUrl,
				SignUtils.setHttpGetReqParam(map));
		RestResponseOutputDto<RealTimeFeeOutputDto> json = new RestResponseOutputDto<RealTimeFeeOutputDto>();

		try {
			logger.info(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS) // 连接超时
					.writeTimeout(15, TimeUnit.SECONDS) // 写超时
					.readTimeout(15, TimeUnit.SECONDS) // 读超时
					.build();
			// Response response = new OkHttpClient().newCall(request).execute();
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			json = JSON.parseObject(result, RestResponseOutputDto.class);

		} catch (Exception e) {
			logger.error("qryRealTimeFee" + e.getMessage() + e.getStackTrace());
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
		String httpUrl = String.format("http://%s/rest/1.0/qryBalanceOfAccount%s", baseUrl,
				SignUtils.setHttpGetReqParam(map));
		RestResponseOutputDto<RealTimeFeeOutputDto> json = new RestResponseOutputDto<RealTimeFeeOutputDto>();

		try {
			logger.info(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS) // 连接超时
					.writeTimeout(15, TimeUnit.SECONDS) // 写超时
					.readTimeout(15, TimeUnit.SECONDS) // 读超时
					.build();
			// Response response = new OkHttpClient().newCall(request).execute();
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			json = JSON.parseObject(result, RestResponseOutputDto.class);

		} catch (Exception e) {
			logger.error("qryBalanceOfAccount" + e.getMessage() + e.getStackTrace());
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
		if (prod_id.equals("ACAZ26285")) {
			map.put("service_no", "ob0014");
		} else {
			map.put("service_no", "oc0069");
			// map.put("service_no", "ob0014");
		}
		String httpUrl = String.format("http://%s/rest/1.0/sShortAddMode%s", baseUrl,
				SignUtils.setHttpGetReqParam(map));
		RestResponseOutputDto<ShortAddModeOutputDto> json = new RestResponseOutputDto<ShortAddModeOutputDto>();

		try {
			logger.info(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			OkHttpClient client = new OkHttpClient.Builder().connectTimeout(65, TimeUnit.SECONDS) // 连接超时
					.writeTimeout(65, TimeUnit.SECONDS) // 写超时
					.readTimeout(65, TimeUnit.SECONDS) // 读超时
					.build();
			// Request request = new Request.Builder().url("http://www.baidu.com").build();
			// Response response = new OkHttpClient().newCall(request).execute();
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<ShortAddModeOutputDto>>() {
			});

		} catch (Exception e) {
			logger.error("sShortAddMode:" + httpUrl);
			logger.error("sShortAddMode:" + e.getMessage() + e.getStackTrace());
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
		String httpUrl = String.format("http://%s/rest/1.0/sOBFreeQry%s", baseUrl, SignUtils.setHttpGetReqParam(map));
		try {
			logger.info(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			OkHttpClient client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS) // 连接超时
					.writeTimeout(15, TimeUnit.SECONDS) // 写超时
					.readTimeout(15, TimeUnit.SECONDS) // 读超时
					.build();
			// Response response = new OkHttpClient().newCall(request).execute();
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			// json = JSON.parseObject(result, new
			// TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>(){});
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<OBFreeQryOutDataOutputDto>>() {
			});
			// System.out.println(json.getOutData().getGPRS_INFO().size());
		} catch (Exception e) {
			logger.error("sOBFreeQry" + e.getMessage() + e.getStackTrace());
			System.out.println(e.getMessage() + e.getStackTrace());
		}
		return json;
	}

	@Override
	public void sQryGdWindMsgL(String phoneNo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("login_no", "oc0069");
		map.put("phone_no", phoneNo);
		map.put("qry_type", "1");
		RestResponseOutputDto<OBFreeQryOutDataOutputDto> json = new RestResponseOutputDto<OBFreeQryOutDataOutputDto>();
		String httpUrl = String.format("http://%s/rest/1.0/sQryGdWindMsgL%s", baseUrl,
				SignUtils.setHttpGetReqParam(map));
		try {
			logger.info(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			// json = JSON.parseObject(result, new
			// TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>(){});
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<OBFreeQryOutDataOutputDto>>() {
			});
			// System.out.println(json.getOutData().getGPRS_INFO().size());
		} catch (Exception e) {
			System.out.println(e.getMessage() + e.getStackTrace());
		}

	}

	@Override
	public void qryGdwindInfo(String phoneNo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("login_no", "oc0069");
		map.put("phone_no", phoneNo);

		RestResponseOutputDto<OBFreeQryOutDataOutputDto> json = new RestResponseOutputDto<OBFreeQryOutDataOutputDto>();
		String httpUrl = String.format("http://%s/rest/1.0/qryGdwindInfo%s", baseUrl,
				SignUtils.setHttpGetReqParam(map));
		try {
			logger.info(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			System.out.println(result);
			// json = JSON.parseObject(result, new
			// TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>(){});
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<OBFreeQryOutDataOutputDto>>() {
			});
			// System.out.println(json.getOutData().getGPRS_INFO().size());
		} catch (Exception e) {
			logger.error("qryGdwindInfo" + e.getMessage() + e.getStackTrace());
			System.out.println(e.getMessage() + e.getStackTrace());
		}
	}

	@Override
	public void qryWifiMsgInfo(String phoneNo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("login_no", "");
		map.put("phone_no", phoneNo);
		map.put("qry_type", "1");
		map.put("prod_prcid", "");
		RestResponseOutputDto<OBFreeQryOutDataOutputDto> json = new RestResponseOutputDto<OBFreeQryOutDataOutputDto>();
		String httpUrl = String.format("http://%s/rest/1.0/qryWifiMsgInfo%s", baseUrl,
				SignUtils.setHttpGetReqParam(map));
		try {
			System.out.println(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			System.out.println(result);
			// json = JSON.parseObject(result, new
			// TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>(){});
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<OBFreeQryOutDataOutputDto>>() {
			});
			// System.out.println(json.getOutData().getGPRS_INFO().size());
		} catch (Exception e) {
			logger.error("qryWifiMsgInfo" + e.getMessage() + e.getStackTrace());
			System.out.println(e.getMessage() + e.getStackTrace());
		}
	}

	@Override
	public void qryGdWindQpT(String phoneNo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("login_no", "");
		map.put("phone_no", phoneNo);
		map.put("qry_type", "1");
		RestResponseOutputDto<OBFreeQryOutDataOutputDto> json = new RestResponseOutputDto<OBFreeQryOutDataOutputDto>();
		String httpUrl = String.format("http://%s/rest/1.0/qryGdWindOpT%s", baseUrl, SignUtils.setHttpGetReqParam(map));
		try {
			System.out.println(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			System.out.println(result);
			// json = JSON.parseObject(result, new
			// TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>(){});
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<OBFreeQryOutDataOutputDto>>() {
			});
			// System.out.println(json.getOutData().getGPRS_INFO().size());
		} catch (Exception e) {
			System.out.println(e.getMessage() + e.getStackTrace());
		}
	}

	@Override
	public RestResponseOutputDto<MarkActHandleOutputDto> MarkActHandle(String phoneNo, String act_id, String prod_id) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("phone_no", phoneNo);
		map.put("login_no", "");
		map.put("act_id", act_id);
		map.put("means_id", prod_id);
		if (prod_id.equals("ACAZ26285")) {
			map.put("service_no", "ob0014");
		} else {
			map.put("service_no", "oc0069");
			// map.put("service_no", "ob0014");
		}
		map.put("channel_type", 2);
		// map.put("back_code", "JRB1");
		String httpUrl = String.format("http://%s/rest/1.0/MarkActHandle%s", baseUrl,
				SignUtils.setHttpGetReqParam(map));
		RestResponseOutputDto<MarkActHandleOutputDto> json = new RestResponseOutputDto<MarkActHandleOutputDto>();

		try {
			logger.info(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<MarkActHandleOutputDto>>() {
			});

		} catch (Exception e) {
			logger.error(httpUrl);
			logger.error("MarkActHandle:" + e.getMessage() + e.getStackTrace());
		}
		// return json;
		return json;
	}

	@Override
	public void qryOrdPudInfo(String phoneNo) {
		// TODO Auto-generated method stub
		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("login_no", "");
		map.put("phone_no", phoneNo);
		map.put("year_month", sdf_month.format(new Date()));
		map.put("qry_type", "0");
		RestResponseOutputDto<OBFreeQryOutDataOutputDto> json = new RestResponseOutputDto<OBFreeQryOutDataOutputDto>();
		String httpUrl = String.format("http://%s/rest/1.0/qryOrdPudInfo%s", baseUrl,
				SignUtils.setHttpGetReqParam(map));
		try {
			logger.info(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			OkHttpClient client = new OkHttpClient.Builder().connectTimeout(65, TimeUnit.SECONDS) // 连接超时
					.writeTimeout(65, TimeUnit.SECONDS) // 写超时
					.readTimeout(65, TimeUnit.SECONDS) // 读超时
					.build();
			// Response response = new OkHttpClient().newCall(request).execute();
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			// json = JSON.parseObject(result, new
			// TypeReference<RestResponseOutputDto<SPFeeQueryOutputDto>>(){});
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<OBFreeQryOutDataOutputDto>>() {
			});
			// System.out.println(json.getOutData().getGPRS_INFO().size());
		} catch (Exception e) {
			logger.error("qryOrdPudInfo:" + e.getMessage() + e.getStackTrace());
		}
	}

	/**
	 * 主资费变更
	 */
	@Override
	public RestResponseOutputDto<UpdateMainChargesOutputDto> updateMainCharges(String phoneNo, String prod_prcid) {
		// TODO Auto-generated method stub

		Date date = new Date();
		HashMap map = new HashMap();
		map.put("appKey", "11000442");
		map.put("timeStamp", sdf.format(date));
		map.put("userName", "zhouyingwei");
		map.put("phone_no", phoneNo);
		map.put("login_no", "");
		map.put("prod_prcid", prod_prcid);
		map.put("chnn_id", 12);
		map.put("service_no", "oc0069");
		// map.put("back_code", "JRB1");
		String httpUrl = String.format("http://%s/rest/1.0/updateMainCharges%s", baseUrl,
				SignUtils.setHttpGetReqParam(map));
		RestResponseOutputDto<UpdateMainChargesOutputDto> json = new RestResponseOutputDto<UpdateMainChargesOutputDto>();

		try {
			logger.info(httpUrl);
			// String response = HttpsUtil.httpsRequestToString(url, "GET", null);
			Request request = new Request.Builder().url(httpUrl).build();
			Response response = new OkHttpClient().newCall(request).execute();
			String result = response.body().string();
			logger.info(result);
			json = JSON.parseObject(result, new TypeReference<RestResponseOutputDto<UpdateMainChargesOutputDto>>() {
			});

		} catch (Exception e) {
			logger.error(httpUrl);
			logger.error("updateMainCharges:" + e.getMessage() + e.getStackTrace());
		}
		return json;

	}

}
