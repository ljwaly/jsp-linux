package com.linux.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linux.cache.manager.ContentCacheManager;
import com.linux.config.ServerApplictionContext;
import com.linux.service.sync.UserTest;
import com.linux.vo.Content;

@RestController
@RequestMapping("/my")
public class MyController {
	
	@Autowired
	private ServerApplictionContext serverApplictionContextUtil;
	
	@Autowired
	private ContentCacheManager contentCacheManager;
	
	@RequestMapping("/test")
	public Map<String, Object> getTest(Date date){
		
		System.out.println(date);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		
		Thread t1 =new Thread(new UserTest("123", "abc"),"t1");
		Thread t2 =new Thread(new UserTest("123", "abc"),"t2");
		Thread t3 =new Thread(new UserTest("123", "abc"),"t3");
		Thread t4 =new Thread(new UserTest("456", "def"),"t4");
		Thread t5 =new Thread(new UserTest("456", "def"),"t5");
		Thread t6 =new Thread(new UserTest("456", "def"),"t6");
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		
		System.out.println(serverApplictionContextUtil.getAppContext().getDisplayName());
		return map;
	}
	
	
	@RequestMapping("/cache")
	public Map<String, Object> getContent(HttpServletRequest request, String contentId){
		
		/**
		 * 预置参数测试
		 */
		String test = request.getParameter("test");
		System.out.println("test="+test);
		String ljw = request.getHeader("ljw");
		System.out.println("ljw="+ljw);
		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		Content content = contentCacheManager.get(contentId);
		map.put("result", content);
		return map;
		
	}

}
