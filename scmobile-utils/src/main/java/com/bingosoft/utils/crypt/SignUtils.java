package com.bingosoft.utils.crypt;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sitech.common.util.MD5;
import com.sitech.common.util.RSAUtils;
import com.sitech.common.util.StringUtil;

public class SignUtils {
	public static final String priKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC4kQA++0H2XoRT9O2AYJZjQNw6eN/Y+KuMVl+7GhVbdQsX8UwYcYobRX77NPHkU2WfrnlvqAnDWmelnA7dNih3xGa4utBg/0u4khYK6xSuDt5AwRi2j3+KQBdggsc5Jnjc/c659dafnN1R16rVCQscn1v78wYM9vcQCpfylVHXPbDvZxqK3Ew4t75t+msbuOCei/E7g9xCnw/KyET2Vn+NyWruW76sULMXtI9U3uJWM9l/2BPH61hAfXjvkX4U1LGN/Ote48WwCThb76Vmn6s1zSbmCo8OTsIPJSMkozn3gud7F9d03/VBLK/sGmxiLnt8GayB1aisrrF8kH0RIo6BAgMBAAECggEBAItJisj9L8uxiuYderueqQAgq9NI8444JKbVbIu0ZJfP3d5k0QvFa/em2+zTCRZKRfBbaPP1+eA/MudNvf396E5ynx8chTQeltOlFBOLU+EEAYEW+QKViygXtEy2hYlA2ofU15WJzT5J0ND6FMePep5QOcgLoO5Z1xx5EjEVtVzcvkCSM4bx7g8qK74RFwY0a1ftoQbNblQvy+mhj84NDZtWGUCMLIj/tFx19aWeJb+jdNHUwlsNzkJOGlMkD+ryHhMU36A6HlLwO0HfqDcXCtW4PrcXNTrpxsU+738hmFAemtusxzXvRIBtmHV4LcBk+YmzMxleVOR283h/aXvDzs0CgYEA5W+kDDOdbPZP8JTqRNWBZ8pA995leJoIMtUv9t0gGYSB5cbk1P6B+pJ94KVEbQsOChTdwsxqjBKHehhgjut1ZYgtaausO0Vy2VwI1Tqf3koExGhEkCDcEa5ZHQgODy9wqA5Mzv5yGnxB87O2vntotukUvBX6755I290EHozscC8CgYEAze9yiZ9DHHm7COOsvRtQz5UdXQUyXOFFZcD2fukDp6E2T+JyCV2UBJIPDJSzFW4MZhr8HdrWJfTuCWSo4fUkGVFJju9BN4sFxJ6b0/OgQx3HAXMoZ9A+tT+lbC1Lp7tUEOGCOlxcO7JW+fbo3KdBB5L9Vi25fdgDqqz0bConEE8CgYBjo1kdx2iNgmZy92jJneMF3Orb+iQc/N+km7qpvZwMGOSEkhycdy6hT+8EmSEfLdbZ//fSeg2naQCWwHFmf9DK2BnIszonv2BCDtrzJvN4iLKLTTDBWtPXhdzDY9UfOR32jcdDeXK6F85YdGpfTJ7MGGrdx7njR90iRlhcoByqFwKBgBamR0qroY34NYH8yHrkDjdGhzw73hYpEKDoYDQFHMnLCVYv6aruB+GYzt7rW+b3e5WfUitWbkcucR/HWmu/soC2WAjId7vMJo5Bg5IEMZQwKjsWl1MTrFHy9ha3xKJmEBDrPI9+qQZHzxke9u2N17ElkYdw7/3PTFfxPdDHshQxAoGBAJWRZ6yOJuo7h+Ov1Bl4wCKVYz9AZ+2UYwL7Gm3lX9A1FtTtImZj9S98xUFlfnCyg3zA/h0s9j0kk2eHX4eOXtlt1C0iVKkDjHb6V4fxD9iqprRZtthjvX6e+LjnxUTHdLJWsc/BSJ7Zpa9P6DfCo07TDW8vRX9pX/bgUKnYPSbn";
	public static final PrivateKey skPrivateKey = RSAUtils.stringToPrivateKey(priKey);
	
	
	public static String setHttpGetReqParam(Map<String, String> reqParams)
	{
		if (reqParams != null) {
			StringBuffer sb = new StringBuffer();
	        Set es = reqParams.entrySet();
	        Iterator it = es.iterator();
	        while(it.hasNext()) {
	            Map.Entry entry = (Map.Entry)it.next();
	            String k = (String)entry.getKey();
	            Object v = entry.getValue();
	            //if(null != v && !"".equals(v)
	            if(null != v  
	                    && !"sign".equals(k) && !"key".equals(k)) {
	                sb.append(k + "=" + v + "&");
	            }
	        }
	        
	        String orginUrlParms=sb.toString();
	        String orginStr = StringUtil.sortOrginReqStr(orginUrlParms.substring(0, orginUrlParms.length()-1));// 参数排序
	        
	        return "?" + orginStr + "&sign=" + getSign(orginStr);
		}
		

		return "";
	}
	
	public static String getSign(String paraStr) {
		String sign = null;
		try {
			String orginStr = URLEncoder.encode(paraStr, "UTF-8");
			String md5str = MD5.ToMD5(orginStr);
			String signStr = RSAUtils.sign(md5str.getBytes(), skPrivateKey);
			sign = URLEncoder.encode(signStr, "UTF-8");
		} catch (Exception e) {
		}
		return sign;
	}
}
