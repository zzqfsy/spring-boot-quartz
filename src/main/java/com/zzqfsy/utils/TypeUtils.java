package com.zzqfsy.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zzqana on 8/24/2016.
 */
public class TypeUtils {
    public static boolean isInteger(String str){
        try{
            Integer.valueOf(str);
        }catch(NumberFormatException ex){
            return  false;
        }
        return true;
    }
    public static boolean isLong(String str){
        try{
            Long.valueOf(str);
        }catch(NumberFormatException ex){
            return  false;
        }
        return true;
    }
    public static boolean isDouble(String str){
        try{
            Double.valueOf(str);
        }catch(NumberFormatException ex){
            return  false;
        }
        return true;
    }
    public static boolean isJson(String str){
        try{
            JSONObject.parseObject(str);
        }catch(Exception ex){
            return false;
        }
        return true;
    }
}
