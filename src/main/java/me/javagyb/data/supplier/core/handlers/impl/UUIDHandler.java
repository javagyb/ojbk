package me.javagyb.data.supplier.core.handlers.impl;

import lombok.EqualsAndHashCode;
import me.javagyb.data.supplier.annotations.DS_UUID;
import me.javagyb.data.supplier.core.handlers.AnnotationHandler;

import java.lang.annotation.Annotation;
import java.util.UUID;

/**
 * Created by javagyb on 2018/1/30.
 */
@EqualsAndHashCode
public class UUIDHandler extends AnnotationHandler<DS_UUID,String> {

    @Override
    public String value(DS_UUID annotation) {
        if(annotation.noLine())
            return  java.util.UUID.randomUUID().toString().replace("-","");
        return  java.util.UUID.randomUUID().toString();
    }
}
