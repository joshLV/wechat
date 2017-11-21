package com.bingosoft.persist.redis.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.serialize.JsonUtils;
import com.bingosoft.utils.serialize.ObjectTranscoder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisServiceImpl implements IRedisService{
	@Autowired
	private ShardedJedisPool convertJedisPool;

	@Autowired
	private JedisPool jedisPool;

	public String evalScript(String script, int keysCount, String[] params) {
		Jedis jedis = jedisPool.getResource();
		Object object = jedis.eval(script, keysCount, params);
		 if(jedis != null){  
	            jedisPool.returnResourceObject(jedis);  
	        }  
		return object == null ? "" : object.toString();
	}

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	public boolean set(final String key, final String value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.set(serializer.serialize(key), serializer.serialize(value));
				
				return true;
			}
		});
		return result;
	}

	@Override
	public boolean set(String key, String value, Date expire) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				//redisTemplate.expireAt(key, expire);
				connection.set(serializer.serialize(key), serializer.serialize(value));
				connection.expireAt(serializer.serialize(key), expire.getTime());
				return true;
			}
		});
		return result;
	}

	@Override
	public boolean set(String key, String value, int SECONDS) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.set(serializer.serialize(key), serializer.serialize(value));
				if(SECONDS>0)
					connection.expire(serializer.serialize(key),Long.valueOf(SECONDS));
				return true;
			}
		});
		return result;
	}

	@Override
	public boolean set(final String key, final byte[] value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.set(serializer.serialize(key), value);
				return true;
			}
		});
		return result;
	}

	public String get(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] value = connection.get(serializer.serialize(key));

				return serializer.deserialize(value);
			}
		});
		return result;
	}

	@Override
	public boolean expire(final String key, long expire) {
		return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

	@Override
	public <T> boolean setList(String key, List<T> list) {
		String value = JsonUtils.toJson(list);
		return set(key, value);
	}

	@Override
	public <T> List<T> getList(String key, Class<T> clz) {
		String json = get(key);
		if (json != null) {
			List<T> list = JsonUtils.toList(json, clz);
			return list;
		}
		return null;
	}

	/**
	 * 设置 map
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 */
	public <T> void setMap(String key, Map<String, T> map) {
		try {
			set(key, ObjectTranscoder.serialize(map));
		} catch (Exception e) {
			// logger.warn("Set key error : "+e);
		}
	}

	@Override
	public long lpush(final String key, Object obj) {
		final String value = JsonUtils.toJson(obj);
		long result = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
				return count;
			}
		});
		return result;
	}

	@Override
	public long rpush(final String key, Object obj) {
		final String value = JsonUtils.toJson(obj);
		long result = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
				return count;
			}
		});
		return result;
	}

	@Override
	public String lpop(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] res = connection.lPop(serializer.serialize(key));
				return serializer.deserialize(res);

			}
		});
		return result;
	}

	@Override
	public List<String> lrange(String key, int index, int count) {

		// TODO Auto-generated method stub
		List<String> result = redisTemplate.execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				List<byte[]> res = connection.lRange(serializer.serialize(key), index, count);
				List<String> headImg = new ArrayList<String>();
				for (int index = 0, endIndex = res.size() - 1; index <= endIndex; index++) {
					headImg.add(serializer.deserialize(res.get(index)));
				}
				return headImg;

			}
		});
		return result;
	}
}
