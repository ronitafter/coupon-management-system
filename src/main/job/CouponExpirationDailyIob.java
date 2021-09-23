package com.ronit.job;

import com.ronit.dao.CouponsDao;
import com.ronit.dao.CouponsDbDao;
import com.ronit.exceptions.CouponSystemException;

public class CouponExpirationDailyIob implements Runnable {
	private CouponsDao couponsdao;
	private boolean quit;
	private static final long TWENTY_FOUR_HOURSE = 1000 * 60 * 60 * 24;
	private Thread thread;

	public CouponExpirationDailyIob() {
		this.couponsdao = new CouponsDbDao();
		quit = false;
	}

	@Override
	public void run() {
		System.out.println("Thread is running...");
		while (!quit) {
			try {
				System.out.println("deleting expired coupons");
				this.couponsdao.deleteExpiredCoupons();
			} catch (CouponSystemException e) {
				System.err.println("Daily Job encountered a problem. see details:");
				e.printStackTrace();
			}

			try {
				Thread.sleep(TWENTY_FOUR_HOURSE);
			} catch (InterruptedException e) {
				if (quit) {
					break;
				} else {
					System.out.println("doing quick check");
				}
			}
		}

		System.out.println("daily job stopped");
	}

	public void start() {
		this.thread = new Thread(this, "daily_job_thread");
		this.thread.start();
	}

	public void stop() {
		this.quit = true;
		this.thread.interrupt();
		// interrupt the thread that encapsulate this runnable, so as not to wait for 24
		// hours
		System.out.println("Thread interrupted to stop...");
	}

	public void checkNow() {
		this.thread.interrupt();
		// interrupt the thread that encapsulate this runnable, so as not to wait for 24
		// hours
		System.out.println("Thread interrupted for quick check...");
	}

	public Thread getThread() {
		return thread;
	}

}
