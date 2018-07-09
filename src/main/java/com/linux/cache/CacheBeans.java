package com.linux.cache;

import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.data.redis.cache.RedisCache;



/**
 * 
 * 通用的cachebean，为不同的产品提供不同的缓存
 * 
 * @author PC
 *
 */
public class CacheBeans implements Cache {
	
	/**
	 * 二级缓存
	 * 
	 * 需要在初始化{@link SimpleCacheManagerInit}的时候，将名字在进行初始化，不然，在创建的时候找不到：
	 * 例如ehcache.xml中是Content，那么在初始化的时候，这个name要设置为Content,
	 * 就是三者要一致
	 * 1.ehcache.xml中的标签名字
	 * 2.cache-init-param.properties中的*.cache.name
	 * 3.本类的字段：name
	 */
	private String name;
	/**
	 * 使用的缓存
	 */
	private EhCacheCache cache;

	
	/**
	 * 回头做redis是否存活的保护，如果没有建立成功，则，不进行redisCache的操作
	 */
	private RedisCache redisCache;
	
	
	
	



	/** 从缓存中获取 key 对应的值（包含在一个 ValueWrapper 中）
	 * 
	 *  在首次调用的时候，会进入这个方法，如果缓存中没有，就会去执行真正的获取方法，
	 *  得到结果之后，调用{@code CacheBeans}的put(Object key, Object value){}
	 *  
	 *  可以打断点在这里查看key的样例
	 */
	@Override
	public ValueWrapper get(Object key) {
		ValueWrapper value = cache.get(key);
		if (value != null) {
			System.out.println("getEhCacheValue");
			return value;
		}
		
		//TODO:其他缓存配置，类似redis的缓存
		if (redisCache != null) {
			System.out.println("getRedisCacheValue");
			value = redisCache.get(key);
		}
		
		return value;
	}

	/** 从缓存中获取 key 对应的指定类型的值（4.0版本新增） */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		Object value = cache.get(key.toString());
		if (null != value) {
			return (T) value;
		}
		
		value = redisCache.get(key.toString());
		if (null != value) {
			return (T) value;
		}
		
		return null;
	}

	/**
	 * 从缓存中获取 key 对应的值，如果缓存没有命中，则添加缓存， 此时可异步地从 valueLoader 中获取对应的值（4.3版本新增）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		return (T) this.get(key.toString());
	}

	/** 缓存 key-value，如果缓存中已经有对应的 key，则替换其 value */
	@Override
	public void put(Object key, Object value) {
		cache.put(key, value);
		redisCache.put(key, value);
	}

	/** 缓存 key-value，如果缓存中已经有对应的 key，则返回已有的 value，不做替换 */
	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		
		ValueWrapper valueWrapper = cache.putIfAbsent(key, value);
		redisCache.putIfAbsent(key, valueWrapper);
		
		return valueWrapper;
		
	}

	/** 从缓存中移除对应的 key */
	@Override
	public void evict(Object key) {
		cache.evict(key);
		redisCache.evict(key);
	}
	
	/** 清空缓存 */
	@Override
	public void clear() {
		cache.clear();
		redisCache.clear();
	}

	/** 获取真正的缓存提供方案 
	 * 
	 * 暂时不知道怎么用
	 */
	@Override
	public Object getNativeCache() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String getName() {
		return this.name;
	}

	
	public EhCacheCache getCache() {
		return cache;
	}



	public void setCache(EhCacheCache cache) {
		this.cache = cache;
	}


	public void setName(String name) {
		this.name = name;
	}

	public RedisCache getRedisCache() {
		return redisCache;
	}

	public void setRedisCache(RedisCache redisCache) {
		this.redisCache = redisCache;
	}
	
	

}
