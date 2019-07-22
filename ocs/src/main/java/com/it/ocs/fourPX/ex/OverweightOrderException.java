package com.it.ocs.fourPX.ex;

import com.it.ocs.common.ex.BussinessException;

/**
 * 用于超重订单的异常类
 * @author zhouyancheng
 *
 */
public class OverweightOrderException extends BussinessException {

	private static final long serialVersionUID = 6101489240186225634L;

	public OverweightOrderException() {
		super();
	}
	
	public OverweightOrderException(String message) {
		super(message);
	}
	
	public OverweightOrderException(Throwable e) {
		super(e);
	}
	
	public OverweightOrderException(String message, Throwable e) {
		super(message, e);
	}

}
