package com.bingosoft.core.web.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bingosoft.core.web.service.ICsfWebService;
import com.bingosoft.models.config.ApiUrlConfig;
import com.bingosoft.models.csf.CrmpfPubInfoInputDto;
import com.bingosoft.models.csf.CsfResultDto;
import com.bingosoft.models.csf.ObjectData;
import com.bingosoft.models.csf.ObjectMsg;
import com.bingosoft.models.csf.OfferItemOutputDto;
import com.bingosoft.models.csf.OfferListInputDto;
import com.bingosoft.models.csf.OfferListResultOutputDto;
import com.bingosoft.models.csf.OfferParamsInputDto;
import com.bingosoft.models.csf.OrderResultOutputDto;
import com.bingosoft.models.csf.PackageParamsInputDto;
import com.bingosoft.models.csf.PlanRemainDto;
import com.bingosoft.models.csf.RealFeeQryRspDataDto;
import com.bingosoft.models.csf.RealFeeQryRspRootDto;
import com.bingosoft.models.csf.ResDataOutputDto;
import com.bingosoft.models.csf.ResourcesDataInfoDto;
import com.bingosoft.models.csf.ResourcesRootDto;
import com.bingosoft.models.csf.sCampaignsOrderPackageInputDto;
import com.bingosoft.models.rest.dto.FlowAdditionalDealParamsDto;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.utils.IdGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class CsfWebServiceImpl implements ICsfWebService {

	// @Autowired
	// ApiUrlConfig apiUrl;

	//private String csfUrl = "http://10.105.4.50:58080";// "http://10.105.4.50:58080";
	 private String csfUrl ="http://10.109.238.14:8080";
	// ;//"http://10.105.4.50:58080";

	private static final Logger logger = LoggerFactory.getLogger(CsfWebServiceImpl.class);

	private static final String ChannelId = "wxzq";

	public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

	SimpleDateFormat sdt = new SimpleDateFormat("yyyyMMddHHmmss");
	// public CsfWebServiceImpl()
	// {
	//// if(apiUrl!=null)
	//// this.csfUrl=apiUrl.getCsfUrl();
	// }

	@Override
	public CsfResultDto<ObjectData<RealFeeQryRspDataDto>> CSCHQFareBalance(String phone) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> userMobile = new HashMap<String, String>();
		userMobile.put("userMobile", phone);
		params.put("params", userMobile);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String postBody = gson.toJson(params);
		String httpUrl = csfUrl + "/ngcrmpfcore_sc/csf/business/fareBalance/" + postBody;
		Request request = new Request.Builder().url(httpUrl).addHeader("X-Channel-Id", ChannelId)
				.addHeader("X-Trans-Id", ChannelId + String.valueOf(IdGenerator.INSTANCE.nextId())).build();
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(65, TimeUnit.SECONDS) // 连接超时
				.writeTimeout(65, TimeUnit.SECONDS) // 写超时
				.readTimeout(65, TimeUnit.SECONDS) // 读超时
				.build();
		logger.info(httpUrl);

		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String result = response.body().string();
			// result="{\"rtnCode\":\"0\",\"rtnMsg\":\"成功!\",\"bean\":{},\"beans\":[],\"object\":{\"data\":{\"oprTime\":\"20180417144751\",\"transIdo\":\"wxzq1804171447509778\",\"realFeeQryRsp\":{\"curFeeTotal\":\"94.27\",\"curFee\":\"94.27\",\"realFee\":\"141.10\",\"oweFee\":\"0.00\"}},\"message\":\"成功\",\"status\":\"0\"}}";
			CsfResultDto<ObjectData<RealFeeQryRspDataDto>> json;
			json = JSON.parseObject(result, new TypeReference<CsfResultDto<ObjectData<RealFeeQryRspDataDto>>>() {
			});

			logger.info("CSCHQFareBalance:" + result);
			return json;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("CSCHQFareBalance:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
//		 String result = "";
//		 result =
//		 "{\"rtnCode\":\"0\",\"rtnMsg\":\"成功!\",\"bean\":{},\"beans\":[],\"object\":{\"data\":{\"oprTime\":\"20180417144751\",\"transIdo\":\"wxzq1804171447509778\",\"realFeeQryRsp\":{\"curFeeTotal\":\"94.27\",\"curFee\":\"94.27\",\"realFee\":\"141.10\",\"oweFee\":\"0.00\"}},\"message\":\"成功\",\"status\":\"0\"}}";
//		 CsfResultDto<ObjectData<RealFeeQryRspDataDto>> json;
//		 json = JSON.parseObject(result, new
//		 TypeReference<CsfResultDto<ObjectData<RealFeeQryRspDataDto>>>() {
//		 });
//		 return json;
	}

	@Override
	public void flowAdditionalDeal() {
		// TODO Auto-generated method stub
		String httpUrl = csfUrl + "/ngcrmpfcore_sc/csf/business/flowAdditionalDeal";

		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(65, TimeUnit.SECONDS) // 连接超时
				.writeTimeout(65, TimeUnit.SECONDS) // 写超时
				.readTimeout(65, TimeUnit.SECONDS) // 读超时
				.build();
		logger.info(httpUrl);
		Map<String, FlowAdditionalDealParamsDto> param = new HashMap<String, FlowAdditionalDealParamsDto>();
		FlowAdditionalDealParamsDto dto = new FlowAdditionalDealParamsDto();
		dto.setUserMobile("13908082769");
		dto.setProdPrcid("ACAZ22296");
		param.put("params", dto);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String postBody = gson.toJson(param);
		RequestBody body = RequestBody.create(JSON_TYPE, postBody);
		logger.info(postBody);
		Request request = new Request.Builder().url(httpUrl).post(body).addHeader("X-Channel-Id", ChannelId)
				.addHeader("X-Trans-Id", ChannelId + String.valueOf(IdGenerator.INSTANCE.nextId())).build();
		logger.info(request.toString());
		// Request request = new Request.Builder().url("http://www.baidu.com").build();
		// Response response = new OkHttpClient().newCall(request).execute();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String result = response.body().string();
			CsfResultDto<ObjectMsg<Object>> json;
			json = JSON.parseObject(result, new TypeReference<CsfResultDto<ObjectMsg<Object>>>() {
			});

			logger.info(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public CsfResultDto<ObjectData<ResDataOutputDto>> CSCHQPlanRemian(String phone) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> userMobile = new HashMap<String, String>();
		userMobile.put("userMobile", phone);
		params.put("params", userMobile);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String postBody = gson.toJson(params);
		String httpUrl = csfUrl + "/ngcrmpfcore_sc/csf/business/planRemian/" + postBody;
		Request request = new Request.Builder().url(httpUrl).addHeader("X-Channel-Id", ChannelId)
				.addHeader("X-Trans-Id", ChannelId + String.valueOf(IdGenerator.INSTANCE.nextId())).build();
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(65, TimeUnit.SECONDS) // 连接超时
				.writeTimeout(65, TimeUnit.SECONDS) // 写超时
				.readTimeout(65, TimeUnit.SECONDS) // 读超时
				.build();
		logger.info(httpUrl);
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String result = response.body().string();
			// result="{\"rtnCode\":\"0\",\"rtnMsg\":\"成功!\",\"bean\":{},\"beans\":[],\"object\":{\"data\":{\"planRemain\":[{\"planName\":\"4G上网流量30元包月\",\"planId\":\"ACAZ22296\",\"validDate\":\"20180115\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"1\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"1048576\",\"usedRes\":\"0\",\"remainRes\":\"1048576\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"04\",\"isMultiTerm\":\"1\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G（上月结转流量）\",\"resourcesLeftInfo\":{\"totalRes\":\"1048576\",\"usedRes\":\"0\",\"remainRes\":\"1048576\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"叠加包资费0元包10M(免费)\",\"planId\":\"ACAZ23005\",\"validDate\":\"20180404\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"10240\",\"usedRes\":\"0\",\"remainRes\":\"10240\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"10240\",\"usedRes\":\"0\",\"remainRes\":\"10240\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"叠加包资费0元包20M(免费)\",\"planId\":\"ACAZ23006\",\"validDate\":\"20180404\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"20480\",\"usedRes\":\"0\",\"remainRes\":\"20480\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"20480\",\"usedRes\":\"0\",\"remainRes\":\"20480\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"流量安心服务\",\"planId\":\"ACAZ23715\",\"validDate\":\"20160501\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"安心包\",\"resourcesLeftInfo\":{\"totalRes\":\"0\",\"usedRes\":\"0\",\"remainRes\":\"0\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"流量安心服务\",\"planId\":\"ACAZ24150\",\"validDate\":\"20160501\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"安心包\",\"resourcesLeftInfo\":{\"totalRes\":\"0\",\"usedRes\":\"0\",\"remainRes\":\"0\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"4G飞享套餐88版\",\"planId\":\"ACAZ25129\",\"validDate\":\"20170501\",\"resourcesInfo\":[{\"resourcesCode\":\"01\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"本地通话优惠分钟数\",\"resourcesLeftInfo\":{\"totalRes\":\"100\",\"usedRes\":\"90\",\"remainRes\":\"10\",\"unit\":\"01\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"01\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"基本通话优惠分钟数\",\"resourcesLeftInfo\":{\"totalRes\":\"220\",\"usedRes\":\"0\",\"remainRes\":\"220\",\"unit\":\"01\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"716800\",\"usedRes\":\"0\",\"remainRes\":\"716800\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G（上月结转流量）\",\"resourcesLeftInfo\":{\"totalRes\":\"716800\",\"usedRes\":\"0\",\"remainRes\":\"716800\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"2G省内流量\",\"planId\":\"ACAZ28684\",\"validDate\":\"20180115\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"省内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"2097152\",\"usedRes\":\"0\",\"remainRes\":\"2097152\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"校园促销88档\",\"planId\":\"ACAZ28752\",\"validDate\":\"20170826\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"省内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"41943040\",\"usedRes\":\"20076350\",\"remainRes\":\"21866690\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"01\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"智能V网优惠分钟数\",\"resourcesLeftInfo\":{\"totalRes\":\"44640\",\"usedRes\":\"0\",\"remainRes\":\"44640\",\"unit\":\"01\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"欢乐时光3元1G\",\"planId\":\"ACAZ29271\",\"validDate\":\"20180409\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"省内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"113069\",\"usedRes\":\"113069\",\"remainRes\":\"0\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"新爱家108\",\"planId\":\"ACBZ14166\",\"validDate\":\"20171201\",\"resourcesInfo\":[{\"resourcesCode\":\"01\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"家庭成员间互拨本地通话优惠分钟数\",\"resourcesLeftInfo\":{\"totalRes\":\"2000\",\"usedRes\":\"0\",\"remainRes\":\"2000\",\"unit\":\"01\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"1GB数据流量叠加卡\",\"planId\":\"BCAZ0020016\",\"validDate\":\"20180404\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"1048576\",\"usedRes\":\"0\",\"remainRes\":\"1048576\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]}],\"mealInfoUp\":[],\"oprTime\":\"20180417154159\",\"transIdo\":\"wxzq1804171541587728\"},\"message\":\"成功\",\"status\":\"0\"}}";
			CsfResultDto<ObjectData<ResDataOutputDto>> json;
			json = JSON.parseObject(result, new TypeReference<CsfResultDto<ObjectData<ResDataOutputDto>>>() {
			});

			//

			logger.info("CSCHQPlanRemian" + result);
			return json;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("CSCHQPlanRemian" + e.getMessage());
			e.printStackTrace();
		
		}
		return null;
//		 String result = "";
//		 result ="{\"rtnCode\":\"0\",\"rtnMsg\":\"成功!\",\"bean\":{},\"beans\":[],\"object\":{\"data\":{\"planRemain\":[{\"planName\":\"4G上网流量30元包月\",\"planId\":\"ACAZ22296\",\"validDate\":\"20180115\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"1\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"1048576\",\"usedRes\":\"0\",\"remainRes\":\"1048576\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"04\",\"isMultiTerm\":\"1\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G（上月结转流量）\",\"resourcesLeftInfo\":{\"totalRes\":\"1048576\",\"usedRes\":\"0\",\"remainRes\":\"1048576\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"叠加包资费0元包10M(免费)\",\"planId\":\"ACAZ23005\",\"validDate\":\"20180404\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"10240\",\"usedRes\":\"0\",\"remainRes\":\"10240\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"10240\",\"usedRes\":\"0\",\"remainRes\":\"10240\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"叠加包资费0元包20M(免费)\",\"planId\":\"ACAZ23006\",\"validDate\":\"20180404\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"20480\",\"usedRes\":\"0\",\"remainRes\":\"20480\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"20480\",\"usedRes\":\"0\",\"remainRes\":\"20480\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"流量安心服务\",\"planId\":\"ACAZ23715\",\"validDate\":\"20160501\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"安心包\",\"resourcesLeftInfo\":{\"totalRes\":\"0\",\"usedRes\":\"0\",\"remainRes\":\"0\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"流量安心服务\",\"planId\":\"ACAZ24150\",\"validDate\":\"20160501\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"安心包\",\"resourcesLeftInfo\":{\"totalRes\":\"0\",\"usedRes\":\"0\",\"remainRes\":\"0\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"4G飞享套餐88版\",\"planId\":\"ACAZ25129\",\"validDate\":\"20170501\",\"resourcesInfo\":[{\"resourcesCode\":\"01\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"本地通话优惠分钟数\",\"resourcesLeftInfo\":{\"totalRes\":\"100\",\"usedRes\":\"90\",\"remainRes\":\"10\",\"unit\":\"01\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"01\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"基本通话优惠分钟数\",\"resourcesLeftInfo\":{\"totalRes\":\"220\",\"usedRes\":\"0\",\"remainRes\":\"220\",\"unit\":\"01\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"716800\",\"usedRes\":\"0\",\"remainRes\":\"716800\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G（上月结转流量）\",\"resourcesLeftInfo\":{\"totalRes\":\"716800\",\"usedRes\":\"0\",\"remainRes\":\"716800\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"2G省内流量\",\"planId\":\"ACAZ28684\",\"validDate\":\"20180115\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"省内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"2097152\",\"usedRes\":\"0\",\"remainRes\":\"2097152\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"校园促销88档\",\"planId\":\"ACAZ28752\",\"validDate\":\"20170826\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"省内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"41943040\",\"usedRes\":\"20076350\",\"remainRes\":\"21866690\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}},{\"resourcesCode\":\"01\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"智能V网优惠分钟数\",\"resourcesLeftInfo\":{\"totalRes\":\"44640\",\"usedRes\":\"0\",\"remainRes\":\"44640\",\"unit\":\"01\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"欢乐时光3元1G\",\"planId\":\"ACAZ29271\",\"validDate\":\"20180409\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"省内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"113069\",\"usedRes\":\"113069\",\"remainRes\":\"0\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"新爱家108\",\"planId\":\"ACBZ14166\",\"validDate\":\"20171201\",\"resourcesInfo\":[{\"resourcesCode\":\"01\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"家庭成员间互拨 本地通话优惠分钟数\",\"resourcesLeftInfo\":{\"totalRes\":\"2000\",\"usedRes\":\"0\",\"remainRes\":\"2000\",\"unit\":\"01\",\"validDate\":\"20180417154159\"}}}]},{\"planName\":\"1GB数据流量叠加卡\",\"planId\":\"BCAZ0020016\",\"validDate\":\"20180404\",\"resourcesInfo\":[{\"resourcesCode\":\"04\",\"isMultiTerm\":\"\",\"secResourcesInfo\":{\"secResourcesName\":\"国内2，3，4G\",\"resourcesLeftInfo\":{\"totalRes\":\"1048576\",\"usedRes\":\"0\",\"remainRes\":\"1048576\",\"unit\":\"03\",\"validDate\":\"20180417154159\"}}}]}],\"mealInfoUp\":[],\"oprTime\":\"20180417154159\",\"transIdo\":\"wxzq1804171541587728\"},\"message\":\"成功\",\"status\":\"0\"}}";
//		 CsfResultDto<ObjectData<ResDataOutputDto>> json;
//		 json = JSON.parseObject(result, new
//		 TypeReference<CsfResultDto<ObjectData<ResDataOutputDto>>>() {
//		 });
//		
//		 //
//		
//		 logger.info("CSCHQPlanRemian" + result);
//		 return json;
	}

	@Override
	public OrderResultOutputDto CSCHTSubmitOffersOrder(String phone, String prod) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();

		OfferListInputDto offerList = new OfferListInputDto();
		offerList.setOfferId(prod);
		List<OfferListInputDto> offers = new ArrayList<>();
		offers.add(offerList);
		OfferParamsInputDto param = new OfferParamsInputDto();
		param.setUserMobile(phone);
		param.setOfferList(offers);
		param.setCrmpfPubInfo(new CrmpfPubInfoInputDto());
		params.put("params", param);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String postBody = gson.toJson(params);
		String httpUrl = csfUrl + "/ngcrmpfcore_sc/csf/business/sOffersOrder";
		Request request = new Request.Builder().url(httpUrl).addHeader("X-Channel-Id", ChannelId)
				.addHeader("X-Trans-Id", ChannelId + String.valueOf(IdGenerator.INSTANCE.nextId()))
				.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody)).build();
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(65, TimeUnit.SECONDS) // 连接超时
				.writeTimeout(65, TimeUnit.SECONDS) // 写超时
				.readTimeout(65, TimeUnit.SECONDS) // 读超时
				.build();
		logger.info(httpUrl);
		logger.info(request.headers().toString());
		// Request request = new Request.Builder().url("http://www.baidu.com").build();
		// Response response = new OkHttpClient().newCall(request).execute();
		Response response = null;
		OrderResultOutputDto dto = new OrderResultOutputDto();
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String result = response.body().string();
			CsfResultDto<ObjectMsg<Object>> json;
			json = JSON.parseObject(result, new TypeReference<CsfResultDto<ObjectMsg<Object>>>() {
			});
			logger.info(result);

			if (json.getRtnCode().equals("0") && json.getObject().getRespCode().equals("0")) {
				dto.setSuccess(true);
				;
			} else {
				dto.setMsg(json.getObject().getRespDesc());
			}
			return dto;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("CSCHTSubmitOffersOrder" + e.getMessage());
		}
		return null;
	}

	@Override
	public void CSCHTSubmitCampaignsOrder(String phone, String prod) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();

		sCampaignsOrderPackageInputDto offerList = new sCampaignsOrderPackageInputDto();
		offerList.setPackageId(prod);
		List<sCampaignsOrderPackageInputDto> offers = new ArrayList<>();
		offers.add(offerList);
		PackageParamsInputDto param = new PackageParamsInputDto();
		param.setUserMobile(phone);
		param.setPackageList(offers);
		param.setCrmpfPubInfo(new CrmpfPubInfoInputDto());
		params.put("params", param);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String postBody = gson.toJson(params);
		String httpUrl = csfUrl + "/ngcrmpfcore_sc/csf/business/sCampaignsOrder";
		Request request = new Request.Builder().url(httpUrl).addHeader("X-Channel-Id", ChannelId)
				.addHeader("X-Trans-Id", ChannelId + String.valueOf(IdGenerator.INSTANCE.nextId()))
				.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody)).build();
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(65, TimeUnit.SECONDS) // 连接超时
				.writeTimeout(65, TimeUnit.SECONDS) // 写超时
				.readTimeout(65, TimeUnit.SECONDS) // 读超时
				.build();
		logger.info(httpUrl);
		// Request request = new Request.Builder().url("http://www.baidu.com").build();
		// Response response = new OkHttpClient().newCall(request).execute();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String result = response.body().string();
			CsfResultDto<ObjectMsg<Object>> json;
			json = JSON.parseObject(result, new TypeReference<CsfResultDto<ObjectMsg<Object>>>() {
			});

			logger.info(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("CSCHTSubmitCampaignsOrder:" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean smsCentral(String phone, String smsContent) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();

		Map<String, String> paramsAll = new HashMap<String, String>();
		paramsAll.put("userMobile", phone);
		paramsAll.put("smsContent", smsContent);
		params.put("params", paramsAll);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String postBody = gson.toJson(params);
		String httpUrl = csfUrl + "/ngcrmpfcore_sc/csf/business/smsCentral";
		Request request = new Request.Builder().url(httpUrl).addHeader("X-Channel-Id", ChannelId)
				.addHeader("X-Trans-Id", ChannelId + String.valueOf(IdGenerator.INSTANCE.nextId()))
				.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody)).build();
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(65, TimeUnit.SECONDS) // 连接超时
				.writeTimeout(65, TimeUnit.SECONDS) // 写超时
				.readTimeout(65, TimeUnit.SECONDS) // 读超时
				.build();
		logger.info(httpUrl);

		// Request request = new Request.Builder().url("http://www.baidu.com").build();
		// Response response = new OkHttpClient().newCall(request).execute();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String result = response.body().string();
			CsfResultDto<ObjectMsg<Object>> json;
			json = JSON.parseObject(result, new TypeReference<CsfResultDto<ObjectMsg<Object>>>() {
			});
			logger.info(result);
			if (json != null && json.getObject() != null) {
				if ("success".equals(json.getObject().getRespDesc())) {
					return true;
				}
			}
			return false;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public OfferItemOutputDto subscribeInfoCentral(String phone, String prod) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();

		Map<String, String> paramsAll = new HashMap<String, String>();
		paramsAll.put("userMobile", phone);
		paramsAll.put("queryType", "00");
		paramsAll.put("queryMode", "0");
		params.put("params", paramsAll);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String postBody = gson.toJson(params);
		String httpUrl = csfUrl + "/ngcrmpfcore_sc/csf/business/subscribeInfoCentral";
		Request request = new Request.Builder().url(httpUrl).addHeader("X-Channel-Id", ChannelId)
				.addHeader("X-Trans-Id", ChannelId + String.valueOf(IdGenerator.INSTANCE.nextId()))
				.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody)).build();
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(65, TimeUnit.SECONDS) // 连接超时
				.writeTimeout(65, TimeUnit.SECONDS) // 写超时
				.readTimeout(65, TimeUnit.SECONDS) // 读超时
				.build();
		logger.info(httpUrl);

		// Request request = new Request.Builder().url("http://www.baidu.com").build();
		// Response response = new OkHttpClient().newCall(request).execute();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String result = response.body().string();
			CsfResultDto<ObjectMsg<OfferListResultOutputDto>> json;
			json = JSON.parseObject(result, new TypeReference<CsfResultDto<ObjectMsg<OfferListResultOutputDto>>>() {
			});
			logger.info(result);
			if (json != null && json.getObject() != null && json.getObject().getResult() != null) {
				List<OfferItemOutputDto> list = json.getObject().getResult().getOfferList();
				if (list != null) {
					String time = sdt.format(new Date());
					List<OfferItemOutputDto> hasOffer = new ArrayList<>();
					for (int index = 0; index <= list.size() - 1; index++) {
						if (list.get(index).getOfferId().contains(prod)
								&& Long.valueOf(list.get(index).getEffTime()) >= (Long.valueOf(time) - 20)) {
							hasOffer.add(list.get(index));
						}
					}
					if (hasOffer.size() >= 1) {
						return hasOffer.get(hasOffer.size() - 1);
					}
				}
			}
			logger.info(result);
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			logger.error("subscribeInfoCentral" + e.getMessage());
		}
		return null;
	}

	public OfferItemOutputDto getOrderProdInfo(String phone, String prod, int eachNum) {
		OfferItemOutputDto dto = subscribeInfoCentral(phone, prod);
		if (dto == null) {
			eachNum++;
			if (eachNum >= 3) {
				return null;
			}
			dto = getOrderProdInfo(phone, prod, eachNum);
			return dto;
		} else {
			return dto;
		}
	}
}
