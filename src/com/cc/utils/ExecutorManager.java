package com.cc.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorManager {

	//�ṩ��̬����
	private static ExecutorManager instance;
	//�ṩ��̬��������ͬ���飬��ֹ���̴߳���
	public static ExecutorManager getInstance(){
		if (instance==null){
			//����
			synchronized (ExecutorManager.class) {
				if (instance==null){
					instance=new ExecutorManager();
				}
			}
			
		}
		return instance;
	}
	
	private ExecutorService executorService;
	//����ģʽ
	private ExecutorManager(){
		init();
	}
	//��ʼ���̳߳�
	private void init(){
		int num=2*Runtime.getRuntime().availableProcessors()+1;
		int max=num>8?8:num;
		executorService=Executors.newFixedThreadPool(max);
	}
	/**
	 * ִ������
	 * @param runnable
	 */
	public void execute(Runnable runnable){
		this.executorService.execute(runnable);
	}
}
