package com.zzqfsy.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 17-1-18.
 */
public class ListUtils {

    /**
     * 分页数组
     * @param list
     * @param start
     * @param length
     * @param <T>
     * @return
     */
    public static <T> List<T> pageList(final List<T> list, int start, int length){
        List<T> result = new ArrayList<T>();
        result.addAll(list);

        int size = list.size();
        if (start < size) {
            if (start + length < size) {
                result = list.subList(start, start + length);
            } else {
                result = list.subList(start, size);
            }
        } else {
            result = new ArrayList<>();
        }
        return result;
    }

    public static Integer getStartByPageNo(Integer pageNo, Integer pageSize){
        if (pageNo.equals(0) || pageSize.equals(0)) return null;

        if (pageNo.equals(1)) return 0;

        return (Integer.valueOf(pageNo) - 1) * pageSize;
    }

}
