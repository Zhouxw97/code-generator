/**
 * Copyright (c) 2011-2016, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.yonyou.cyx.cyxframework.util.dao;

import java.util.Properties;

/**
 * code is far away from bug with the animal protecting
 *
 * @Description : MybatisPlus代码生成器
 * ---------------------------------
 * @Author : zhangxianchao
 * @Date : Create in 2017/9/19 14:48
 */
public class MyBatisPlusGenerator {

    private MyBatisPlusGenerator() {
    }

    /**
     * 生成文件
     *
     * @return void
     * @author zhangxianchao
     * @since 2018/7/28 0028
     */
    public static void generate(Properties properties) {
        String superEntityClass = properties.getProperty("output.superEntityClass");
        String superMapperClass = properties.getProperty("output.superMapperClass");
        String superControllerClass = properties.getProperty("output.superControllerClass");
        String logicDeleteFieldName = properties.getProperty("output.logicDeleteFieldName");
        String versionFieldName = properties.getProperty("output.versionFieldName");
        String superEntityColumns = properties.getProperty("output.superEntityColumns");

        String[] columns = superEntityColumns.split(",");

        MyBatisPlusCustomConfig config = new MyBatisPlusCustomConfig();
        config.setSuperEntityColumns(columns);
        config.setSuperEntityClass(superEntityClass);
        // 自定义 mapper 父类
        config.setSuperMapperClass(superMapperClass);
        // 自定义 controller 父类
        config.setSuperControllerClass(superControllerClass);
        config.setEntityBooleanColumnRemoveIsPrefix(false);
        config.setLogicDeleteFieldName(logicDeleteFieldName);
        config.setVersionFieldName(versionFieldName);
        //执行代码生成
        MyBatisPlusCommonGenerator.generate(config, properties);
    }
}