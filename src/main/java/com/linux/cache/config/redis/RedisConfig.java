package com.linux.cache.config.redis;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource(value = { "classpath:/redis-conf.properties" })
public class RedisConfig {

	/**
	 * 工作模式
	 */
	@Value("${redis.conf.redisWorkMode}")
	private String redisWorkMode = "stand_alone";

	@Value("${redis.conf.hostName}")
	private String hostName;

	/**
	 * redis配置的jedisPool参数
	 * 
	 * 控制一个pool最多有多少个状态为idle(空闲)的jedis实例；
	 */
	@Value("${redis.conf.maxIdle}")
	private int maxIdle;

//	/**
//	 * 本处没用 redis配置的jedisPool参数
//	 * 
//	 * 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；如果赋值为-1，则表示-不限制
//	 */
//	@Value("${redis.conf.maxActive}")
//	private int maxActive;

	@Value("${redis.conf.maxTotal}")
	private int maxTotal;
	
	@Value("${redis.conf.database}")
	private int database;

	/**
	 * 表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
	 */
	@Value("${redis.conf.maxWaitMillis}")
	private Long maxWaitMillis;

	/**
	 * 在获取连接的时候检查有效性, 默认false
	 * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	 */
	@Value("${redis.conf.testOnBorrow}")
	private boolean testOnBorrow;
	
	
	
	
	@Bean
	public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory factory){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(factory);
//		redisTemplate.afterPropertiesSet();
		return redisTemplate;
		
	}
	
	

	@Bean
	public RedisConnectionFactory getFactory() {
		
		switch (redisWorkMode) {
		
		case "stand_alone":
			
			return this.getStandAloneFactory();

		case "cluster":

			return this.getClusterFactory();
		case "sentinel":

			return null;
		}

		return null;

	}



	/**
	 * 
	 * ----------------------------------------------------------------
	 */

	private RedisConnectionFactory getClusterFactory() {
		String[] hostNames = parseSpilt(hostName, ",");
		List<String> ipList = Arrays.asList(hostNames);
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(ipList);
		redisClusterConfiguration.setMaxRedirects(1);//不知道什么作用
		JedisConnectionFactory factory = new JedisConnectionFactory(redisClusterConfiguration);
		factory.setUsePool(true);
		factory.setDatabase(database);
		factory.setPoolConfig(initJedisPoolConfig());
		factory.afterPropertiesSet();
		return factory;
	}

	/**
	 * 本地单独模式
	 * @return
	 */
	private RedisConnectionFactory getStandAloneFactory() {
		
		String[] info = parseSpilt(hostName, ":");
		
		//TODO：info长度验证
		
		String host = info[0];
		int port = Integer.parseInt(info[1]);
		
		JedisConnectionFactory factory = new JedisConnectionFactory();
		
		factory.setHostName(host);
		factory.setPort(port);
		
		factory.setUsePool(true);
		factory.setPoolConfig(initJedisPoolConfig());
		factory.setDatabase(database);
		
		factory.afterPropertiesSet();
		
		return factory;

	}

	/**
	 * 初始化JedisPoolConfig
	 * 
	 * @return
	 */
	private JedisPoolConfig initJedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		return jedisPoolConfig;
	}

	/**
	 * 拆分字符串
	 * 
	 * @param hostName2
	 * @return
	 */
	private String[] parseSpilt(String hostAndPort, String regex) {
		//TODO:非空验证
		String[] split = hostAndPort.split(regex);
		return split;
	}

}
