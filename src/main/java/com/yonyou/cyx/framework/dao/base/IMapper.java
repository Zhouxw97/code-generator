package com.yonyou.cyx.framework.dao.base;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 演示 mapper 父类，注意这个类不要让 mp 扫描到！！
 */
public interface IMapper<T> extends BaseMapper<T> {

    /**
     * 分页查询对应数据
     *
     * @param page
     * @param queryCondition
     * @return com.baomidou.mybatisplus.core.metadata.IPage<T>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    List<T> selectPageBySql(Page page, @Param("params") T queryCondition);

    /**
     * 根据查询条件返回结果集
     *
     * @param queryCondition
     * @return java.util.List<T>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    List<T> selectListBySql(@Param("params") T queryCondition);

}
