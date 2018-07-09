package com.linux;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;



/**
 * 配置外部tomcat启动
 * @author PC
 *
 */
public class TomcatApp extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		//DefaultProfileUtil.addDefaultProfile(builder.application());
		return builder.sources(ServerApp.class);
	}
	
}
