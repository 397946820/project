package com.it.ocs.upload.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class CustomMultipartResolver extends CommonsMultipartResolver{

	@Override
	protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
		 HttpSession session = request.getSession();
	        String encoding = "utf-8";
	        FileUpload fileUpload = prepareFileUpload(encoding);
	        FileUploadProgressListener uploadListener = new FileUploadProgressListener(session);
	        fileUpload.setProgressListener(uploadListener);
	        try {
	            List<FileItem> fileItem = ((ServletFileUpload)fileUpload).parseRequest(request);
	            return parseFileItems(fileItem, encoding);
	        } catch (FileUploadBase.SizeLimitExceededException ex) {
	            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
	        }catch (FileUploadException ex) {
	            throw new MultipartException("Could not parse multipart servlet request",ex);
	        }
	}

}
