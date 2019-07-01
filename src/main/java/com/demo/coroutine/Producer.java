package com.demo.coroutine;

import kilim.Mailbox;
import kilim.Pausable;
import kilim.Task;

public class Producer extends Task<Object> {

	Integer count = null;
	Mailbox<Integer> mb = null;

	public Producer(Integer count, Mailbox<Integer> mb) {
		this.count = count;
		this.mb = mb;
	}

	public void execute() throws Pausable {
		count = count*10;
		for (int i = 0; i < 10; i++) {
			mb.put(count);//当空间不足时，阻塞协程线程
			System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + mb.size() + "生产了：" + count);
			count++;
		}
	}
	
}
