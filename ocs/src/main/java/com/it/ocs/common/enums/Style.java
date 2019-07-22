package com.it.ocs.common.enums;

/**
 * 字段转换方式
 */
public enum Style {
    normal,                    				//原值
    camelhump,                  			//驼峰转下划线
    uppercase,                  			//转换为大写
    lowercase,                  			//转换为小写
    camelhumpAndUppercase,      			//驼峰转下划线大写形式
    camelhumpAndLowercase,      			//驼峰转下划线小写形式
    underlineToCamelhump,					//下划线转驼峰
    underlineToCamelhumpAndUppercase,		//下划线转驼峰大写形式
    underlineToCamelhumpAndLowercase,		//下划线转驼峰小写形式
}
