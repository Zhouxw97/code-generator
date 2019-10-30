/**
 * Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.framework
 * @File name : JavaBeanUtil.java
 * @Author : zhangxc
 * @Date : 2017年2月25日
 * <p>
 * ----------------------------------------------------------------------------------
 * Date       Who       Version     Comments
 * 1. 2017年2月25日    zhangxc    1.0
 * <p>
 * <p>
 * <p>
 * <p>
 * ----------------------------------------------------------------------------------
 */

package com.yonyou.cyx.function.utils.bean;

import java.util.Locale;

/**
 * Java 类型转换
 *
 * @author zhangxc
 * @date 2017年2月25日
 */

public class JavaBeanUtil {

    private static final char SEPARATOR = '_';

    private JavaBeanUtil() {
    }

    /**
     * 将属性样式字符串转成驼峰样式字符串<br>
     * (例:branchNo -> branch_no)<br>
     *
     * @param inputString
     * @return
     */
    public static String toUnderlineString(String inputString) {
        if (inputString == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            boolean nextUpperCase = true;

            if (i < (inputString.length() - 1)) {
                nextUpperCase = Character.isUpperCase(inputString.charAt(i + 1));
            }

            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 将驼峰字段转成属性字符串<br>
     * (例:branch_no -> branchNo )<br>
     *
     * @param inputString 输入字符串
     * @return
     */
    public static String toCamelCaseString(String inputString) {
        return toCamelCaseString(inputString, false);
    }

    /**
     * 将驼峰字段转成属性字符串<br>
     * (例:branch_no -> branchNo )<br>
     *
     * @param inputString             输入字符串
     * @param firstCharacterUppercase 是否首字母大写
     * @return
     */
    public static String toCamelCaseString(String inputString, boolean firstCharacterUppercase) {
        if (inputString == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

    /**
     * 得到标准字段名称
     *
     * @param inputString 输入字符串
     * @return
     */
    public static String getValidPropertyName(String inputString) {
        String answer;
        if (inputString == null) {
            answer = null;
        } else if (inputString.length() < 2) {
            answer = inputString.toLowerCase(Locale.US);
        } else {
            if (Character.isUpperCase(inputString.charAt(0)) && !Character.isUpperCase(inputString.charAt(1))) {
                answer = inputString.substring(0, 1).toLowerCase(Locale.US) + inputString.substring(1);
            } else {
                answer = inputString;
            }
        }
        return answer;
    }

    /**
     * 将属性转换成标准set方法名字符串<br>
     *
     * @param property
     * @return
     */
    public static String getSetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();

        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0)) && sb.length() > 1 && !Character.isUpperCase(sb.charAt(1))) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        sb.insert(0, "set");
        return sb.toString();
    }

    /**
     * 将属性转换成标准get方法名字符串<br>
     *
     * @param property
     * @return
     */
    public static String getGetterMethodName(String property) {
        StringBuilder sb = new StringBuilder();

        sb.append(property);
        if (Character.isLowerCase(sb.charAt(0)) && sb.length() > 1 && !Character.isUpperCase(sb.charAt(1))) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        sb.insert(0, "get");
        return sb.toString();
    }
}
