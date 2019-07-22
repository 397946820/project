package com.it.ocs.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseResult<T extends Serializable> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5754568481754923680L;

	private List<T> rows = new ArrayList<T>();
	private int total = 0;
	private List footer = new ArrayList<>();
	private String source;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getFooter() {
		return footer;
	}

	public void setFooter(List footer) {
		this.footer = footer;
	}

}
