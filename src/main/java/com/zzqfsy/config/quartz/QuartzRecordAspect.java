package com.zzqfsy.config.quartz;

import com.zzqfsy.service.core.TaskRecordService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by john on 17-1-3.
 */
@Component
@Order(-1)
@Aspect
public class QuartzRecordAspect {
    protected static final Logger logger = LoggerFactory.getLogger(QuartzRecordAspect.class);

    @Autowired
    private TaskRecordService taskRecordService;

    @Pointcut("execution(* com.zzqfsy.service.task..*.*(..))")
    public void record(){}

    @Around("record()")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        Object result = jp.proceed();

        Class<?> target = jp.getTarget().getClass();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        resolveTaskLog(target, signature.getMethod(), jp.getArgs(), result);
        return result;
    }

    /*
     * 提取目标对象方法注解和类型注解中的数据源标识
     * @param clazz
     * @param method
     */
    private void resolveTaskLog(Class<?> clazz, Method method, Object[] args, Object result) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(QuartzRecordType.class)) {
                QuartzRecordType source = clazz.getAnnotation(QuartzRecordType.class);
                taskRecordService.createRecord(source, clazz, method, args, result);
                return;
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(QuartzRecordType.class)) {
                QuartzRecordType source = m.getAnnotation(QuartzRecordType.class);
                taskRecordService.createRecord(source, clazz, method, args, result);
            }
        } catch (Exception e) {
            logger.error("resolveTaskLog exception" +
                         "(class: " + clazz.getName() +
                         ",method: " + method.getName() +
                         ",args:" + args.toString() + ")",
                         e);
            System.out.println(clazz + ":" + e.getMessage());
        }
    }
}
