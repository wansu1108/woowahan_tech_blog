package com.wskim.woowahan.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SuperClassReflectionUtils {

    private SuperClassReflectionUtils(){
    }

    public static List<Field> getAllFields(Class<?> clazz){
        List<Field> fields = new ArrayList<>();
        for(Class<?> clazzInClasses : getAllClassesIncludingSuperClasses(clazz, true)){
            fields.addAll(Arrays.asList(clazzInClasses.getDeclaredFields()));
        }
        return fields;
    }

    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException{
        for(Class<?> clazzInClass : getAllClassesIncludingSuperClasses(clazz, true)){
            for(Field field : clazzInClass.getDeclaredFields()){
                if(field.getName().equals(fieldName)){
                    return field;
                }
            }
        }
        throw new NoSuchFieldException();
    }

    private static List<Class<?>> getAllClassesIncludingSuperClasses(Class<?> clazz, boolean fromSuper) {
		List<Class<?>> classes = new ArrayList<>();

        // 부모 클래스 까지
		while (clazz != null) {
			classes.add(clazz);
			clazz = clazz.getSuperclass();
		}
		if (fromSuper) {
            // classes.add(clazz);
			Collections.reverse(classes);
		}
		return classes;
	}

}
