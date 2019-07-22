package com.it.ocs.upload.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class JSCHImageUploadSupport {
	
	private final static Logger log = Logger.getLogger(JSCHImageUploadSupport.class);
	
	private final static String HOST_NAME = "144.217.149.37";// ftp服务器地址

	private final static Integer PORT = 22; // SFTP默认端口

	private final static String USER_NAME = "ebayimage";

	private final static String PASS_WORD = "RpL!hxGKX%";
	
	private ChannelSftp sftp = null;
	private Channel channel = null;
	private Session sshSession = null;
	
	public static JSCHImageUploadSupport getInstance(){
		int i = 1;
		JSCHImageUploadSupport jsCHImageUploadSupport= null;
		while (i < 11){
			jsCHImageUploadSupport = new JSCHImageUploadSupport();
			if(jsCHImageUploadSupport.hasConnect()){
				i = 12;
			}else{
				log.error("第"+i+"次与图片服务器建立连接失败,稍后重试.");
				i++;
			}
		}
		return jsCHImageUploadSupport;
	}
	public JSCHImageUploadSupport(){
		try {
			JSch jsch = new JSch();
			sshSession = jsch.getSession(USER_NAME, HOST_NAME, PORT);
			sshSession.setPassword(PASS_WORD);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			sftp.cd("javaimg");
		} catch (Exception e) {
			log.error("与图片服务器建立连接失败.",e);
		}
	}
	public boolean hasConnect(){
		if(null == sftp){
			return false;
		}
		return true;
	}
	public boolean put(String fileName,String filePath){
		boolean uploadFlag = true;
		InputStream is = null;
		try {
			File file = new File(filePath);
			is = new FileInputStream(file);
			sftp.put(is,fileName);
		} catch (FileNotFoundException e) {
			log.error("图片"+filePath+"上传服务器失败,图片被删除或者转移.");
			uploadFlag = false;
		} catch (SftpException e) {
			log.error("图片"+filePath+"上传服务器失败,上传异常.",e);
			uploadFlag = false;
		}finally{
			if(null != is){
				IOUtils.closeQuietly(is);
			}
		}
		return uploadFlag;
	}
	
	public void disConnect(){
		closeChannel(sftp);
		closeChannel(channel);
		closeSession(sshSession);
	}
	private static void closeChannel(Channel channel) {
		if (channel != null) {
			if (channel.isConnected()) {
				channel.disconnect();
			}
		}
	}
	private static void closeSession(Session session) {
		if (session != null) {
			if (session.isConnected()) {
				session.disconnect();
			}
		}
	}
}
