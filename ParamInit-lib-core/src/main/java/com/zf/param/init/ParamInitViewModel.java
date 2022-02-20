package com.zf.param.init;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(value = {ElementType.FIELD})
public @interface ParamInitViewModel {
    //方法名
    String value() default "";

    //键名
    String key() default "";
}
