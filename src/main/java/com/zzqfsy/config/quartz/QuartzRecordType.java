package com.zzqfsy.config.quartz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by john on 17-1-3.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QuartzRecordType {
    String type() default QuartzRecordType.job;
    String action() default "";

    public static String job = "job";
    public static String execute = "executeHttpRPC";
}
