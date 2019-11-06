/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : JsonIOSLocalDateTimeSerializer.java
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

package com.yonyou.cyx.function.utils.jsonserializer.localdatetime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yonyou.cyx.function.constants.FunctionConstants;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
 *
 * @author zhangxianchao
 * JsonIOSLocalDateTimeSerializer
 * @date 2016年2月24日
 */

public class JsonSimpleLocalDateSerializer extends JsonSerializer<LocalDate> {

    /*
     * @author zhangxianchao
     * @date 2016年2月24日
     * @param value
     * @param gen
     * @param serializers
     * @throws IOException
     * @throws JsonProcessingException (non-Javadoc)
     * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
     * com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
     */

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(FunctionConstants.SIMPLE_DATE_FORMAT);
        gen.writeString(dateFormat.format(value));
    }

}
