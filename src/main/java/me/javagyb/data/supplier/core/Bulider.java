package me.javagyb.data.supplier.core;

import jdk.nashorn.internal.ir.annotations.Ignore;
import me.javagyb.data.supplier.annotations.DS_Array;
import me.javagyb.data.supplier.annotations.DS_Collection;
import me.javagyb.data.supplier.annotations.DS_Ingore;
import me.javagyb.data.supplier.annotations.DS_Override;
import me.javagyb.data.supplier.commons.ClassUtils;
import me.javagyb.data.supplier.core.handlers.Handlers;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import sun.applet.Main;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by javagyb on 2018/1/29.
 */
public class Bulider {

    static ThreadLocal<Boolean> override= new ThreadLocal<>();

    protected static <T> T bulid(Class<T> clazz){
        return bulid(clazz,null);
    }


    private static <T> T bulid(Class<T> clazz, Set<Class<?>> haveDone) {
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

        try {
            Object newInstance = clazz.newInstance();
            Field[] declaredFields = clazz.getDeclaredFields();
            for(Field field: declaredFields){
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
                if(me.javagyb.data.supplier.commons.ClassUtils.isPrimitiveOrWrapper(field.getType()) || field.getType().isAssignableFrom(String.class)){
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
                    if(ClassUtils.getGeneric(field) != null){
                        componentType = ClassUtils.getGeneric(field);

                    }else {
                        DS_Collection collectionAnnotation = ClassUtils.find(field, DS_Collection.class);
                        if(collectionAnnotation != null){
                            componentType = collectionAnnotation.type();
                            size = collectionAnnotation.size();
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
