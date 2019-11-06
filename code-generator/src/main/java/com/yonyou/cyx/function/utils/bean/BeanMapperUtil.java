/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.framework
*
* @File name : BeanMapper.java
*
* @Author : LiaoYuzhi
*
* @Date : 2016年3月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年3月3日    LiaoYuzhi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.cyx.function.utils.bean;

import com.yonyou.cyx.function.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/***
 * Bean 映射转换
 *
 * @author zhangxianchao
 * @since 2018/7/22 0022
 */
public final class BeanMapperUtil extends org.apache.commons.collections.MapUtils {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(BeanMapperUtil.class);

    private static String errorMsg = "Map 转换对象失败";
    private static String errorMsg2 = "对象转换MAP失败";


    /**
     * 将对象dest的属性值复制到对象orig中
     *
     * @param orig
     * @param clazz
     * @return T
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static <T> T copyProperties(Object orig, Class<T> clazz) {
        if (orig == null) {
            throw new UtilException("input cannot be none");
        }
        T t = null;
        try {
            t = clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            logger.error(e.getMessage(), e);
        }
        BeanUtils.copyProperties(orig, t);
        return t;
    }

    /**
     * 将对象dest的属性值复制到对象orig中
     *
     * @param orig
     * @param destObj
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static void copyProperties(Object orig, Object destObj) {
        BeanUtils.copyProperties(orig, destObj);
    }

    /**
     * 将对象dest的属性值复制到对象orig中
     *
     * @param orig
     * @param destObj
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static void copyProperties(Object orig, Object destObj, String... ignoreProperties) {
        BeanUtils.copyProperties(orig, destObj, ignoreProperties);
    }

    /**
     * 将对象dest的属性值复制到对象orig中
     *
     * @param orig
     * @param destObj
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static void copyPropertiesWithOutNull(Object orig, Object destObj) {
        BeanUtils.copyProperties(orig, destObj, getNullPropertyNames(orig));
    }


    /**
     * 查询null 值，不进行copy 操作
     *
     * @param source
     * @return java.lang.String[]
     * @throws
     * @author zhangxianchao
     * @since 2018/7/24 0024
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 把origList 的属性值复制到 destList中
     *
     * @param origList
     * @param destClass
     * @return java.util.List<T>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static <T> List<T> copyList(List<?> origList, Class<T> destClass) {
        List<T> destList = new ArrayList<>();
        for (Object orig : origList) {
            destList.add(copyProperties(orig, destClass));
        }
        return destList;
    }

    /**
     * 将map 转换成对象
     *
     * @param clazz
     * @param map
     * @return T
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static <T, V> T toObject(Class<T> clazz, Map<String, V> map) {
        try {
            T object = clazz.getDeclaredConstructor().newInstance();
            return toObject(object, map);
        } catch (Exception e) {
            throw new UtilException(errorMsg, e);
        }
    }

    /**
     * 将map 转换成对象
     *
     * @param clazz
     * @param map
     * @param toCamelCase
     * @return T
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static <T, V> T toObject(Class<T> clazz, Map<String, V> map, boolean toCamelCase) {
        try {
            T object = clazz.getDeclaredConstructor().newInstance();
            return toObject(object, map, toCamelCase);
        } catch (Exception e) {
            throw new UtilException(errorMsg, e);
        }

    }

    /**
     * 将map 转换成对象
     *
     * @param object
     * @param map
     * @return T
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static <T, V> T toObject(T object, Map<String, V> map) {
        try {
            org.apache.commons.beanutils.BeanUtils.populate(object, map);
            return object;
        } catch (Exception e) {
            throw new UtilException(errorMsg, e);
        }

    }

    /**
     * 将map 转换成对象,将完成驼峰式转换
     *
     * @param object
     * @param map
     * @param toCamelCase
     * @return T
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static <T, V> T toObject(T object, Map<String, V> map, boolean toCamelCase) {
        try {
            if (toCamelCase) {
                map = toCamelCaseMap(map);
            }
            org.apache.commons.beanutils.BeanUtils.populate(object, map);
            return object;
        } catch (Exception e) {
            throw new UtilException(errorMsg, e);
        }
    }

    /**
     * 将对象转换成MAP
     *
     * @param obj
     * @return java.util.Map<java.lang.String
                       *       ,
                       *       java.lang
                       *       .
                       *       <   p>
     * String>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static Map<String, Object> toMap(Object obj) {
        try {
            if (obj == null) {
                return null;
            }

            Map<String, Object> map = new HashMap();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                map.put(key, value);
            }
            return map;
        } catch (Exception e) {
            throw new UtilException(errorMsg2, e);
        }

    }

    /**
     * 转换成Map并提供字段命名驼峰转平行
     *
     * @param object
     * @return java.util.Map<java.lang.String
                       *       ,
                       *       java.lang
                       *       .
                       *       String>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static Map<String, Object> toMapForFlat(Object object) {
        try {
            Map<String, Object> map = toMap(object);
            return toUnderlineStringMap(map);
        } catch (Exception e) {
            throw new UtilException(errorMsg2, e);
        }
    }

    /**
     * 对对象集合转换成MAP 集合
     *
     * @param collection
     * @return java.util.Collection<java.util.Map
                       *       <   p>
     * <
     * java
     * .       lang.String
     * ,
     * java.lang.String>>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static <T> Collection<Map<String, Object>> toMapList(Collection<T> collection) {
        List<Map<String, Object>> retList = new ArrayList();
        if (collection != null && !collection.isEmpty()) {
            for (Object object : collection) {
                Map<String, Object> map = toMap(object);
                retList.add(map);
            }
        }
        return retList;
    }

    /**
     * 转换成驼峰格式
     *
     * @param map
     * @return java.util.Map<java.lang.String
                       *       <   p>
     * ,
     * V>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static <V> Map<String, V> toCamelCaseMap(Map<String, V> map) {
        Map<String, V> newMap = new HashMap();
        for (Map.Entry<String, V> entry : map.entrySet()) {
            safeAddToMap(newMap, JavaBeanUtil.toCamelCaseString(entry.getKey()), entry.getValue());
        }
        return newMap;
    }

    /**
     * 将Map的Keys转译成下划线格式的<br>
     *
     * @param map 需要转换的map
     * @return java.util.Map<java.lang.String
                       *       ,
                       *       V>
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static <V> Map<String, V> toUnderlineStringMap(Map<String, V> map) {
        Map<String, V> newMap = new HashMap();
        for (Map.Entry<String, V> entry : map.entrySet()) {
            newMap.put(JavaBeanUtil.toUnderlineString(entry.getKey()), entry.getValue());
        }
        return newMap;
    }


    /**
     * 转换为Collection,同时为字段做驼峰转换<Map<K, V>>
     *
     * @param collection 需要转换的集合
     * @return java.util.Collection<java.util.Map
                       *       <
                       *       java
                       *       .
                       *       lang.String
                       *       ,
                       *       java.lang.String>>
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    @SuppressWarnings("unchecked")
    public static <T> Collection<Map<String, Object>> toMapListForFlat(Collection<T> collection) {
        List<Map<String, Object>> retList = new ArrayList();
        if (collection != null && !collection.isEmpty()) {
            for (Object object : collection) {
                Map<String, Object> map = toMapForFlat(object);
                retList.add(map);
            }
        }
        return retList;
    }

    /**
     * 根据方法名字对对象进行赋值
     *
     * @param object
     * @param methodName
     * @param value
     * @return void
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    public static void populateValueByMethod(Object object, String methodName, Object value) {
        Method method = BeanUtils.findMethodWithMinimalParameters(object.getClass(), methodName);
        try {
            method.invoke(object, value);
        } catch (Exception e) {
            throw new UtilException("属性赋值失败", e);
        }
    }

}
