package com.bingosoft.persist.redis.dao;

import java.util.Date;
import java.util.List;

public interface IRedisService {
	public boolean set(String key, String value);

	public boolean set(String key, String value, Date expire);

	boolean set(String key, byte[] value);

	public String get(String key);

	// public byte[] getByte(String key);

	public boolean expire(String key, long expire);
	
	public boolean set(String key, String value, int SECONDS) ;

	public <T> boolean setList(String key, List<T> list);

	public <T> List<T> getList(String key, Class<T> clz);

	public long lpush(String key, Object obj);

	public long rpush(String key, Object obj);

	public String lpop(String key);

	public List<String> lrange(String key,int index ,int count);
	
	public String evalScript(String script,int keysCount,String[] params);
	
	public boolean del(final String key) ;
}
