package me.javagyb.data.supplier.core.handlers;

import java.lang.annotation.Annotation;

/**
 * Created by javagyb on 2018/1/29.
 */
public interface Handler<K extends  Annotation,T> {

    T value(K annotation);

   default  boolean accept(K annotation) {
        return annotation.annotationType().isAssignableFrom(getSupported());
    };

     Class<K> getSupported();
}
