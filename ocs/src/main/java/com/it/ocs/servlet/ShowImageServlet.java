package com.it.ocs.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowImageServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6381568990801208019L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String url = request.getParameter("url");
		File imageFile = new File(url);
		FileInputStream fin = new FileInputStream(imageFile);
		BufferedInputStream bis = new BufferedInputStream(fin);
		ServletOutputStream sos = response.getOutputStream();
		byte[] b = new byte[1024*1024];
		int len = 0;
		while ((len = bis.read(b)) != -1) {
			sos.write(b,0,len);
		}
		bis.close();
		sos.close();
		sos.flush();
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.doGet(request, response);
	}
}
