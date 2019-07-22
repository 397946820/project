package com.it.ocs.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FTPUtil {
	private static String HOST_NAME = "144.217.149.37";// ftp服务器地址

	private static Integer PORT = 22; // SFTP默认端口

	private static String USER_NAME = "ebayimage";

	private static String PASS_WORD = "RpL!hxGKX%";
	private static ChannelSftp SFTP = null;
	private static Channel CHANNEL = null;
	private static Session SSHSESSION = null;
	static {
		JSch jsch = new JSch();
		try {
			SSHSESSION = jsch.getSession(USER_NAME, HOST_NAME, PORT);
			SSHSESSION.setPassword(PASS_WORD);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			SSHSESSION.setConfig(sshConfig);
			SSHSESSION.connect();
			CHANNEL = SSHSESSION.openChannel("sftp");
			CHANNEL.connect();
			SFTP = (ChannelSftp) CHANNEL;
			SFTP.cd("javaimg");
		} catch (JSchException | SftpException e) {
			e.printStackTrace();
		}
		
	}

	public static void uploadImage(String imgName,InputStream instream) throws IOException, SftpException {
		OutputStream outstream = SFTP.put(imgName);
		 byte b[] = new byte[1024];
		    int n;
		    while ((n = instream.read(b)) != -1) {
		        outstream.write(b, 0, n);
		    }
		    outstream.flush();
		    outstream.close();
		    instream.close();
//		try {
//			
//		} finally {
//			closeChannel(SFTP);
//			closeChannel(CHANNEL);
//			closeSession(SSHSESSION);
//		}
	}

//	private static void closeChannel(Channel channel) {
//		if (channel != null) {
//			if (channel.isConnected()) {
//				channel.disconnect();
//			}
//		}
//	}
//
//	private static void closeSession(Session session) {
//		if (session != null) {
//			if (session.isConnected()) {
//				session.disconnect();
//			}
//		}
//	}

}
