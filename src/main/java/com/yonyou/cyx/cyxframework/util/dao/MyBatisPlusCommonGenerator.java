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

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.yonyou.cyx.function.utils.common.StringUtils;
import com.yonyou.cyx.function.utils.resource.YamlUtils;

import java.io.File;
import java.util.*;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @Description : MybatisPlus代码生成器
 * ---------------------------------
 * @Author : Liang.Guangqing
 * @Date : Create in 2017/9/19 14:48
 */
public class MyBatisPlusCommonGenerator {

    private static final String JAVA_VM_SUFFIX = ".java.vm";
    private static final String OUTPUT_MODULE_NAME = "output.moduleName";
    private static final String OUTPUT_PATH = "output.path";

    private MyBatisPlusCommonGenerator() {
    }

    /**
     * 生成文件
     *
     * @return void
     * @author zhangxianchao
     * @since 2018/7/28 0028
     */
    public static void generate(MyBatisPlusCustomConfig customConfig) {
        //加载生成配置文件
        Properties properties = YamlUtils.yaml2Properties("generator.yaml");


        String moduleName = properties.getProperty(OUTPUT_MODULE_NAME);
        String packageName = properties.getProperty("output.packageName");
        String controllerPackage = properties.getProperty("output.defineChildPackage.controller");
        controllerPackage = controllerPackage == null ? "controller" : controllerPackage;

        String mapperPackage = properties.getProperty("output.defineChildPackage.mapper");
        mapperPackage = mapperPackage == null ? "dao" : mapperPackage;

        String entityPackage = properties.getProperty("output.defineChildPackage.entity");
        entityPackage = entityPackage == null ? "bean.entity" : entityPackage;

        String servicePackage = properties.getProperty("output.defineChildPackage.service");
        final String servicePackageStatic = servicePackage == null ? "service" : servicePackage;


        String serviceImplPackage = properties.getProperty("output.defineChildPackage.serviceImpl");
        final String serviceImplPackageStatic = serviceImplPackage == null ? "impl" : serviceImplPackage;


        String moduleNameDir = moduleName.replaceAll("\\.", "/");


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //行常规设置
        setAutoGeneratorConfig(mpg, properties, customConfig);

        //进行模板配置
        setTemplateConfig(mpg, properties);


        // 包配置
        PackageConfig packageConfig = new PackageConfig().setParent(packageName);
        packageConfig.setController(controllerPackage + "." + moduleName);
        packageConfig.setEntity(entityPackage + "." + moduleName);
        packageConfig.setService(servicePackageStatic + "." + moduleName);
        packageConfig.setServiceImpl(servicePackageStatic + "." + moduleName + "." + serviceImplPackageStatic);
        packageConfig.setMapper(mapperPackage + "." + moduleName);
        mpg.setPackageInfo(packageConfig);


        String dtoPackage = properties.getProperty("output.defineChildPackage.dto");
        final String dtoPackageStatic = dtoPackage == null ? "bean.dto" : dtoPackage;
        InjectionConfig injectionConfig = new InjectionConfig() {
            /**
             * 注入自定义 Map 对象
             */
            @Override
            public void initMap() {
                this.getConfig().getGlobalConfig().getEntityName();
                Map<String, Object> map = new HashMap<>();
                map.put("packageDto", packageName + (mpg.getPackageInfo().getModuleName()
                        == null ? "" : "." + mpg.getPackageInfo().getModuleName()) + "."
                        + dtoPackageStatic + "." +
                        moduleName);
                map.put("moduleName", moduleNameDir);

                map.put("packageServiceImplTest", packageName + (mpg.getPackageInfo().getModuleName()
                        == null ? "" : "." + mpg.getPackageInfo().getModuleName()) + "."
                        + servicePackageStatic + "." +
                        moduleName);

                this.setMap(map);
            }
        };


        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        //进行xml 生成配置
        setXmlOutPutConfig(properties, fileOutConfigs);
        //进行DTO 输出配置
        setDtoOutPutConfig(properties, fileOutConfigs, mpg);
        //进行ControllerTest 输出配置
        setControllerTestConfig(properties, fileOutConfigs, mpg);
        //进行ServiceTest 输出配置
        setServiceTestConfig(properties, fileOutConfigs, mpg);

        injectionConfig.setFileOutConfigList(fileOutConfigs);

        mpg.setCfg(injectionConfig);


        // 执行生成
        mpg.execute();

    }


