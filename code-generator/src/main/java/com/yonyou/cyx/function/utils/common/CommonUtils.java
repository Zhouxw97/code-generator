/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : HttpUtils.java
*
* @Author : zhangxianchao
*
* @Date : 2016年2月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月23日    zhangxianchao    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.cyx.function.utils.common;

import com.yonyou.cyx.function.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/*
 *
 * @author zhangxianchao
 * CommonUtils
 * @date 2016年2月23日
 */

public class CommonUtils {

    private static final String STR_NEW_LINE = "\n\r";
    private static final String STR_SEPARATED_BLANK = StringUtils.BLANK_SPRING_STRING;
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    private CommonUtils() {
    }

    /**
     * check if the list is null or empty
     */
    public static boolean isNullOrEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * check if the map is null or empty
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /*
     * @author LiuJun 产生紧凑型32位UUID-目前用于SAF taskId
     * @date 2016年3月28日
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * parse request and get data var html
     */
    public static String parseHttpServletRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("date:").append(new Date());
        sb.append(STR_NEW_LINE);
        sb.append("request: ").append(request.getPathInfo());
        sb.append(STR_NEW_LINE);
        sb.append("request method: ").append(request.getMethod());
        sb.append(STR_NEW_LINE);
        sb.append("request parameter：");
        sb.append(STR_NEW_LINE);
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String data = request.getParameter(name);
            appendValue(name, data, sb);
        }
        sb.append("header parameter" + "-----------------");
        sb.append(STR_NEW_LINE);
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            String data = request.getHeader(name);
            appendValue(name, data, sb);
        }
        sb.append("header parameter" + "-----------------");
        sb.append(STR_NEW_LINE);
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                appendValue(cookie.getName(), cookie.getValue(), sb);
            }
        }
        return sb.toString();
    }

    /**
     * 进行数据拼接
     *
     * @param key
     * @param value
     * @param sb
     * @return void
     * @author zhangxianchao
     * @since 2018/10/7 0007
     */
    private static void appendValue(String key, String value, StringBuilder sb) {
        sb.append("key " + key);
        sb.append(STR_SEPARATED_BLANK);
        sb.append("value " + value);
        sb.append(STR_NEW_LINE);
    }


    /**
     * get local host
     */
    public static InetAddress getLocalHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new UtilException(e);
        }
    }

    /**
     * get local host
     */
    public static InetAddress[] getAllLocalHosts() {
        String hostName = CommonUtils.getLocalHostName();
        if (StringUtils.isNullOrEmpty(hostName)) {
            throw new UtilException("cannot get local host");
        }
        try {
            return InetAddress.getAllByName(hostName);
        } catch (UnknownHostException e) {
            throw new UtilException(e);
        }

    }

    /**
     * get local host name
     */
    public static String getLocalHostName() {
        InetAddress address = getLocalHost();
        return address.getHostName();
    }

    /**
     * List<Map> 转换字符串 （[{OWNER_CODE=2100000, ROLE=10060001}, {OWNER_CODE=2100000, ROLE=10060002},
     * {OWNER_CODE=2100000, ROLE=10060003}]）
     * Map中有两列   OWNER_CODE和 ROLE   默认拼接第二个（ROLE）  的value值
     *
     * @param list      转换的list
     * @param separator 分隔符
     * @return
     * @author jcsi
     * @date 2016年7月12日
     */
    public static String listMapToString(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        String str = "";
        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = (Map) list.get(i);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    str = entry.getValue() + "";
                }
                sb.append(str).append(separator);
            }
            return sb.toString().substring(0, sb.toString().length() - 1);
        } else {
            return "";
        }


    }

    /**
     * 普通List转换字符串
     *
     * @param list
     * @param separator
     * @return
     * @author jcsi
     * @date 2016年7月12日
     */
    public static String listToString(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * requestbody转换成byte[]
     *
     * @param request
     * @return byte[]
     * @author huangyuhua
     * @date 2017年4月18日
     */
    public static byte[] binaryReader(HttpServletRequest request) {

        int len = request.getContentLength();
        if (len > 0) {
            logger.debug("buffer len:{}", len);
            byte[] buffer = new byte[len];
            try {
                ServletInputStream sistream = request.getInputStream();
                sistream.read(buffer, 0, len);
                return buffer;
            } catch (Exception e) {
                throw new UtilException("读取消息体失败", e);
            }
        }
        return new byte[0];
    }
}
