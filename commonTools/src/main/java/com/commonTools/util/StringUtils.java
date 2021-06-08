package com.commonTools.util;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 * @since ZQ002
 */
public class StringUtils {

    public static boolean isBlank(String string){
        return string == null || string.trim().length()==0;
    }

    public static boolean isEmpty(String string){
        return string == null || string.length()==0;
    }

    public static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }
}
