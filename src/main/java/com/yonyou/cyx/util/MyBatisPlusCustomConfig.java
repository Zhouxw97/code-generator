package com.yonyou.cyx.util;

/**
 * 设定生成Mybatis Plus 时的自定义属性
 *
 * @author: zhangxianchao
 * @since: 2018/12/24 0024
 * @updateDate:
 * @updateRemark:
 * @version:1.0 Copyright: Copyright (c) 2018
 */
public class MyBatisPlusCustomConfig {

    /**
     * 设置父类实体字段
     */
    private String[] superEntityColumns;
    /**
     * 设置父类实体类
     */
    private String superEntityClass;
    /**
     * 设置父类Mapper 类
     */
    private String superMapperClass;
    /**
     * 设置父类Controller 类
     */
    private String superControllerClass;
    /**
     * 设置逻辑删除字段
     */
    private String logicDeleteFieldName;

    /**
     * 设置逻辑删除字段
     */
    private String versionFieldName;
    /**
     * 设计删除boolean 类型前缀
     */
    private boolean entityBooleanColumnRemoveIsPrefix;

    public String[] getSuperEntityColumns() {
        return superEntityColumns;
    }

    public void setSuperEntityColumns(String... superEntityColumns) {
        this.superEntityColumns = superEntityColumns;
    }

    public String getSuperEntityClass() {
        return superEntityClass;
    }

    public void setSuperEntityClass(String superEntityClass) {
        this.superEntityClass = superEntityClass;
    }

    public String getSuperMapperClass() {
        return superMapperClass;
    }

    public void setSuperMapperClass(String superMapperClass) {
        this.superMapperClass = superMapperClass;
    }

    public String getSuperControllerClass() {
        return superControllerClass;
    }

    public void setSuperControllerClass(String superControllerClass) {
        this.superControllerClass = superControllerClass;
    }

    public String getLogicDeleteFieldName() {
        return logicDeleteFieldName;
    }

    public void setLogicDeleteFieldName(String logicDeleteFieldName) {
        this.logicDeleteFieldName = logicDeleteFieldName;
    }

    public boolean isEntityBooleanColumnRemoveIsPrefix() {
        return entityBooleanColumnRemoveIsPrefix;
    }

    public void setEntityBooleanColumnRemoveIsPrefix(boolean entityBooleanColumnRemoveIsPrefix) {
        this.entityBooleanColumnRemoveIsPrefix = entityBooleanColumnRemoveIsPrefix;
    }


    public String getVersionFieldName() {
        return versionFieldName;
    }

    public void setVersionFieldName(String versionFieldName) {
        this.versionFieldName = versionFieldName;
    }
}
