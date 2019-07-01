package com.demo.coroutine;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import kilim.Mailbox;
import kilim.Task;

/**
 * Hello world!
 *
 */
public class Coroutine  {

	static Map<Integer, Mailbox<Integer>> mailMap = new HashMap<Integer, Mailbox<Integer>>();

	public static void main(String[] args) {

		if (kilim.tools.Kilim.trampoline(false,args)) return;
		Properties propes = new Properties();
		propes.setProperty("kilim.Scheduler.numThreads", "4");
		System.setProperties(propes);
		long startTime = System.currentTimeMillis();
		
		for (int i = 0; i < 1000; i++) {
			Mailbox<Integer> mb = new Mailbox<Integer>(1, 10);
			new Producer(i, mb).start();
			mailMap.put(i, mb);
		}
		
		for (int i = 0; i < 1000; i++) {
			new Consumer(mailMap.get(i)).start();
		}
		
		Task.idledown();
		

		 long endTime = System.currentTimeMillis();
	        
	     System.out.println( Thread.currentThread().getName()  + "总计花费时长：" + (endTime- startTime));
	}
	
}


