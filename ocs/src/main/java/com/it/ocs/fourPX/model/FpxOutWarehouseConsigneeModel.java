package com.it.ocs.fourPX.model;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.it.ocs.common.util.StringUtil;

public class FpxOutWarehouseConsigneeModel {

	@ClientUneditable
	private Long id;
	@ClientUneditable
	private Long parentId;
	private String fullName;
	private String countryCode;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	private String email;
	private String phone;
	private String company;
	@ClientUneditable
	private String doorplate;
	@ClientUneditable
	private String cardId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDoorplate() {
		return doorplate;
	}
	public void setDoorplate(String doorplate) {
		this.doorplate = doorplate;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/**
	 * 兼容4px数据格式要求
	 * <p>
	 * 4PX 规则：</br>
	 * 1、遇到电话号码是空的，用"000000000"代替。</br>
	 * 2、街道栏里面，遇到"PO"或"BOX"或"PO"或"P"字母开头的都把街道地址跟公司地址对调。</br>
	 * 	    对调后，如果街道地址是空的，用*星号代替</br>
	 * 3、如果客人填写的地址只填写公司栏的地址，街道地址没有填，也用*星号代入街道地址</br>
	 * 4、如果客人填写两个一样的地址，不能都抓取过来换行。要保留一个，另一段重复的内容，放到公司栏。</br>
	 * 5、如果收件人名字，字符>30个字符的，取前30个字符作为收件人名字。</br>
	 * </p>
	 */
	public void compatibleWith4px(Map<String, Object> unshipped) {
		String one = StringUtil.cancelNewline((String) unshipped.get("ADDRESSFIELDONE"));
		String two = StringUtil.cancelNewline((String) unshipped.get("ADDRESSFIELDTWO"));
		String three= StringUtil.cancelNewline((String) unshipped.get("ADDRESSFIELDTHREE"));
		
		Field[] fields = this.getClass().getDeclaredFields();
		if(null != fields && fields.length > 0) {
			for (Field field : fields) {
				try {
					Object value = field.get(this);
					if(null != value && value instanceof String) {
						field.set(this, StringUtil.cancelNewline((String) value)); //去掉换行符
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// Ignore ...
				}
			}
		}
		
		if(StringUtils.isBlank(this.getPhone())) {
			this.setPhone("0000000000");
		}
		
		if(StringUtils.isNotBlank(this.getFullName()) && this.getFullName().length() > 30) {
			this.setFullName(this.getFullName().substring(0, 30));
		}
		
		//街道优先取地址一，如果地址一是空值则取地址二
		this.setStreet(StringUtils.isNotBlank(two) ? two : one);  
		//如果地址二有值，公司取地址一，否则取空值（业务逻辑上，公司看街道的取值：街道取地址二，公司取地址一；街道取地址一，公司取空值）
		this.setCompany(StringUtils.isNotBlank(two) ? one : null);
		//门牌号取地址三的值
		this.setDoorplate(three);
		
		//街道栏里面，遇到BOX或P字母开头的都把街道地址跟公司对调
		if(StringUtils.isNotBlank(this.getStreet()) && (this.getStreet().startsWith("P") || this.getStreet().startsWith("BOX"))) {
			String temp = this.getStreet();
			this.setStreet(this.getCompany());
			this.setCompany(temp);
		}
		
		if(StringUtils.isBlank(getStreet())) {
			this.setStreet("*");
		}
		
	}
}
