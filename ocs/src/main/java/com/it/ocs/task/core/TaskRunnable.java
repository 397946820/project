package com.it.ocs.task.core;

public abstract class TaskRunnable implements Runnable {
	
	private Object service;
	
	public TaskRunnable(){
		
	}
	public TaskRunnable(Object service){
		this.service = service;
	}
	
	@Override
	public void run() {
		runTask();
	}
	
	protected abstract void runTask();

	public Object getService() {
		return service;
	}

	public void setService(Object service) {
		this.service = service;
	}
	
	
}
