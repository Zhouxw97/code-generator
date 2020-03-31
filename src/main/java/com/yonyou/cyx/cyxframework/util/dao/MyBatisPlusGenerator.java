package com.yonyou.cyx.cyxframework.util.dao;

import com.yonyou.cyx.function.utils.CodeFormater;
import com.yonyou.cyx.function.utils.FileUtil;
import com.yonyou.cyx.function.utils.common.CommonUtils;
import com.yonyou.cyx.function.utils.common.StringUtils;
import com.yonyou.cyx.function.utils.resource.YamlUtils;

import java.util.ArrayList;
import java.util.List;
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
    public static void generate() {
        Properties properties = YamlUtils.yaml2Properties("generator.yaml");

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

        String javaFormat = properties.getProperty("output.javaFormat");
        String xmlFormat = properties.getProperty("output.xmlFormat");
        boolean jf = !StringUtils.isBlank(javaFormat) && "true".equals(javaFormat);
        boolean xf = !StringUtils.isBlank(xmlFormat) && "true".equals(xmlFormat);

        //获取代码生成路径
        String outputPath = properties.getProperty("output.path") + "/src";

        //格式化代码生成路径中的文件
        CodeFormater codeFormater = new CodeFormater();
        List<String> fileList = FileUtil.getFileList(outputPath, new ArrayList<>());
        if (!CommonUtils.isNullOrEmpty(fileList)) {
            fileList.forEach(r -> codeFormater.formatFile(r, jf, xf));
        }
    }
}