/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : JsonIOSLocalDateDeserializer.java
*
* @Author : zhangxianchao
*
* @Date : 2016年2月24日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月24日    zhangxianchao    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.cyx.function.utils.date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.yonyou.cyx.function.constants.FunctionConstants;
import com.yonyou.cyx.function.exception.JsonSerializeException;
import com.yonyou.cyx.function.utils.common.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *
 * @author zhangxianchao
 * JsonLocalDateDeserializer
 * @date 2016年2月24日
 */

public class JsonSimpleDateTimeDeserializer extends JsonDeserializer<Date> {

    /*
     * @author zhangxianchao
     * @date 2016年2月24日
     * @param p
     * @param ctxt
     * @return
     * @throws IOException
     * @throws JsonProcessingException (non-Javadoc)
     * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser,
     * com.fasterxml.jackson.databind.DeserializationContext)
     */

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat(FunctionConstants.SIMPLE_DATE_TIME_FORMAT);
        String date = p.getText();
        try {
            if (StringUtils.isNullOrEmpty(date)) {
                return null;
            } else {
                return format.parse(date);
            }
        } catch (ParseException e) {
            throw new JsonSerializeException(e);
        }
    }

}
