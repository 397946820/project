package com.it.ocs.common.enums;

/**
 * 退货申请单的状态
 * @author wangqiang
 */
public enum OrderStatusEnum {  
	processing("processing", 1), complete("complete", 2),closed("closed", 3);  //分别代表申请处理中，完成，关闭
    private String name;  
    private int index;  
    private OrderStatusEnum(String name, int index) {  
        this.name = name;  
        this.index = index;  
    }  
    public static String getName(int index) {  
        for (OrderStatusEnum c : OrderStatusEnum.values()) {  
            if (c.getIndex() == index) {  
                return c.name;  
            }  
        }  
        return null;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public int getIndex() {  
        return index;  
    }  
    public void setIndex(int index) {  
        this.index = index;  
    }  
	}  