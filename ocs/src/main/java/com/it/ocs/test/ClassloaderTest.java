package com.it.ocs.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.aspectj.weaver.bcel.ExtensibleURLClassLoader;

import com.it.ocs.sys.model.UserModel;

public class ClassloaderTest {
	public static void main(String[] args)
			throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		URL url = null;
		url = new URL("file:///D:\\work\\project\\ocs\\target\\classes");
		URL[] urls = new URL[1];
		urls[0] = url;
		URLClassLoader urlClsLdr1 = new URLClassLoader(urls);
		UserModel compare1 = (UserModel) urlClsLdr1.loadClass("com.it.ocs.sys.model.UserModel").newInstance();

		UserModel compare3 = (UserModel) urlClsLdr1.loadClass("com.it.ocs.sys.model.UserModel").newInstance();

		URLClassLoader urlClsLdr2 = new URLClassLoader(urls);
		UserModel compare2 = null;
		compare2 = (UserModel) urlClsLdr2.loadClass("com.cxz.classloader.Sample").newInstance();

		System.out.println(compare1.getClass().equals(compare2.getClass()));
		System.out.println(urlClsLdr1.getSystemClassLoader());
		
	}
}
