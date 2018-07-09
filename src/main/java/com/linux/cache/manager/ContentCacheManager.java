package com.linux.cache.manager;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.linux.vo.Content;



@Component
public class ContentCacheManager {

	@Cacheable(value = {"Content"}, cacheManager = "contentEhCacheManager", key = "#contentId")
	public Content get(String contentId) {
		
		System.out.println("new Content");
		Content content = new Content();
		content.setContentId(contentId);
		content.setContName("abc");
		content.setDetail("this is my cache");
		return content;

	}

}
