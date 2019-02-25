package me.javagyb.data.supplier.core.handlers.impl;

import lombok.EqualsAndHashCode;
import me.javagyb.data.supplier.annotations.DS_RandomString;
import me.javagyb.data.supplier.core.handlers.AnnotationHandler;
import me.javagyb.data.supplier.core.handlers.CommonHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * Created by javagyb on 2018/1/30.
 */
@EqualsAndHashCode
public class RandomStringHandler extends AnnotationHandler<DS_RandomString,String> {

    Random random = new Random();
    @Override
    public String value(DS_RandomString annotation) {

        if(ArrayUtils.isNotEmpty(annotation.tuple()) && annotation.tuple().length!=1 ){
            int length = annotation.tuple().length;
            int i = random.nextInt(length - 1);
            return annotation.prefix()+annotation.tuple()[i]+annotation.postfix();
        }
        return annotation.prefix()+CommonHandler.randomString(annotation.length())+annotation.postfix();
    }
}
