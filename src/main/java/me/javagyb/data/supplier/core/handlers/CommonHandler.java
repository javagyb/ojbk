package me.javagyb.data.supplier.core.handlers;

import lombok.EqualsAndHashCode;
import me.javagyb.data.supplier.commons.ClassUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.annotation.Annotation;
import java.util.Random;

/**
 * Created by javagyb on 2018/1/29.
 */
@EqualsAndHashCode
public class CommonHandler implements  Handler<Annotation,Object> {

    private Class<?> type;
    Random rand = new Random();
    public CommonHandler(Class<?> type){
        this.type = type;
    }


    @Override
    public Object value(Annotation annotation) {
        Class<?> wrapper = ClassUtils.getWrapper(type);
        if(wrapper.isAssignableFrom(Integer.class))
            return  Integer.valueOf(rand.nextInt(100));
        if(wrapper.isAssignableFrom(Float.class))
            return  Float.valueOf(rand.nextFloat());
        if(wrapper.isAssignableFrom(Double.class))
            return  Double.valueOf(rand.nextDouble());
        if(wrapper.isAssignableFrom(Long.class))
            return  Long.valueOf(rand.nextLong());
        if(wrapper.isAssignableFrom(Boolean.class))
            return  Boolean.valueOf(rand.nextBoolean());
        if(wrapper.isAssignableFrom(String.class))
            return  RandomStringUtils.random(8);
        return null;
    }



    @Override
    public boolean accept(Annotation annotation) {
        return false;
    }

    @Override
    public Class<Annotation> getSupported() {
        return null;
    }
}
