package me.javagyb.data.supplier.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by javagyb on 2018/1/30.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DS_DateRange {

    String start();

    String end();

    String formate() default  "yyyy-MM-dd HH:mm:ss";

    Step step() default Step.day;


    public static enum Step{
        second,minute,hour,day,year,month,week
    }

}
