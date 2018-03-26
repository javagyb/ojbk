package me.javagyb.data.supplier.core.handlers;

import lombok.EqualsAndHashCode;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by javagyb on 2018/1/29.
 */

@EqualsAndHashCode
public abstract class AnnotationHandler<K extends Annotation,T> implements  Handler<K,T> {


    @Override
    public Class<K> getSupported() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }
}
