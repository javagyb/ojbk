package me.javagyb.data.supplier.core.handlers;


import me.javagyb.data.supplier.commons.ClassUtils;
import me.javagyb.data.supplier.core.handlers.impl.*;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by javagyb on 2018/1/29.
 */
public class Handlers {

    public static Set<Handler> handlers = new HashSet<>();

    static{
        Set<Class<?>> classesByPackageName = ClassUtils.getClassesByPackageName("me.javagyb.data.supplier.core.handlers.impl");
        for(Class<?> clazz: classesByPackageName){
            if(!AnnotationHandler.class.isAssignableFrom(clazz))
                continue;
            try {
                handlers.add((Handler) clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    public static Map<Class<?>,CommonHandler> commonHandlers = new HashMap<>();
     public static Handler getCommonsHandler(Class<?> type){
         CommonHandler commonHandler = commonHandlers.get(type);
         if(commonHandler==null){
             commonHandler = new CommonHandler(type);
             commonHandlers.put(type,commonHandler);
         }
         return commonHandler;
     }


    public static Handler getHandler(Annotation annotation){
       for(Handler handler : handlers){
           if(handler.accept(annotation)){
               return handler;
           }
       }
       return null;
    }

    public static Set<Class<? extends Annotation>> listSupported(){
        Set<Class<? extends  Annotation>> result = new HashSet<>();
        handlers.forEach(handler -> result.add(handler.getSupported()));
        return result;
    }

}
