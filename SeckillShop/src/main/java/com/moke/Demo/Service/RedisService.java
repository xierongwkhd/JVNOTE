package com.moke.Demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moke.Demo.base.redis.KeyPrefix;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
	
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * 获取单个对象
	 * @param <T>
	 * @param prefix
	 * @param key
	 * @param clazz
	 * @return
	 */
	
	public <T> T get(KeyPrefix prefix,String key,Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = jedis.get(prefix.getPrefix()+key);
			T t = stringToBean(str,clazz);
			return t;
		}finally {
			returntoPool(jedis);
		}
	}
	
	/**
	 *    设置对象
	 * @param <T>
	 * @param prefix
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> Boolean set(KeyPrefix prefix,String key,T value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = beanToString(value);
			int expire = prefix.expierSeconds();
			if(expire <= 0) {
				jedis.set(prefix.getPrefix()+key, str);
			}else {
				jedis.setex(prefix.getPrefix()+key, expire, str);
			}
			return true;
		}finally {
			returntoPool(jedis);
		}
	}
	
	/**
	 * 删除
	 * @param <T>
	 * @param prefix
	 * @param key
	 * @return
	 */
	public <T> boolean delete(KeyPrefix prefix,String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long ret = jedis.del(prefix.getPrefix()+key);
			return ret>0?true:false;
		}finally {
			returntoPool(jedis);
		}
	}
	
	/**
	 * 判断是否存在
	 * @param <T>
	 * @param prefix
	 * @param key
	 * @return
	 */
	public <T> Boolean exists(KeyPrefix prefix,String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(prefix.getPrefix()+key);
		}finally {
			returntoPool(jedis);
		}
	}
	
	/**
	 * 增加
	 * @param <T>
	 * @param prefix
	 * @param key
	 * @return
	 */
	public <T> Long incr(KeyPrefix prefix,String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.incr(prefix.getPrefix()+key);
		}finally {
			returntoPool(jedis);
		}
	}
	
	/**
	 * 减少
	 * @param <T>
	 * @param prefix
	 * @param key
	 * @return
	 */
	public <T> Long decr(KeyPrefix prefix,String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.decr(prefix.getPrefix()+key);
		}finally {
			returntoPool(jedis);
		}
	}
	
	private <T> String beanToString(T value) {
		if(value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz == int.class || clazz == Integer.class) {
			return ""+value;
		}else if(clazz == String.class) {
			return (String)value;
		}else if(clazz == long.class || clazz == Long.class){
			return ""+value;
		}else {
			return JSON.toJSONString(value);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T stringToBean(String str,Class<T> clazz) {
		if(str == null || str.length()==0 || clazz==null)
			return null;
		if(clazz == int.class || clazz == Integer.class) {
			return (T)Integer.valueOf(str);
		}else if(clazz == String.class) {
			return (T)str;
		}else if(clazz == long.class || clazz == Long.class){
			return (T)Long.valueOf(str);
		}else {
			return JSON.toJavaObject(JSON.parseObject(str),clazz);
		}
	}

	private void returntoPool(Jedis jedis) {
		if(jedis!=null) {
			jedis.close();
		}
		
	}
}
