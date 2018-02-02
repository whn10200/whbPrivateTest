package com.whb.redisson.demoTwo.util;

import com.whb.redisson.demoTwo.uuid.UUidGenerator;

public class ThreadUtil extends Thread {

	private UUidGenerator uuid;
	private String perKey;
	private int num;

	/**
	 *
	 * @param uuid
	 *            UUidGenerator
	 * @param perKey
	 *            key
	 * @param num
	 *            循环的次数
	 */
	public ThreadUtil(UUidGenerator uuid, String perKey, int num) {
		this.uuid = uuid;
		this.perKey = perKey;
		this.num = num;
	}

	@Override
	public void run() {
		for (int i = 0; i < num; i++) {
			try {
				Thread.sleep(100);// 休眠1s
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int nextIntUuid = uuid.getNextIntUuid(perKey, 10);
			System.out.println(Thread.currentThread().getName() + " nextIntUuid = " + nextIntUuid);
		}

	}

}
