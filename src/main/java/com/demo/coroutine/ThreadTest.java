package com.demo.coroutine;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class ThreadTest {
	private static Integer count = 0;
	private static final Integer FULL = 10;
	private static String LOCK = "lock";

	public static void main(String[] args) {
		ThreadTest test1 = new ThreadTest();

		long start = System.currentTimeMillis();

		List<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < 1000; i++) {
			Thread thread = new Thread(test1.new Producer());
			thread.start();
			list.add(thread);
		}

		for (int i = 0; i < 1000; i++) {
			Thread thread = new Thread(test1.new Consumer());
			thread.start();
			list.add(thread);
		}

		try {
			for (Thread thread : list) {
				thread.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		System.out.println("子线程执行时长：" + (end - start));
	}

	class Producer implements Runnable {
		public void run() {
			for (int i = 0; i < 10; i++) {
				synchronized (LOCK) {
					while (count == FULL) {
						try {
							LOCK.wait();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					count++;
					System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + count);
					LOCK.notifyAll();
				}
			}
		}
	}

	class Consumer implements Runnable {
		public void run() {
			for (int i = 0; i < 10; i++) {
				synchronized (LOCK) {
					while (count == 0) {
						try {
							LOCK.wait();
						} catch (Exception e) {
						}
					}
					count--;
					System.out.println(Thread.currentThread().getName() + "消费者消费，目前总共有" + count);
					LOCK.notifyAll();
				}
			}
		}
	}
}
