package com.bingosoft.utils;

public class LuaScriptUtils {
	public static final String INCR_BY_KEY="if redis.call('exists',KEYS[1]) then\r\n" + 
			"    redis.call('incr',KEYS[1]);\r\n" + 
			"else\r\n" + 
			"    redis.call('set',KEYS[1],1);\r\n" + 
			"end";
	
	public static final String CATE_INCR_BY_KEY_OLD="if redis.call('exists',KEYS[1]) then\r\n" + 
			"    redis.call('incr',KEYS[1]);\r\n" + 
			"else\r\n" + 
			"    redis.call('set',KEYS[1],1);\r\n" + 
			"end\r\n" + 
			"if redis.call('exists',KEYS[2]) then\r\n" + 
			"    redis.call('incr',KEYS[2]);\r\n" + 
			"else\r\n" + 
			"    redis.call('set',KEYS[2],1);\r\n" + 
			"end\r\n" + 
			"return redis.call('incr','llcs:order');";
	
	public static final String CATE_INCR_BY_KEY="redis.call('incr',KEYS[1]);\r\n" + 
			"redis.call('incr',KEYS[2]);\r\n" + 
			"return redis.call('incr','llcs:order');";
	
	public static final String GAME_SHARE_LUA_OLD="if redis.call('get',KEYS[1]) then\r\n" + 
			"    return \"0\"\r\n" + 
			"else\r\n" + 
			"    redis.call('set',KEYS[1],1)\r\n" + 
			"    redis.call('incr',KEYS[2])\r\n" + 
			"    redis.call('EXPIREAT',KEYS[1],ARGV[1])\r\n" + 
			"    return \"1\"\r\n" + 
			"end" 
			;
	
	public static final String GAME_SHARE_LUA="if redis.call('get',KEYS[1]) then\r\n" + 
			"    redis.call('del',KEYS[1]);\r\n" + 
			"    return redis.call('incr',KEYS[2]);\r\n" + 
			"else\r\n" + 
			"    return 0;\r\n" + 
			"end"
			;
	
	public static final String CATE_INCR_AND_CHANCE_OLD="if redis.call('exists',KEYS[1]) then\r\n" + 
			"    redis.call('incr',KEYS[1]);\r\n" + 
			"else\r\n" + 
			"    redis.call('set',KEYS[1],1);\r\n" + 
			"end\r\n" + 
			"if redis.call('exists',KEYS[2]) then\r\n" + 
			"    redis.call('incr',KEYS[2]);\r\n" + 
			"else\r\n" + 
			"    redis.call('set',KEYS[2],1);\r\n" + 
			"end\r\n" + 
			"redis.call('incr',KEYS[3]);\r\n" + 
			"return redis.call('incr','llcs:order');";
	
	public static final String CATE_INCR_AND_CHANCE="redis.call('incr',KEYS[1]);\r\n" + 
			"redis.call('incr',KEYS[2]);\r\n" + 
			"redis.call('incr',KEYS[3]);\r\n" + 
			"redis.call('set',KEYS[4],1);\r\n" + 
			"redis.call('EXPIREAT',KEYS[4],ARGV[1])\r\n" + 
			"return redis.call('incr','llcs:order');";
}
