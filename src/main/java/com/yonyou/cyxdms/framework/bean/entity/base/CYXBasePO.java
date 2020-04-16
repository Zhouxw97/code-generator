package com.yonyou.cyxdms.framework.bean.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.yonyou.cyx.framework.bean.entity.base.BasePO;

import java.time.LocalDateTime;

/**
 * //所有PO 的基类
 *
 * @author zhangxianchao
 * @since 2018/7/22 0022
 */
public abstract class CYXBasePO<T extends Model> extends BasePO<T> {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_dc_change_data_content.CREATED_BY
     *
     * @mbggenerated Wed Jul 18 20:48:49 CST 2018
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_dc_change_data_content.CREATED_TIME
     *
     * @mbggenerated Wed Jul 18 20:48:49 CST 2018
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_dc_change_data_content.UPDATED_BY
     *
     * @mbggenerated Wed Jul 18 20:48:49 CST 2018
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_dc_change_data_content.UPDATED_TIME
     *
     * @mbggenerated Wed Jul 18 20:48:49 CST 2018
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tt_dc_change_data_content.RECORD_VERSION
     *
     * @mbggenerated Wed Jul 18 20:48:49 CST 2018
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Version
    private Integer recordVersion;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }
}