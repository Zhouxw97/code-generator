/**
 * Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.function
 * @File name : JSONUtil.java
 * @Author : zhangxc
 * @Date : 2017年1月13日
 * <p>
 * ----------------------------------------------------------------------------------
 * Date       Who       Version     Comments
 * 1. 2017年1月13日    zhangxc    1.0
 * <p>
 * <p>
 * <p>
 * <p>
 * ----------------------------------------------------------------------------------
 */

package com.yonyou.cyx.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.cyx.function.exception.UtilException;
import com.yonyou.cyx.function.utils.common.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JSON 转换公共方法
 *
 * @author zhangxc
 * @date 2017年1月13日
 */

public class JSONUtil {

    private static String errorMsg = "json 转换失败";

    private JSONUtil() {
    }

    /**
     * 将json转化为实体POJO
     *
     * @param jsonStr
     * @param obj
     * @return
     * @throws Exception
     */
    public static <T> T jsonToObj(String jsonStr, Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            t = objectMapper.readValue(jsonStr, obj);
        } catch (Exception e) {
            throw new UtilException(errorMsg, e);
        }
        return t;
    }

    /**
     * 将json转化为实体POJO
     *
     * @param jsonBytes
     * @param obj
     * @return
     * @throws Exception
     */
    public static <T> T jsonToObj(byte[] jsonBytes, Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            t = objectMapper.readValue(jsonBytes, obj);
        } catch (Exception e) {
            throw new UtilException(errorMsg, e);
        }
        return t;
    }

    /**
     * 将json转化为实体POJO
     *
     * @param jsonStr
     * @param typeReference
     * @return
     * @throws Exception
     */
    public static <T> T jsonToObj(String jsonStr, TypeReference<T> typeReference) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            t = objectMapper.readValue(jsonStr, typeReference);
        } catch (Exception e) {
            throw new UtilException(errorMsg, e);
        }
        return t;
    }

    /**
     * 将实体POJO转化为JSON
     *
     * @param <T>
     * @param obj
     * @return
     * @throws IOException
     */
    public static <T> String objectToJson(T obj) {
        String jsonStr = null;

        if (obj != null) {
            if (obj instanceof String) {
                return obj.toString();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                jsonStr = mapper.writeValueAsString(obj);
            } catch (IOException e) {
                throw new UtilException(errorMsg, e);
            }
        }
        return jsonStr;
    }

    /**
     * @param json
     * @return
     * @throws Exception
     * @author zhangxc @ description JSON转换为MAP
     * @date 2016年12月7日
     */
    public static Map<String, Object> jsonToMap(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (!StringUtils.isNullOrEmpty(json)) {
                json = json.replaceAll("NULL", "null");
                mapper.setSerializationInclusion(Include.NON_NULL);
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) mapper.readValue(json, Map.class);
                return map;
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new UtilException(errorMsg, e);
        }
    }

    /**
     * @param jsonArray
     * @return
     * @throws Exception
     * @author huangyuhua @ description JSON转换为MAPList
     * @date 2017年5月18日
     */
    public static List<Map<String, Object>> jsonToMapList(String jsonArray) {
        if (!StringUtils.isNullOrEmpty(jsonArray)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                @SuppressWarnings("unchecked")
                JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Map.class);

                return mapper.readValue(jsonArray, javaType);
            } catch (Exception e) {
                throw new UtilException(errorMsg, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * @param jsonArray
     * @return
     * @throws Exception
     * @author huangyuhua @ description JSON转换为MAPList
     * @date 2017年5月18日
     */
    public static <T> List<T> jsonToList(String jsonArray, Class<T> className) {
        if (!StringUtils.isNullOrEmpty(jsonArray)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, className);
                @SuppressWarnings("unchecked")
                List<T> list = mapper.readValue(jsonArray, javaType);
                return list;
            } catch (Exception e) {
                throw new UtilException(errorMsg, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * @param json
     * @return
     * @throws Exception
     * @author zhangxc @ description JSON转换为MAP
     * @date 2016年12月7日
     */
    public static Map<String, String> jsonToMapString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> map = (Map<String, String>) mapper.readValue(json, Map.class);
            return map;
        } catch (Exception e) {
            throw new UtilException(errorMsg, e);
        }
    }
}
