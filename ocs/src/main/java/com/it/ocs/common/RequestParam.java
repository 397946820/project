package com.it.ocs.common;

import java.io.Serializable;
import java.util.Map;

public class RequestParam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8609731235419294884L;
	/**
	 * 当前页数
	 */
	private int page = 1;
	/**
	 * 每页行数
	 */
	private int rows = 10;
	/**
	 * 起始行
	 */
	private int startRow;
	/**
	 * 结束行
	 */
	private int endRow;
	/**
	 * 排序字段
	 */
	private String sort;
	/**
	 * 排序方式
	 */
	private String order;

	Map<String, Object> param;

	/**
	 * 条件
	 */

	public int getStartRow() {
		// return (page - 1) * rows + 1; oracle
		return startRow;//mysql
	}

	public int getEndRow() {
		// return page * rows;oracle
		return rows;//mysql
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
}
