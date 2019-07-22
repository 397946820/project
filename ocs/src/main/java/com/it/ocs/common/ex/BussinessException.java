package com.it.ocs.common.ex;

/**
 * 定义一个业务异常类
 * @see 
 * 有些业务错误的提示信息需要以异常形式往外抛，catch时通过异常类型进行辨别。</br>
 * 例如：因为业务原因需要中断一个事务，且需要将原因告知事务外部调用者。
 * @author zhouyancheng
 *
 */
public class BussinessException extends Exception {

	private static final long serialVersionUID = -8768959212812095371L;

	public BussinessException() {
		super();
	}
	
	public BussinessException(String message) {
		super(message);
	}
	
	public BussinessException(Throwable e) {
		super(e);
	}
	
	public BussinessException(String message, Throwable e) {
		super(message, e);
	}
	
}
