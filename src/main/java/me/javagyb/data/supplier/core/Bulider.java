package me.javagyb.data.supplier.core;

import java.lang.reflect.ParameterizedType;
import jdk.nashorn.internal.ir.annotations.Ignore;
import me.javagyb.data.supplier.annotations.*;
import me.javagyb.data.supplier.commons.ClassUtils;
import me.javagyb.data.supplier.core.handlers.Handlers;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import sun.applet.Main;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by javagyb on 2018/1/29.
 */
public class Bulider {

    static ThreadLocal<Boolean> override= new ThreadLocal<>();

    protected static <T> T bulid(Class<T> clazz){
        if(me.javagyb.data.supplier.commons.ClassUtils.isPrimitiveOrWrapper(clazz) || clazz.isAssignableFrom(String.class) || clazz.isAssignableFrom(BigDecimal.class)){
            return (T) Handlers.getCommonsHandler(clazz).value(null);
        }
        if(clazz.isArray()){
            Class<?> componentType = clazz.getComponentType();
            Object[] values =  new Object[1];
            values[0] = bulid(componentType);
            return (T) values;
        }
        if(me.javagyb.data.supplier.commons.ClassUtils.isCollection(clazz)){
            if(ArrayUtils.isEmpty(clazz.getGenericInterfaces())){
                if(clazz.isAssignableFrom(Set.class)){
                    HashSet<Object> objects = new HashSet<>();
                    return (T) objects;
                }else {
                    ArrayList<Object> objects = new ArrayList<>();
                    return (T) objects;
                }
            }
            String typeName = ((ParameterizedType) clazz.getGenericInterfaces()[0])
                .getActualTypeArguments()[0].getTypeName();
            try {
                Class<?> aClass = ClassUtils.forName(typeName, ClassUtils.getDefaultClassLoader());
                Object bulid = bulid(aClass);
                if(clazz.isAssignableFrom(Set.class)){
                    HashSet<Object> objects = new HashSet<>();
                    objects.add(bulid);
                    return (T) objects;
                }else {
                    ArrayList<Object> objects = new ArrayList<>();
                    objects.add(bulid);
                    return (T) objects;
                }
            } catch (ClassNotFoundException e) {
                return null;
            }

        }
        return bulid(clazz,null);
    }


