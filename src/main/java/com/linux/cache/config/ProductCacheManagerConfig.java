package com.linux.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/**
 * 所有的contentEhCacheManager的清单列表
 * 
 * @author PC
 *
 */
@Configuration
@PropertySource(value = "classpath:/cache-init-param.properties")
public class ProductCacheManagerConfig {
	
	@Autowired
	private SimpleCacheManagerInit simpleCacheManagerinit;

	@Primary
	@Bean("contentEhCacheManager")
	public SimpleCacheManager getContentCacheManager(@Value(value = "${content.ehcache.cacheName}") String cacheName, @Value(value = "${content.redis.expiryTime}") Long expiryTime){
		return simpleCacheManagerinit.init(cacheName, expiryTime);
		
	}
}