    /**
     * 进行常规设置
     *
     * @param mpg
     * @param properties
     * @return void
     * @author zhangxianchao
     * @since 2018/10/8 0008
     */
    private static void setAutoGeneratorConfig(AutoGenerator mpg, Properties properties, MyBatisPlusCustomConfig
            customConfig) {
        //文件路径
        String outputPath = properties.getProperty(OUTPUT_PATH);
        File file = new File(outputPath);
        String path = file.getAbsolutePath();
        //作者
        String authorName = properties.getProperty("output.author");
        //table名字
        String[] tables = properties.getProperty("table.tableNames").split(",");
        //table前缀
        String prefix = properties.getProperty("table.tablePrefix");

        mpg.setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        .setOutputDir(path + "/src/main/java")//输出目录
                        .setFileOverride(true)// 是否覆盖文件
                        .setActiveRecord(true)// 开启 activeRecord 模式
                        .setEnableCache(false)// XML 二级缓存
                        .setBaseResultMap(true)// XML ResultMap
                        .setBaseColumnList(true)// XML columList
                        .setOpen(false)//生成后打开文件夹
                        .setAuthor(authorName)
                        // 自定义文件命名，注意 %s 会自动填充表实体属性！
                        .setMapperName("%sMapper")
                        .setXmlName("%sMapper")
                        .setEntityName("%sPO")
                        .setServiceName("%sService")
                        .setServiceImplName("%sServiceImpl")
                        .setControllerName("%sController")
        );
        mpg.setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(DbType.MYSQL)// 数据库类型
                        .setDriverName(properties.getProperty("db.driverName"))
                        .setUsername(properties.getProperty("db.username"))
                        .setPassword(properties.getProperty("db.password"))
                        .setUrl(properties.getProperty("db.url"))
        );
        mpg.setStrategy(
                // 策略配置
                new StrategyConfig()
                        .setTablePrefix(prefix)// 此处可以修改为您的表前缀
                        .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                        .setInclude(tables) // 需要生成的表
                        .setRestControllerStyle(true)
                        //.setExclude(new String[]{"test"}) // 排除生成的表
                        // 自定义实体父类
                        // .setSuperEntityClass("com.baomidou.demo.TestEntity")
                        // 自定义实体，公共字段
                        .setSuperEntityColumns(customConfig.getSuperEntityColumns())
//                        .setSuperEntityColumns(new String[]{"createdBy", "createdTime", "updatedBy",
//                                "updatedTime", "recordVersion"})
                        .setTableFillList(null)
                        .setSuperEntityClass(customConfig.getSuperEntityClass())
                        // 自定义 mapper 父类
                        .setSuperMapperClass(customConfig.getSuperMapperClass())
                        // 自定义 service 父类
                        // .setSuperServiceClass("com.baomidou.demo.TestService")
                        // 自定义 service 实现类父类
                        // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
                        // 自定义 controller 父类
                        .setSuperControllerClass(customConfig.getSuperControllerClass())
                        .setEntityBooleanColumnRemoveIsPrefix(customConfig.isEntityBooleanColumnRemoveIsPrefix())
                        .setLogicDeleteFieldName(customConfig.getLogicDeleteFieldName())
                        .setVersionFieldName(customConfig.getLogicDeleteFieldName())
                // .setRestControllerStyle(true)
                // .setControllerMappingHyphenStyle(true)
        );
    }

    /**
     * 进行模板配置
     *
     * @param mpg
     * @param properties
     * @return void
     * @author zhangxianchao
     * @since 2018/10/8 0008
     */
    private static void setTemplateConfig(AutoGenerator mpg, Properties properties) {
        String isServiceImplGenerate = properties.getProperty("output.isGeneratePackage.serviceImpl");
        String isControllerGenerate = properties.getProperty("output.isGeneratePackage.controller");
        String isMapperGenerate = properties.getProperty("output.isGeneratePackage.mapper");
        String isServiceGenerate = properties.getProperty("output.isGeneratePackage.service");
        String isEntityGenerate = properties.getProperty("output.isGeneratePackage.entity");

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig templateConfig = new TemplateConfig().setXml(null);

        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
        if (StringUtils.isNullOrEmpty(isControllerGenerate) || isControllerGenerate.equals("true")) {
            templateConfig.setController("/template/myController.java.vm");
        } else {
            templateConfig.setController(null);
        }
        if (StringUtils.isNullOrEmpty(isEntityGenerate) || isEntityGenerate.equals("true")) {
            templateConfig.setEntity("/template/myEntity.java.vm");
        } else {
            templateConfig.setEntity(null);
        }
        if (StringUtils.isNullOrEmpty(isServiceGenerate) || isServiceGenerate.equals("true")) {
            templateConfig.setService("/template/myService.java.vm");
        } else {
            templateConfig.setService(null);
        }
        if (StringUtils.isNullOrEmpty(isServiceImplGenerate) || isServiceImplGenerate.equals("true")) {
            templateConfig.setServiceImpl("/template/myServiceImpl.java.vm");
        } else {
            templateConfig.setServiceImpl(null);
        }
        if (StringUtils.isNullOrEmpty(isMapperGenerate) || isMapperGenerate.equals("true")) {
            templateConfig.setMapper("/template/myMapper.java.vm");
        } else {
            templateConfig.setMapper(null);
        }

        mpg.setTemplate(templateConfig);
    }

    /**
     * 进行xml 文件输出配置
     *
     * @param properties
     * @param fileOutConfigs
     * @return void
     * @author zhangxianchao
     * @since 2018/10/8 0008
     */
    private static void setXmlOutPutConfig(Properties properties, List<FileOutConfig> fileOutConfigs) {
        String mapperXmlPackage = properties.getProperty("output.defineChildPackage.mapperXml");
        final String mapperXmlPackageStatic = mapperXmlPackage == null ? "mapper" :
                mapperXmlPackage;

        String moduleName = properties.getProperty(OUTPUT_MODULE_NAME);
        String moduleNameDir = moduleName.replaceAll("\\.", "/");
        //文件路径
        String outputPath = properties.getProperty(OUTPUT_PATH);
        File file = new File(outputPath);
        String path = file.getAbsolutePath();

        FileOutConfig xmlFileConfig = new FileOutConfig("/template/myMapper" + ".xml.vm") {
            /**
             * 输出文件
             *
             * @param tableInfo
             */
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/resources/" + mapperXmlPackageStatic.replaceAll("\\.", "/") + "/" +
                        moduleNameDir
                        + "/" + com.yonyou.cyx.function.utils
                        .common.StringUtils.firstCharToLowerCase(tableInfo
                                .getEntityName())
                        +
                        "Mapper" +
                        ".xml";
            }
        };

        String isMapperXmlGenerate = properties.getProperty("output.isGeneratePackage.mapperXml");
        if (StringUtils.isNullOrEmpty(isMapperXmlGenerate) || isMapperXmlGenerate.equals("true")) {
            fileOutConfigs.add(xmlFileConfig);
        }
    }

    /**
     * 进行xml 文件输出配置
     *
     * @param properties
     * @param fileOutConfigs
     * @return void
     * @author zhangxianchao
     * @since 2018/10/8 0008
     */
    private static void setDtoOutPutConfig(Properties properties, List<FileOutConfig> fileOutConfigs, AutoGenerator
            mpg) {
        String dtoPackage = properties.getProperty("output.defineChildPackage.dto");
        final String dtoPackageStatic = dtoPackage == null ? "bean.dto" : dtoPackage;

        String moduleName = properties.getProperty(OUTPUT_MODULE_NAME);
        String moduleNameDir = moduleName.replaceAll("\\.", "/");
        //文件路径
        String outputPath = properties.getProperty(OUTPUT_PATH);
        File file = new File(outputPath);
        String path = file.getAbsolutePath();

        FileOutConfig dtoFileConfig = new FileOutConfig("/template/myDto" + JAVA_VM_SUFFIX) {
            /**
             * 输出文件
             *
             * @param tableInfo
             */
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/main/java/" + mpg.getPackageInfo().getParent().replaceAll("\\.", "/") +
                        "/" + dtoPackageStatic.replaceAll("\\.", "/") + (moduleNameDir == null ? "" : "/" +
                        moduleNameDir) + "/" + tableInfo
                        .getEntityName().substring(0, tableInfo
                                .getEntityName().length() - 2) + "DTO.java";
            }
        };

        String isDtoGenerate = properties.getProperty("output.isGeneratePackage.dto");
        if (StringUtils.isNullOrEmpty(isDtoGenerate) || isDtoGenerate.equals("true")) {
            fileOutConfigs.add(dtoFileConfig);
        }
    }

    /**
     * 进行xml 文件输出配置
     *
     * @param properties
     * @param fileOutConfigs
     * @return void
     * @author zhangxianchao
     * @since 2018/10/8 0008
     */
    private static void setControllerTestConfig(Properties properties, List<FileOutConfig> fileOutConfigs, AutoGenerator
            mpg) {
        String controllerTestPackage = properties.getProperty("output.defineChildPackage.controllerTest");
        final String controllerTestPackageStatic = controllerTestPackage == null ? "controller" :
                controllerTestPackage;

        String moduleName = properties.getProperty(OUTPUT_MODULE_NAME);
        String moduleNameDir = moduleName.replaceAll("\\.", "/");
        //文件路径
        String outputPath = properties.getProperty(OUTPUT_PATH);
        File file = new File(outputPath);
        String path = file.getAbsolutePath();

        FileOutConfig controllerTestFileConfig = new FileOutConfig("/template/myControllerTest" + JAVA_VM_SUFFIX) {
            /**
             * 输出文件
             *
             * @param tableInfo
             */
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/test/java/" + mpg.getPackageInfo().getParent().replaceAll("\\.", "/") +
                        "/" + controllerTestPackageStatic.replaceAll("\\.", "/") + (moduleNameDir == null ? "" : "/"
                        + moduleNameDir) + "/" +
                        tableInfo
                                .getControllerName
                                        () + "Test.java";
            }
        };

        String isControllerTestGenerate = properties.getProperty("output.isGeneratePackage.controllerTest");
        if (StringUtils.isNullOrEmpty(isControllerTestGenerate) || isControllerTestGenerate.equals("true")) {
            fileOutConfigs.add(controllerTestFileConfig);
        }
    }


    /**
     * 进行xml 文件输出配置
     *
     * @param properties
     * @param fileOutConfigs
     * @return void
     * @author zhangxianchao
     * @since 2018/10/8 0008
     */
    private static void setServiceTestConfig(Properties properties, List<FileOutConfig> fileOutConfigs, AutoGenerator
            mpg) {
        String serviceTestPackage = properties.getProperty("output.defineChildPackage.serviceTest");
        final String serviceTestPackageStatic = serviceTestPackage == null ? "service" :
                serviceTestPackage;

        String moduleName = properties.getProperty(OUTPUT_MODULE_NAME);
        String moduleNameDir = moduleName.replaceAll("\\.", "/");
        //文件路径
        String outputPath = properties.getProperty(OUTPUT_PATH);
        File file = new File(outputPath);
        String path = file.getAbsolutePath();

        FileOutConfig serviceImplTestFileConfig = new FileOutConfig("/template/myServiceImplTest" + JAVA_VM_SUFFIX) {
            /**
             * 输出文件
             *
             * @param tableInfo
             */
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/src/test/java/" + mpg.getPackageInfo().getParent().replaceAll("\\.", "/") +
                        "/" + serviceTestPackageStatic.replaceAll("\\.", "/") + (moduleNameDir == null ? "" : "/" +
                        moduleNameDir) + "/" +
                        tableInfo.getServiceImplName() + "Test.java";
            }
        };

        String isServiceTestGenerate = properties.getProperty("output.isGeneratePackage.serviceTest");
        if (StringUtils.isNullOrEmpty(isServiceTestGenerate) || isServiceTestGenerate.equals("true")) {
            fileOutConfigs.add(serviceImplTestFileConfig);
        }
    }


}