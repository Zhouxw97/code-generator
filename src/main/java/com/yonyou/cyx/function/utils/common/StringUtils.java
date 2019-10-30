/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.framework
*
* @File name : StringUtils.java
*
* @Author : zhangxianchao
*
* @Date : 2016年2月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月14日    zhangxianchao    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.cyx.function.utils.common;

import com.yonyou.cyx.function.exception.UtilException;
import com.yonyou.cyx.function.sercice.common.RegexReplaceCallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *
 * @author zhangxianchao
 * StringUtils
 * @date 2016年2月14日
 */

public final class StringUtils {
    public static final String EMPTY_STRING = "";
    public static final String BLANK_SPRING_STRING = " ";
    public static final char CHAR_TILDE = '~';
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    private StringUtils() {
    }

    public static UUID getRandomUUID() {
        return UUID.randomUUID();
    }

    public static boolean isBlank(CharSequence cs) {
        return org.springframework.util.StringUtils.isEmpty(cs);
    }

    /**
     * check if null or empty string
     */
    public static boolean isNullOrEmpty(Object obj) {
        return (null == obj || EMPTY_STRING.equals(obj) || "\"\"".equals(obj));
    }

    /**
     * 检查是否相等
     *
     * @param obj
     * @param expectValue
     * @return
     * @author zhangxc
     * @date 2016年7月7日
     */
    public static boolean isEquals(Object obj, String expectValue) {
        if (isNullOrEmpty(obj) || isNullOrEmpty(expectValue)) {
            return false;
        }
        return expectValue.equals(obj.toString());
    }

    /**
     * 检查是否相等（忽略大小写）
     *
     * @param obj
     * @param expectValue
     * @return
     * @author zhangxc
     * @date 2016年7月7日
     */
    public static boolean isEqualsNoCasetive(Object obj, String expectValue) {
        if (isNullOrEmpty(obj) || isNullOrEmpty(expectValue)) {
            return false;
        }
        return expectValue.equalsIgnoreCase(obj.toString());
    }

    /**
     * escape html str
     */
    public static String escapeHtml(String str) {
        return HtmlUtils.htmlEscape(str);
    }

    /**
     * unescape html str
     */
    public static String unEscapeHtml(String str) {
        return HtmlUtils.htmlUnescape(str);
    }

    /**
     * substring with utf-8 bytes length
     *
     * @param utfStr the string with utf-8 encoding
     */
    public static String subStringWithBytesLength(String utfStr, int bytesLength) {
        if (null == utfStr) {
            throw new UtilException("input string cannot be null");
        }
        if (bytesLength <= 0) {
            throw new UtilException("bytesLength should >0");
        }
        byte[] bytes = utfStr.getBytes(StandardCharsets.UTF_8);
        if (bytes.length <= bytesLength) {
            return utfStr;
        }
        int index = bytesLength;
        String temp1 = new String(bytes, 0, index);
        String temp2 = new String(bytes, 0, index + 1);
        // when temp2 is end with a new char
        if (temp2.length() > temp1.length()) {
            return temp1;
        }
        // when temp2.length()=temp1.length()
        temp2 = new String(bytes, 0, index - 1);
        // when temp2 is end with a whole char
        if (temp2.length() < temp1.length()) {
            return temp2;
        }
        // when the new String(bytes, 0, index) is in the middle char
        return new String(bytes, 0, index - 2);
    }

    /**
     * split string(bytes) into list
     */
    public static List<String> subStringToListWithBytesLength(String utfStr, int bytesLength) {
        if (null == utfStr) {
            throw new UtilException("input string cannot be null");
        }
        if (bytesLength <= 0) {
            throw new UtilException("bytesLength should >0");
        }
        List<String> list = new ArrayList<>();
        String temp = utfStr;
        String data = EMPTY_STRING;
        while (temp.length() != data.length()) {
            temp = temp.substring(data.length());
            data = subStringWithBytesLength(temp, bytesLength);
            list.add(data);
        }
        return list;
    }

    /**
     * get last size string with maxsize,if string.length> maxsize , return string 'ABC' to '~BC'
     *
     * @
     */
    public static String getLastStringWithTilde(String str, int maxSize) {
        if (maxSize <= 3) {
            throw new UtilException("max size should >3");
        }
        byte[] bytes = str.getBytes();
        if (bytes.length <= maxSize) {
            return str;
        }
        String temp1 = new String(bytes, bytes.length - maxSize + 1, maxSize - 1);

        // the first char is not messy code
        if (temp1.getBytes().length <= maxSize - 1) {
            return CHAR_TILDE + temp1;
        }
        String temp2 = new String(bytes, bytes.length - maxSize + 2, maxSize - 2);
        // the first char is not messy code
        if (temp2.getBytes().length <= maxSize - 2) {
            return CHAR_TILDE + temp2;
        }
        String temp3 = new String(bytes, bytes.length - maxSize + 3, maxSize - 3);
        // the first char is not messy code
        if (temp3.getBytes().length <= maxSize - 3) {
            return CHAR_TILDE + temp3;
        }
        throw new UtilException("this cannot be true in utf-8");
    }

