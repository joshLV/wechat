package com.bingosoft.utils;

public class LuaScriptUtils {
	public static final String INCR_BY_KEY="if redis.call('exists',KEYS[1]) then\r\n" + 
			"    redis.call('incr',KEYS[1]);\r\n" + 
			"else\r\n" + 
			"    redis.call('set',KEYS[1],1);\r\n" + 
			"end";
	
	public static final String CATE_INCR_BY_KEY="if redis.call('exists',KEYS[1]) then\r\n" + 
			"    redis.call('incr',KEYS[1]);\r\n" + 
			"else\r\n" + 
			"    redis.call('set',KEYS[1],1);\r\n" + 
			"end\r\n" + 
			"if redis.call('exists',KEYS[2]) then\r\n" + 
			"    redis.call('incr',KEYS[2]);\r\n" + 
			"else\r\n" + 
			"    redis.call('set',KEYS[2],1);\r\n" + 
			"end";
}
