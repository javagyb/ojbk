package me.javagyb.data.supplier.core;

import java.util.*;
import me.javagyb.data.supplier.commons.ClassUtils;

/**
 * Created by javagyb on 2018/1/29.
 */
public class Supplier {

    protected static ThreadLocal<Set<String>>  exculdes = new ThreadLocal<Set<String>>();


    public static void  addExculde(String exculde){
        Set<String> strings = exculdes.get();
        if(strings==null){
            strings = new HashSet<>();
        }
        strings.add(exculde);
        exculdes.set(strings);
    }

    public static <T> T single(String className){

        if (className.startsWith("java.util.List")) {
            String elementName = className.substring("java.util.List<".length(),className.length()-1);
            try {
                Class<?> elementClass = ClassUtils.forName(elementName, ClassUtils.getDefaultClassLoader());
                Object single = single(elementClass);
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(single);
                return (T) objects;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (className.startsWith("java.util.Set")) {
            String elementName = className.substring("java.util.Set<".length(),className.length()-1);
            try {
                Class<?> elementClass = ClassUtils.forName(elementName, ClassUtils.getDefaultClassLoader());
                Object single = single(elementClass);
                HashSet<Object> objects = new HashSet<>();
                objects.add(single);
                return (T) objects;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            return (T) Bulider.bulid(ClassUtils.forName(className, ClassUtils.getDefaultClassLoader()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T single(Class<T> clazz){
        return Bulider.bulid(clazz);
    }

    public static <T> List<T> listLimitSize(Class<T> clazz,int size){
        Random random = new Random();
        List<T> objects = new ArrayList<>();
        for(int i = 0; i<random.nextInt(size);i++){
            objects.add(Bulider.bulid(clazz));
        }
        return objects;
    }

    public static <T> List<T> listLimitSize(String className,int size){
        Random random = new Random();
        List<T> objects = new ArrayList<>();
        for(int i = 0; i<random.nextInt(size);i++){
            objects.add(single(className));
        }
        return objects;
    }



    public static <T> List<T> list(Class<T> clazz){
      return listLimitSize(clazz,20);
    }

    public static <T> List<T> list(String clazzName){
        return listLimitSize(clazzName,20);
    }

    public static <T> List<T> list(Class<T> clazz,int size){
        List<T> objects = new ArrayList<>();
        for(int i = 0; i<size;i++){
            objects.add(Bulider.bulid(clazz));
        }
        return objects;
    }

}
