package com.bingosoft.models.prefix;

public class TemplateTipsMsg {
	/**
	 * 流量小时包、叠加包、新闲时套餐5元
	 */
	public static final String XSB = "恭喜，业务已办理成功,并获赠100M流量。2018年01月31日之前,第66666名流量超市办理用户将获赠华为手机mate10一部,订购序号带6还送10元话费。您的业务办理情况如下：";
	//public static final String XSB = "恭喜，业务已办理成功。2018年01月31日之前,第66666名流量超市办理用户将获赠华为手机mate10一部,订购序号带6还送10元话费。您的业务办理情况如下：";

	/**
	 * 新超级周末、新闲时套餐10元、10元月末流量宝（大月末版）
	 */
	public static final String XCJZM = "恭喜，业务已办理成功,并获得100M省内流量赠送及10元话费赠送。2018年01月31日之前,第66666名流量超市办理用户将获赠华为手机mate10一部,订购序号尾数带6还送10元话费。您的业务办理情况如下：";
	//public static final String XCJZM = "恭喜，业务已办理成功,并获得10元话费赠送。2018年01月31日之前,第66666名流量超市办理用户将获赠华为手机mate10一部,订购序号尾数带6还送10元话费。您的业务办理情况如下：";

	public static final int[] ZSLL = { 10000, 10002, 10003, 10006, 10007, 10009, 10025, 10026, 10027, 10021 };

	public static final int[] ZSHF = { 10008, 10020, 10022 };

	public static String getTips(int goodsId) {
		String tips = "";
		switch (goodsId) {
		case 10000:
		case 10002:
		case 10003:
		case 10007:
		case 10009:
		case 10021:
		case 10023:
		case 10024:
		case 10025:
		case 10026:
		case 10027:
		case 10028:
		case 10029:
		case 10031:
		case 10032:
		case 10033:
		case 10034:
		case 10036:
		case 10037:
		case 10038:
		case 10039:
		case 10040:
		case 10041:
		case 10042:
		case 10043:
		case 10044:
		case 10045:
		case 10046:
		case 10047:
		case 10048:
		case 10141:
		case 10143:
			tips = XSB;
			break;
		case 10030:
		case 10020:
		case 10022:
		case 10006:
		case 10008:
		case 10142:
			tips = XCJZM;
			break;
		default:
			break;
		}
		return tips;
	}
}
