package com.it.ocs.email.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.aspectj.weaver.ArrayAnnotationValue;

import com.it.ocs.email.util.MailUtils;

public class Mail {
	/**
	 * 收件人
	 */
	private EmailAddress[] to;
	/**
	 * 抄送
	 */
	private EmailAddress[] cc;
	/**
	 * 密送
	 */
	private EmailAddress[] bcc;
	
	/**
	 * 主题
	 */
	private String subject;
	
	/**
	 * 内容
	 */
	private String content;
	
	public InternetAddress[] getAllTo(){
		return getInternetAddress(this.to);
	}
	public InternetAddress[] getAllCc(){
		return getInternetAddress(this.cc);
	}
	public InternetAddress[] getAllBcc(){
		return getInternetAddress(this.bcc);
	}
	
	/**
	 * 发送邮件
	 * @throws Exception
	 */
	public void send() throws Exception{
		MailUtils.sendMail(this);
	}
	
	private static InternetAddress[] getInternetAddress(EmailAddress[] emailAddress){
		if(null != emailAddress && emailAddress.length > 0){
			List<InternetAddress> address = new ArrayList<>();
			for(EmailAddress ea :emailAddress){
				InternetAddress ia;
				try {
					ia = new InternetAddress(ea.getEmail(), ea.getViewName(), "UTF-8");
					address.add(ia);
				} catch (UnsupportedEncodingException e) {
					
				}
			}
			return address.toArray(new InternetAddress[]{});
		}
		
		return new InternetAddress[]{};
	}

	public EmailAddress[] getTo() {
		return to;
	}

	public void setTo(EmailAddress[] to) {
		this.to = to;
	}

	public EmailAddress[] getCc() {
		return cc;
	}

	public void setCc(EmailAddress[] cc) {
		this.cc = cc;
	}

	public EmailAddress[] getBcc() {
		return bcc;
	}

	public void setBcc(EmailAddress[] bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public static void main(String[] args) throws Exception {
		Mail mail = new Mail();
		EmailAddress[] to = {new EmailAddress("chenyong@ledbrighter.com", "chenyong")};
		mail.setTo(to);
		mail.setSubject("邮件提醒测试");
		mail.setContent("<img src=\"https://i.ebayimg.com/00/s/MTAwMFgxMDAw/z/NpMAAOSwOuRZuJwr/$_12.JPG?set_id=880000500\" />");
		mail.send();
	}
}
