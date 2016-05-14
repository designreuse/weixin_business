package com.cht.emm.util.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtil {  
	  
    public static AnnotationUtil anno = null;  
  
    public static AnnotationUtil getInstance() {  
        if (anno == null) {  
            anno = new AnnotationUtil();  
        }  
        return anno;  
    }  
  
    /** 
     * 读取注解值 
     *  
     * @param annotationClasss 处理Annotation类名称 
     * @param annotationField 处理Annotation类属性名称 
     * @param className 处理Annotation的使用类名称 
     * @return 
     * @throws Exception 
     */  
    @SuppressWarnings("all")  
    public String loadVlaue(Class annotationClasss,  
            String annotationField, String className) throws Exception {  
  
//        System.out.println("处理Annotation类名称  === "+annotationClasss.getName());  
//        System.out.println("处理Annotation类属性名称  === "+annotationField);  
//        System.out.println("处理Annotation的调用类名称  === "+className);  
        String value=null;
        Method[] methods = Class.forName(className).getDeclaredMethods();  
        for (Method method : methods) {  
            if (method.isAnnotationPresent(annotationClasss)) {  
                Annotation p = method.getAnnotation(annotationClasss);  
                Method m = p.getClass()  
                        .getDeclaredMethod(annotationField, null);  
                String[] values = (String[]) m.invoke(p, null);  
                
                value=values[0];
            }  
        }  
        return value;  
    }  
  
    public static void main(String[] args) throws Exception {  
  
//        AnnotationUtil.getInstance().loadVlaue(Privilege.class, "value",  
//                this.class.getName());  
    }  
  
}  