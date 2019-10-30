package com.yonyou.cyx.function.utils.resource;

import com.yonyou.cyx.function.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.Properties;

/**
 * 提供YAML 操作的公共类
 *
 * @author zhangxianchao
 * @since 2018/7/28 0028
 */
public class YamlUtils {
    private static final Logger logger = LoggerFactory.getLogger(YamlUtils.class);

    private YamlUtils() {
    }

    /**
     * 解析yaml 文件，并返回MAP 类型
     *
     * @param yamlSource
     * @return java.util.Map<java.lang.String , java.lang.Object>
     * @author zhangxianchao
     * @since 2018/7/28 0028
     */
    public static Map<String, Object> yaml2Map(String yamlSource) {
        try {
            YamlMapFactoryBean yaml = new YamlMapFactoryBean();
            yaml.setResources(new ClassPathResource(yamlSource));
            return yaml.getObject();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UtilException("yaml解悉错误", e);
        }
    }

    /**
     * 解悉yaml 文件，并解悉成properties格式
     *
     * @param yamlSource
     * @return java.util.Properties
     * @author zhangxianchao
     * @since 2018/7/28 0028
     */
    public static Properties yaml2Properties(String yamlSource) {
        try {
            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(new ClassPathResource(yamlSource));
            return yaml.getObject();
        } catch (Exception e) {
            throw new UtilException("yaml解悉错误", e);
        }
    }
}