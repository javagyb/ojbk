package me.javagyb.data.supplier.core.handlers;

import lombok.EqualsAndHashCode;
import me.javagyb.data.supplier.commons.ClassUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
            return  randomString(8);
        if(wrapper.isAssignableFrom(BigDecimal.class))
            return  BigDecimal.valueOf(rand.nextInt(8));
        if(wrapper.isEnum()){
            try {
                Method method = wrapper.getMethod("values");
                Object[] values = (Object[]) method.invoke(null,null);
                Random random = new Random();
                int i = random.nextInt(values.length);
                return  values[i];

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    public static String randomString(int strLength) {
        Random rnd = ThreadLocalRandom.current();
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
            boolean isChar = (rnd.nextInt(2) % 2 == 0);// 输出字母还是数字
            if (isChar) { // 字符串
                int choice = rnd.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                ret.append((char) (choice + rnd.nextInt(26)));
            } else { // 数字
                ret.append(Integer.toString(rnd.nextInt(10)));
            }
        }
        return ret.toString();
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
