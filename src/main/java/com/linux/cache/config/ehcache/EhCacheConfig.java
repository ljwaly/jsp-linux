package com.linux.cache.config.ehcache;

import java.net.URL;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import net.sf.ehcache.CacheManager;



@Configuration
public class EhCacheConfig {

	private static String path = "ehcache.xml";
	
	/**
	 * 创建CacheManager
	 * 
	 * @param ehCacheManager
	 * @return
	 */
	@Bean("cacheManager")
	public CacheManager getCacheManager(EhCacheCacheManager ehCacheManager){
		return ehCacheManager.getCacheManager();
		
	}
	
	/**
	 * 提供 ehCacheManager
	 * @param cacheManagerFactory
	 * @return
	 */
	@Bean("ehCacheManager")
	public EhCacheCacheManager getEhCacheCacheManager(EhCacheManagerFactoryBean cacheManagerFactory){
		
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
		ehCacheCacheManager.setCacheManager(cacheManagerFactory.getObject());
		return ehCacheCacheManager;
	}
	
	/**
	 * 提供 cacheManagerFactory
	 * @return
	 */
	@Bean("cacheManagerFactory")
	public EhCacheManagerFactoryBean getCacheManagerFactory(){
		EhCacheManagerFactoryBean cacheManagerFactory = new EhCacheManagerFactoryBean();
		
		URL url = EhCacheConfig.class.getClassLoader().getResource(path);
		Resource configLocation = new UrlResource(url);
		cacheManagerFactory.setConfigLocation(configLocation);
		
		return cacheManagerFactory;
		
	}
	
	
	
}
