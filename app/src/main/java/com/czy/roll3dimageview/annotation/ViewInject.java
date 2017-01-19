package com.czy.roll3dimageview.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于findView的注解
 * Created by zhangyu on 2016-07-11 11:04.
 */
@Target(ElementType.FIELD)      //表示用在字段上
@Retention(RetentionPolicy.RUNTIME)     //表示其生命周期是运行时
public @interface ViewInject {

    int value() default 0;
}
