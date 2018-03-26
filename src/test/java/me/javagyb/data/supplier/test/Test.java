package me.javagyb.data.supplier.test;


import me.javagyb.data.supplier.core.Bulider;
import me.javagyb.data.supplier.core.Supplier;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by javagyb on 2018/1/29.
 */
public class Test {
    public static void main(String[] args) throws InvocationTargetException {
        User bulid = Supplier.single(User.class);
        System.out.println(bulid);
    }


}
