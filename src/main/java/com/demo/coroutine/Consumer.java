package com.demo.coroutine;

import kilim.Mailbox;
import kilim.Pausable;
import kilim.Task;

public class Consumer extends Task<Object> {

	Mailbox<Integer> mb = null;

	public Consumer(Mailbox<Integer> mb) {
		this.mb = mb;
	}

	/**
	 * 执行
	 */
	public void execute() throws Pausable {
		Integer c = null;
		for (int i = 0; i < 10000; i++)  {
			c = mb.get();//获取消息，阻塞协程线程
			
			if (c == null) {
				System.out.println("计数");
			}else {
				System.out.println(Thread.currentThread().getName() + "消费者消费，目前总共有" + mb.size() + "消费了：" + c);
				c = null;
			}
		}
	}
	
}
