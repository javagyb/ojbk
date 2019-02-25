package me.javagyb.data.supplier.annotations;

import java.lang.annotation.*;

/**
 * Created by javagyb on 2018/1/29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DS_Collection {

    Class<?> type();

}
