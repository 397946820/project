package com.it.ocs.fourPX.model;

import java.util.Date;

import com.it.ocs.excel.annotation.ExcelLink;

public abstract class BaseModel {

	@ClientUneditable
	@ExcelLink(title = "ID", index = 1)
	private Long id;

	@ClientUneditable
	@ExcelLink(title = "创建时间", index = 98)
	private Date createdat;
	
	@ClientUneditable
	@ExcelLink(title = "最后更新时间", index = 99)
	private Date updatedat;

	public BaseModel() {
		
	}
	
	public BaseModel(Long id, Date createdat, Date updatedat) {
		this.id = id;
		this.createdat = createdat;
		this.updatedat = updatedat;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedat() {
		return createdat;
	}
	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}
	public Date getUpdatedat() {
		return updatedat;
	}
	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}
	
	public void init() {
		this.createdat = new Date();
		this.updatedat = this.createdat;
	}
	
	public void refresh() {
		this.updatedat = new Date();
	}
	
}
