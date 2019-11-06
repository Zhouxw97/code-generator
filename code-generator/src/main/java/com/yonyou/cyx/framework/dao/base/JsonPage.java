/*
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
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
package com.yonyou.cyx.framework.dao.base;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 由于原始的PAGE 对象无法反序列化，则新生成一个对象
 * </p>
 *
 * @author hubin
 * @since 2018-06-09
 */
public class JsonPage<T> {

    /**
     * 查询数据列表
     */
    private List<T> records = new ArrayList<>();

    /**
     * 总数，当 total 为 null 或者大于 0 分页插件不在查询总数
     */
    private Long total = 0L;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    private long pages;
    /**
     * 当前页
     */
    private long current = 1;
    /**
     * <p>
     * 自动优化 COUNT SQL
     * </p>
     */
    private boolean optimizeCountSql = true;
    /**
     * <p>
     * 集合模式，不分页返回集合结果
     * </p>
     */
    private boolean listMode = false;

    public JsonPage() {
        // to do nothing
    }
    /**
     * <p>
     * 分页构造函数
     * </p>
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public JsonPage(long current, long size) {
        this(current, size, 0L);
    }


    public JsonPage(long current, long size, Long total) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    /**
     * <p>
     * 存在上一页
     * </p>
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * <p>
     * 存在下一页
     * </p>
     *
     * @return true / false
     */

    public List<T> getRecords() {
        return this.records;
    }

    public JsonPage<T> setRecords(List<T> records) {
        this.records.addAll(records);
        return this;
    }

    public Long getTotal() {
        return this.total;
    }

    public JsonPage<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    public long getSize() {
        return this.size;
    }

    public JsonPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public long getCurrent() {
        return this.current;
    }

    public JsonPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    public void setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
    }

    public boolean listMode() {
        return listMode;
    }

    public void setListMode(boolean listMode) {
        this.listMode = listMode;
    }
}
