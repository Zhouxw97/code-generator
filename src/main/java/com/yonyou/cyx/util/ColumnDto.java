package com.yonyou.cyx.util;

/**
 * @description:
 * @author: lijun
 * @create: 2020-05-15 18:21:41
 **/
public class ColumnDto {
    private Boolean isNullable;
    private Long maxLength;

    public ColumnDto() {
    }

    public ColumnDto(Boolean isNullable, Long maxLength) {
        this.isNullable = isNullable;
        this.maxLength = maxLength;
    }

    public Boolean getNullable() {
        return isNullable;
    }

    public void setNullable(Boolean nullable) {
        isNullable = nullable;
    }

    public Long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Long maxLength) {
        this.maxLength = maxLength;
    }
}
