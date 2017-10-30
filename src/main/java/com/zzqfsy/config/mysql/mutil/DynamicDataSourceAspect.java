package com.zzqfsy.config.mysql.mutil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by john on 16-6-7.
 */
@Component
@Order(-1)
@Aspect
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("execution(* com.zzqfsy.service.core.repository.*.*(..))")
    public void doBefore(JoinPoint jp) throws Throwable {
        Class<?> target = jp.getTarget().getClass();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        if (target.getInterfaces() != null){
            for (Class<?> clazz : target.getInterfaces()) {
                resolveDataSource(clazz, signature.getMethod());
            }
        }else
            resolveDataSource(target, signature.getMethod());
    }

    /*
     * 提取目标对象方法注解和类型注解中的数据源标识
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(TargetDataSource.class)) {
                TargetDataSource source = clazz.getAnnotation(TargetDataSource.class);
                DynamicDataSourceContextHolder.setDataSource(source.name());
                return;
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(TargetDataSource.class)) {
                TargetDataSource source = m.getAnnotation(TargetDataSource.class);
                DynamicDataSourceContextHolder.setDataSource(source.name());
                return;
            }
            //默认主
            DynamicDataSourceContextHolder.setDataSource("master");

        } catch (Exception e) {
            System.out.println(clazz + ":" + e.getMessage());
        }
    }
}
