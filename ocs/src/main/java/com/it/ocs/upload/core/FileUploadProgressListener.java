package com.it.ocs.upload.core;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

public class FileUploadProgressListener implements ProgressListener {
	
	private HttpSession session;  
	
    public FileUploadProgressListener(HttpSession session){  
        super();
    	this.session=session;  
        Progress status = new Progress();//保存上传状态  
        session.setAttribute("status", status);  
    }  
	
	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		Progress status = (Progress) session.getAttribute("status");  
        try {  
            Thread.sleep(5);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        status.setValue(pBytesRead);  
        status.setMax(pContentLength);  
        status.setItem(pItems);  
		
	}

}