    /**
     * 将集合转化为按指定分隔符分隔的字符串
     *
     * @param list
     * @param separator
     * @return java.lang.String
     * @author zhangxianchao
     * @since 2018/7/28 0028
     */
    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * 判断是否是符合的正则表达示
     *
     * @param pattenStr
     * @param extensions
     * @return
     * @author zhangxc
     * @date 2016年10月27日
     */
    public static boolean isMatcherPatten(String pattenStr, String extensions) {
        Pattern patten = Pattern.compile(pattenStr);
        Matcher matcher = patten.matcher(extensions);
        return matcher.find();
    }

    /*
     * @author zhangxc 生成指定长度的随机
     * @date 2016年3月31日
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String[] randStr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1",
                "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomValue = new Random().nextInt(randStr.length - 1);
            sb.append(randStr[randomValue]);
        }
        return sb.toString();
    }

    /**
     * 对String 的正则表达示进行处理
     *
     * @param sourceStr
     * @param splitPattern
     * @param callBack
     * @return (non - Javadoc)
     * @author zhangxc
     * @date 2017年1月14日
     */
    public static String getMatcherPatternStr(String sourceStr, String splitPattern, RegexReplaceCallBack callBack) {
        Pattern patten = Pattern.compile(splitPattern);
        Matcher matcher = patten.matcher(sourceStr);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String groupStr = matcher.group(1);
            matcher.appendReplacement(sb, callBack.replace(groupStr));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 替换首个字符串
     *
     * @param str
     * @param regx
     * @param replaceStr
     * @return
     * @author LiaoYuzhi
     * @date 2017年8月15日
     */

    public static String replaceFirst(String str, String regx, String replaceStr) {
        Pattern p = Pattern.compile(regx);
        Matcher m = p.matcher(str);
        return m.replaceFirst(replaceStr);
    }

    /**
     * 根据分隔符，将字符串转化为对应的数组
     *
     * @param str
     * @param split
     * @return java.lang.Long[]
     * @throws
     * @author zhangxianchao
     * @since 2018/7/24 0024
     */
    public static <T> List<T> convertStrToArray(String str, String split, Class<T> returnClass) {
        if (StringUtils.isNullOrEmpty(str)) {
            return Collections.emptyList();
        }
        try {
            //转化为数组
            String[] splitArray = str.split(split);
            List<T> returnList = new ArrayList<>();
            for (int i = 0; i < splitArray.length; i++) {
                Constructor<T> constructor = returnClass.getConstructor(String.class);
                T instance = constructor.newInstance(splitArray[i]);
                returnList.add(instance);
            }
            return returnList;
        } catch (Exception e) {
            throw new UtilException(e.getMessage(), e);
        }

    }

    /**
     * 将首字母转换为小写
     *
     * @param str
     * @return java.lang.String
     * @throws
     * @author zhangxianchao
     * @since 2018/7/28 0028
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 将首字母转换为大写
     *
     * @param str
     * @return java.lang.String
     * @author zhangxianchao
     * @since 2018/7/28 0028
     */
    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 将某个集合字符串按某个分隔符进行分割
     *
     * @param stringList
     * @param delimiter
     * @return java.lang.String
     * @author zhangxianchao
     * @since 2018/9/28 0028
     */
    public static String join(List<?> stringList, String delimiter) {
        return listToString(stringList, delimiter.charAt(0));
    }

    /**
     * NULL字符串转换，参数对象为null时，返回空字符串
     *
     * @param object 转换原对象
     * @return 字符串
     */
    public static String nvl(Object object) {
        if (object == null) {
            return "";
        }
        return object.toString().trim();
    }

    /**
     * 根据输入的值获取对应的数字
     *
     * @param obj
     * @return java.lang.Number
     * @author zhangxianchao
     * @since 2018/9/29 0029
     */
    public static Integer getInteger(Object obj) {
        int def = 0;
        if (obj != null) {
            try {
                def = Integer.parseInt(obj.toString());
            } catch (Exception ex) {
                logger.debug("异常处理");
            }

        }
        return def;
    }

    /**
     * @param @param  obj
     * @param @return
     * @return Integer[]
     * @throws
     * @Title: parseStr2IntArray
     * @Description: 以“，”分隔的字符串转成 INT数组
     */
    public static Integer[] parseStr2IntArray(String obj) {
        String[] str = obj.split(",");
        Integer[] array = new Integer[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        return array;
    }

}
