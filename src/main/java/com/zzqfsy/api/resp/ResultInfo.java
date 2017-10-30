package com.zzqfsy.api.resp;

public class ResultInfo {
	/**
	 * 执行成功
	 * @return
	 * @author zzqfsy
	 */
	public synchronized static ResponseBodyInfo success() {
        return success(null, null);
    }
	
	/**
	 * 执行成功
	 * @return
	 * @author zzqfsy
	 */
	public synchronized static ResponseBodyInfo success(Object obj) {
        return success(obj, null);
    }
	/**
	 * 转化执行成功, 并把成功的结果以JSON对象传给前端
	 * @param obj				转化的对象
	 * @param filterPropNames	需要过滤的字段
	 * @author zzqfsy
	 */
    public synchronized static ResponseBodyInfo success(Object obj, String[] filterPropNames) {
        return success(obj, true, filterPropNames);
    }
    
    public synchronized static ResponseBodyInfo success(Object obj, boolean isRefDetect, String[] filterPropNames) {
        return new ResponseBodyInfo<>(0, "", obj);
    }
    
    /**
     * 返回错误对象
     * @param code		错误代码
     * @param message	错误信息
     * @author zzqfsy
     */
    public synchronized static ResponseBodyInfo error(int code, String message) {
        return error(code, message, null);
    }
    
    public synchronized static ResponseBodyInfo error(int code, String message, Object obj) {
    	return new ResponseBodyInfo<>(code, message, obj);
    }
}