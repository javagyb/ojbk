package me.javagyb.data.supplier.core.handlers.impl;

import lombok.EqualsAndHashCode;
import me.javagyb.data.supplier.annotations.DS_RandomInteger;
import me.javagyb.data.supplier.core.handlers.AnnotationHandler;

import java.lang.annotation.Annotation;
import java.util.Random;

/**
 * Created by javagyb on 2018/1/29.
 */
@EqualsAndHashCode
public class RandomIntegerHandler extends AnnotationHandler<DS_RandomInteger,Integer> {

    private Random random = new Random();

    @Override
    public Integer value(DS_RandomInteger annotation) {
        return  Integer.valueOf(random.nextInt(annotation.end()));
    }

}
