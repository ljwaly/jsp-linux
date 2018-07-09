package com.linux.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest implements Runnable{
	
	private String userId;
	private String userToken;

	public static Map<String, Lock> myMap = new ConcurrentHashMap<String, Lock>();
	
	
	
	public LockTest(String userId, String userToken) {
		super();
		this.userId = userId;
		this.userToken = userToken;
	}

	public void test(String userId, String userToken) {
		Lock lock = this.getLock(userId, userToken);
		lock.lock();
		if (lock.tryLock()) {
			System.out.println("ljw");
			try {
				Thread.sleep(2000);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			lock.notifyAll();
		}else{
			try {
				lock.wait();
				System.out.println("122");
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		lock.unlock();
	}
	
	private Lock getLock(String userId, String userToken) {
		String key = userId+"_"+userToken;
//		if (myMap.containsKey(key)) {
//			return myMap.get(key);
//		}else {
//			Lock lock = new ReentrantLock();
//			myMap.put(key, lock);
//		}
		myMap.putIfAbsent(key, new ReentrantLock());
		return myMap.get(key);
		
	}

	@Override
	public void run() {
		test(userId, userToken);
		
	}
	
}
