package com.cc.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorManager {

	//提供静态变量
	private static ExecutorManager instance;
	//提供静态方法，加同步块，防止多线程创建
	public static ExecutorManager getInstance(){
		if (instance==null){
			//加锁
			synchronized (ExecutorManager.class) {
				if (instance==null){
					instance=new ExecutorManager();
				}
			}
			
		}
		return instance;
	}
	
	private ExecutorService executorService;
	//单立模式
	private ExecutorManager(){
		init();
	}
	//初始化线程池
	private void init(){
		int num=2*Runtime.getRuntime().availableProcessors()+1;
		int max=num>8?8:num;
		executorService=Executors.newFixedThreadPool(max);
	}
	/**
	 * 执行任务
	 * @param runnable
	 */
	public void execute(Runnable runnable){
		this.executorService.execute(runnable);
	}
}
