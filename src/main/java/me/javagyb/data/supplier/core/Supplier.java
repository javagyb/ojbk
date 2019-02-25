package me.javagyb.data.supplier.core;

import java.util.*;

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


    public static <T> List<T> list(Class<T> clazz){
      return listLimitSize(clazz,20);
    }

    public static <T> List<T> list(Class<T> clazz,int size){
        List<T> objects = new ArrayList<>();
        for(int i = 0; i<size;i++){
            objects.add(Bulider.bulid(clazz));
        }
        return objects;
    }

}
