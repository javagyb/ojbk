package me.javagyb.data.supplier.annotations.more;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by javagyb on 2018/1/29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DS_Name {


    Type type() default  Type.english;

    enum Type{
        china,
        english
    }

}
