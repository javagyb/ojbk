package me.javagyb.data.supplier.test;


import java.util.ArrayList;
import me.javagyb.data.supplier.commons.ClassUtils;
import me.javagyb.data.supplier.core.Bulider;
import me.javagyb.data.supplier.core.Supplier;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by javagyb on 2018/1/29.
 */
public class Test {
    public static void main(String[] args) throws InvocationTargetException, ClassNotFoundException {


        Object single = Supplier.single(User.class);

        System.out.println(single);
    }


}