    private static <T> T bulid(Class<T> clazz, Set<Class<?>> haveDone)  {
        if(override.get()==null){
            DS_Override ds_override = ClassUtils.find(clazz, DS_Override.class);
            override.set(false);
            if(ds_override!=null){
                override.set(ds_override.value());
            }
        }
        if(CollectionUtils.isEmpty(haveDone)){
            haveDone = new HashSet<>();
            haveDone.add(clazz);
        }
        if(clazz.isEnum()){
            try {
                Method method = clazz.getMethod("values");
                Object[] values = (Object[]) method.invoke(null,null);
                Random random = new Random();
                int i = random.nextInt(values.length);
                return (T) values[i];

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        try {
            Object newInstance = clazz.newInstance();
            Field[] declaredFields = clazz.getDeclaredFields();
            for(Field field: declaredFields){
                // if ignore
                if(Supplier.exculdes.get()!=null && Supplier.exculdes.get().contains(field.getName())){
                    continue;
                }

                if(!override.get()){
                    try {
                        if(BeanUtils.getProperty(newInstance,field.getName())!=null){
                            continue;
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                }

                // if ignore
                if(me.javagyb.data.supplier.commons.ClassUtils.isAnnotation(field, DS_Ingore.class)){
                    continue;
                }

                if(haveDone.contains(field.getType())){
                    continue;
                }
                // simple type
                Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
                if(me.javagyb.data.supplier.commons.ClassUtils.isPrimitiveOrWrapper(field.getType()) || field.getType().isAssignableFrom(String.class) || field.getType().isAssignableFrom(BigDecimal.class)){
                    // no annotation

                    Annotation annotation = ClassUtils.find(declaredAnnotations, Handlers.listSupported());
                    if(annotation==null){
                        BeanUtils.setProperty(newInstance,field.getName(), Handlers.getCommonsHandler(field.getType()).value(null));
                    }else{
                        BeanUtils.setProperty(newInstance,field.getName(), Handlers.getHandler(annotation).value(annotation));
                    }
                    continue;
                }

                // date type
                if(field.getType().isAssignableFrom(Date.class)){
                    Annotation annotation = ClassUtils.find(declaredAnnotations, Handlers.listSupported());
                    if(annotation==null){
                        BeanUtils.setProperty(newInstance,field.getName(), new Date());
                    }else{
                        BeanUtils.setProperty(newInstance,field.getName(), Handlers.getHandler(annotation).value(annotation));
                    }
                    continue;
                }

                // array
                if(field.getType().isArray()){
                    Class<?> componentType = field.getType().getComponentType();
                    if(haveDone.contains(componentType)){
                        continue;
                    }
                    DS_Array array = me.javagyb.data.supplier.commons.ClassUtils.find(field, DS_Array.class);
                    int size = array==null?8:array.size();
                    componentType = ClassUtils.getWrapper(componentType);
                    Object[] arrayObject = (Object[]) java.lang.reflect.Array.newInstance(componentType, size);
                    // is primitive
                    if(ClassUtils.isPrimitiveOrWrapper(componentType)|| componentType.isAssignableFrom(String.class)){
                        if(ClassUtils.exclude(field,DS_Array.class).isEmpty()){
                            for(int i =0;i<size;i++){
                                arrayObject[i]=Handlers.getCommonsHandler(componentType).value(null);

                            }
                        }else {
                            Annotation annotation = ClassUtils.find(declaredAnnotations, Handlers.listSupported());
                            if(annotation==null){
                                for(int i =0;i<size;i++){
                                    arrayObject[i]=Handlers.getCommonsHandler(componentType).value(null);
                                }
                            }else{
                                for(int i =0;i<size;i++){
                                    arrayObject[i]=Handlers.getHandler(annotation).value(annotation);
                                }
                            }
                        }
                    }else{
                        for(int i =0;i<size;i++){
                            arrayObject[i]=bulid(componentType,haveDone);
                        }
                    }
                    BeanUtils.setProperty(newInstance,field.getName(),arrayObject);
                    continue;
                }

                if(me.javagyb.data.supplier.commons.ClassUtils.isCollection(field.getType())){

                    Class<?> componentType = null;
                    int size = 8;
                    DS_Size ds_size = ClassUtils.find(field, DS_Size.class);
                    if(ds_size != null){
                        size = ds_size.value();
                    }
                    if(ClassUtils.getGeneric(field) != null){
                        componentType = ClassUtils.getGeneric(field);

                    }else {
                        DS_Collection collectionAnnotation = ClassUtils.find(field, DS_Collection.class);
                        if(collectionAnnotation != null){
                            componentType = collectionAnnotation.type();
                        }

                    }
                    if(componentType==null &&  haveDone.contains(componentType) ){
                        continue;
                    }
                    if(componentType!=null && !ClassUtils.isCollection(componentType)){
                        java.util.Collection<Object> objects ;
                        if(componentType.isAssignableFrom(Set.class)){
                            objects = new HashSet<>();
                        }else{
                            objects = new ArrayList<>();
                        }
                        if(ClassUtils.isPrimitiveOrWrapper(componentType)|| componentType.isAssignableFrom(String.class)){
                            if(ClassUtils.exclude(field,DS_Collection.class).isEmpty()){
                                for(int i =0;i<size;i++){
                                    objects.add(Handlers.getCommonsHandler(componentType).value(null));
                                }
                            }else {
                                Annotation annotation = ClassUtils.find(declaredAnnotations, Handlers.listSupported());
                                if(annotation==null){
                                    for(int i =0;i<size;i++){
                                        objects.add(Handlers.getCommonsHandler(componentType).value(null));
                                    }
                                }else{
                                    for(int i =0;i<size;i++){
                                        objects.add(Handlers.getHandler(annotation).value(annotation));
                                    }
                                }
                            }
                        }else{
                            for(int i =0;i<size;i++){
                                objects.add(bulid(componentType,haveDone));
                            }
                        }
                        BeanUtils.setProperty(newInstance,field.getName(),objects);
                        continue;
                    }
                }

                BeanUtils.setProperty(newInstance,field.getName(),bulid(field.getType(),haveDone));

            }
            return (T) newInstance;
        } catch (InstantiationException | IllegalAccessException |InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
