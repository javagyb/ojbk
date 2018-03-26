package me.javagyb.data.supplier.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by javagyb on 2018/1/29.
 */
public class Supplier {

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
