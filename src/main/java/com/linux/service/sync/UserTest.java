package com.linux.service.sync;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserTest implements Runnable{
	
	private String userId;
	private String userToken;

	/**
	 * 模拟本地锁
	 */
	public static Map<String, UserLock> myMap = new ConcurrentHashMap<String, UserLock>();
	
	/**
	 * 模拟session接口
	 */
	public static Map<String, String> resultMap = new HashMap<String, String>();
	
	
	public UserTest(String userId, String userToken) {
		super();
		this.userId = userId;
		this.userToken = userToken;
	}

	public String test(String userId, String userToken) {
		
		String session = "begin";
		
		String lockKey = getLockKey(userId,userToken);
		
		session = resultMap.get(lockKey);
		
		if (session == null) {
			initUserSession(userId, userToken);
		}
		
		session = resultMap.get(lockKey);
		return session;
	}

	/**
	 * 首次调用接口，初始化session
	 * @param userId
	 * @param userToken
	 */
	private void initUserSession(String userId, String userToken) {
		String lockKey = this.getLockKey(userId, userToken);
		
		UserLock lock = this.getLock(lockKey);
		if (lock.pleaseWait(true)) {
			System.out.println("ljw 每次调用1次");
			if (resultMap.get(lockKey) == null) {
				resultMap.put(lockKey, userId+"_"+userToken);
				System.out.println("ljw搜索接口");
			}
			lock.pleaseWait(false);
		}
		
	}
	
	private String getLockKey(String userId, String userToken){
		return userId+"_"+userToken;
	}
	
	private UserLock getLock(String lockKey) {
		myMap.putIfAbsent(lockKey, new UserLock());
		return myMap.get(lockKey);
		
	}

	@Override
	public void run() {
		String test = test(userId, userToken);
		System.out.println("result="+test);
		
	}
	
}
