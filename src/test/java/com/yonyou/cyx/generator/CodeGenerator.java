package com.yonyou.cyx.generator;

import com.yonyou.cyx.cyxframework.util.dao.MyBatisPlusGenerator;
import com.yonyou.cyx.function.utils.CodeFormater;
import com.yonyou.cyx.function.utils.FileUtil;
import com.yonyou.cyx.function.utils.common.CommonUtils;
import com.yonyou.cyx.function.utils.resource.YamlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 用于代码生成
 *
 * @author: zhangxianchao
 * @since: 2018/7/28 0028
 * @updateDate:
 * @updateRemark:
 * @version:1.0 Copyright: Copyright (c) 2018
 */
public class CodeGenerator {
    public static void main(String[] args) {
        //代码生成
        MyBatisPlusGenerator.generate();

        //获取代码生成路径
        Properties properties = YamlUtils.yaml2Properties("generator.yaml");
        String outputPath = properties.getProperty("output.path") + "/src";

        //格式化代码生成路径中.java文件
        CodeFormater codeFormater = new CodeFormater();
        List<String> fileList = FileUtil.getFileList(outputPath, new ArrayList<>());
        if (!CommonUtils.isNullOrEmpty(fileList)) {
            fileList.forEach(r -> codeFormater.formatFile(r));
        }
    }
}
