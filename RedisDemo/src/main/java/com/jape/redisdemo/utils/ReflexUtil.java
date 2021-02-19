package com.jape.redisdemo.utils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具
 */
public class ReflexUtil {


    /**
     * 对象转HashMap
     *
     * @param obj
     * @return 对象属性的HashMap
     */
    public static Map<String, Object> obj2Map(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(obj);
                map.put(fieldName, fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * Map转对象
     *
     * @param map
     * @param clazz
     * @return
     */
    public static Object map2Obj(Map<Object, Object> map, Class<?> clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Object filedValue = parseField(field, map);
                field.set(obj, filedValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    //解析字段，处理特殊类型
    private static Object parseField(Field field, Map<Object, Object> map) {
        field.setAccessible(true);
        String fieldName = field.getName();
        String simpleName = field.getType().getSimpleName();
        Object filedValue = map.get(fieldName);
        switch (simpleName) {
            case "LocalDateTime":
                filedValue = LocalDateTime.parse((String) filedValue);
                break;
        }
        return filedValue;
    }

}
