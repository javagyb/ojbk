package me.javagyb.data.supplier.annotations.more;

import me.javagyb.data.supplier.annotations.DS_Regx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by javagyb on 2018/1/29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DS_Email {

    String service() default "@javagyb.com.cn";

}
