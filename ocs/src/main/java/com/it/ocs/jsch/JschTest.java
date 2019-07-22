package com.it.ocs.jsch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class JschTest {
	public static void main(String[] args) throws JSchException, SftpException, IOException {
		//声明JSCH对象
        JSch jSch = new JSch();
        //获取一个Linux会话
        Session session = jSch.getSession("root", "47.90.209.66", 22);
        //设置登录密码
        session.setPassword("admins@123");
        //关闭key的检验
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        //连接Linux
        session.connect();
        //通过sftp的方式连接
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        //上传文件
        File file = new File("d:\\1.txt");
        InputStream inputStream = new FileInputStream(file);
        channel.put(inputStream, "/root/file/2.txt");
        //下载文件
        OutputStream out = new FileOutputStream("d:\\4.txt");
        channel.get("/root/file/1.txt", out);
        //关闭流
        inputStream.close();
        out.close();
	}
}
