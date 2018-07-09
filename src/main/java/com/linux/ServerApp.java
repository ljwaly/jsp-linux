package com.linux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;

import com.linux.config.ServerApplictionContext;

/**
 * 启动类（因为使用redis缓存，启动之前，先启动redis）
 * 
 * @author PC
 *
 */
@EnableCaching
@SpringBootApplication
public class ServerApp {
	
	private static final Logger log = LoggerFactory.getLogger(ServerApp.class);
	
	
	
	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(ServerApp.class);
		
		Environment env = app.run(args).getEnvironment();
		log.info("\n----------------------------------------------------------\n\t"
				+ "Application {} is running! Access URLs:\n\t" + // 大括号1
				"Local: \t\thttp://localhost:{}\n\t" + // 大括号2
				"{}\n\t" + // 大括号3
				"\n----------------------------------------------------------",

				env.getProperty("spring.application.name"), // 大括号1对应的内容
				env.getProperty("server.port"), // 大括号2对应的内容
				"Thank You!"// 大括号3对应的内容
				
				);
	}
	
	
}
