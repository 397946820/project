package com.it.ocs.fourPX.ex;

import com.it.ocs.common.ex.BussinessException;

/**
 * 用于标记异常单的异常
 * @author zhouyancheng
 *
 */
public class AbnormalOrderException extends BussinessException {

	private static final long serialVersionUID = -5220966491916437864L;

	public AbnormalOrderException() {
		super();
	}
	
	public AbnormalOrderException(String message) {
		super(message);
	}
	
	public AbnormalOrderException(Throwable e) {
		super(e);
	}
	
	public AbnormalOrderException(String message, Throwable e) {
		super(message, e);
	}

}
