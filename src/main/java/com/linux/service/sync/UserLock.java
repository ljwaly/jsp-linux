package com.linux.service.sync;

import java.util.concurrent.atomic.AtomicBoolean;

public class UserLock {
	private AtomicBoolean wait = new AtomicBoolean(false);

	public boolean pleaseWait(boolean flag) {

		synchronized (this.wait) {

			if (flag) {
				if (this.wait.get() == true) {
					try {
						System.out.println("wait");
						this.wait.wait();// 等待
						return false;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return true;// 等待失效，则直接调用接口
				}
				this.wait.set(true);// 首次进入
				System.out.println("首次进入");
				return true;

			}
			this.wait.set(false);// 首次进入
			System.out.println("唤醒等待进程");
			this.wait.notifyAll();

			return false;

		}
	}



}
