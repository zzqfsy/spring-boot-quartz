package com.zzqfsy.config.mysql.mutil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by john on 16-6-6.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {
    String name() default TargetDataSource.master;

    public static String master = "master";

    public static String quartzDataSource = "quartzDataSource";
}
