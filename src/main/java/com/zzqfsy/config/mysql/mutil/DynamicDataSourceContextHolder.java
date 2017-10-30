package com.zzqfsy.config.mysql.mutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by john on 16-6-7.
 */
public class DynamicDataSourceContextHolder {
    //线程本地环境
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();
    public static List<String> dataSourceIds = new ArrayList<>(Arrays.asList("master", "quartzDataSource"));

    //设置数据源
    public static void setDataSource(String customerType) {
        dataSources.set(customerType);
    }

    //获取数据源
    public static String getDataSource() {
        return (String) dataSources.get();
    }

    //清除数据源
    public static void clearDataSource() {
        dataSources.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dataSourceId
     * @return
     * @author SHANHY
     * @create  2016年1月24日
     */
    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }
}
