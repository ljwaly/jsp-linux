package com.linux.cache.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.linux.cache.CacheBeans;

import net.sf.ehcache.CacheManager;

/**
 * 创建每个SimpleCacheManager
 * @author PC
 *
 */
@Component
public class SimpleCacheManagerInit {
	
	
	private final static String REDIS_KEY_NAME = "redis";
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	
	
	public SimpleCacheManager init(String cacheName, Long expiryTime) {
		
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		
		
		List<org.springframework.cache.Cache> caches = new ArrayList<org.springframework.cache.Cache>(8);
		CacheBeans cacheBeans = new CacheBeans();
		
		
		//初始化EhCacheCache
		EhCacheCache ehCacheCache = new EhCacheCache(cacheManager.getCache(cacheName));
		cacheBeans.setCache(ehCacheCache);
		
		
		
		//初始化RedisCache
		RedisCache redisCache =  new RedisCache(REDIS_KEY_NAME, REDIS_KEY_NAME.getBytes(), redisTemplate, expiryTime);
		cacheBeans.setRedisCache(redisCache);
		
		
		/**
		 * 在AbstractCacheManager的cacheMap(type:ConcurrentMap类型)中绑定cacheName和cacheBeans这个对象
		 * 具体在cacheMap执行初始化方法initializeCaches()的时候，对缓存进行了绑定名称和bean
		 */
		cacheBeans.setName(cacheName);
		
		caches.add(cacheBeans);
		simpleCacheManager.setCaches(caches);
		return simpleCacheManager;
	}

	
	
	
}
