package com.it.ocs.fourPX.vo;

import com.it.ocs.eda.vo.EDAStockVO;

public class FPXStockVO extends EDAStockVO {

	private static final long serialVersionUID = 4286774976203034496L;

	private String busarea;
	
	public String getBusarea() {
		return busarea;
	}
	public void setBusarea(String busarea) {
		this.busarea = busarea;
	}
}
