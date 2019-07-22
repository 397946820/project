package com.it.ocs.task.core;

public class Timer {
	private long runTime;
	private long startTime;
	private long totalTime;
	
	public Timer(long runTime){
		this.runTime = runTime;
		this.startTime = System.currentTimeMillis();
	}
	
	private long getCurtime(){
		return System.currentTimeMillis();
	}
	
	public boolean isContinue(){
		return getCurtime() - startTime < runTime;
	}
	
	public long getUseTotalTime(){
		return getCurtime() - startTime;
	}
	
	public long getRemainingTime(){
		if(isContinue()){
			long reTime = runTime-(getCurtime()-startTime);
			return reTime>0?reTime:0;
		}else{
			return 0;
		}
	}
}
