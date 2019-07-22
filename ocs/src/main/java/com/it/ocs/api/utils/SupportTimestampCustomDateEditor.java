package com.it.ocs.api.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;

public class SupportTimestampCustomDateEditor extends CustomDateEditor {

	private final DateFormat dateFormatCopy;
	
	public SupportTimestampCustomDateEditor(DateFormat dateFormat) {
		this(dateFormat, true);
	}
	
	public SupportTimestampCustomDateEditor(DateFormat dateFormat, boolean allowEmpty) {
		super(dateFormat, allowEmpty);
		this.dateFormatCopy = dateFormat;
	}

	private static final String REGEX = "^[0-9]*[1-9][0-9]*$";
	
	protected boolean isPositiveInteger(String text) {
		return StringUtils.isNotBlank(text) && Pattern.compile(REGEX).matcher(text).find();
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(this.isPositiveInteger(text)) {
			text = this.dateFormatCopy.format(new Date(Long.parseLong(text)));
		} 
		super.setAsText(text);
	}
	
	/*public static void main(String[] args) {
		SupportTimestampCustomDateEditor ins = new SupportTimestampCustomDateEditor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"), true);
		System.out.println(ins.isPositiveInteger("s123s"));
		System.out.println(ins.isPositiveInteger("-23"));
		System.out.println(ins.isPositiveInteger("0"));
		System.out.println(ins.isPositiveInteger("12-3s"));
		System.out.println(ins.isPositiveInteger("12-3"));
		System.out.println(ins.isPositiveInteger("10101010101"));
	}*/
}
