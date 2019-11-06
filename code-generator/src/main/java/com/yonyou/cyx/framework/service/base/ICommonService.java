package com.yonyou.cyx.framework.service.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yonyou.cyx.framework.bean.dto.base.BaseDTO;
import com.yonyou.cyx.framework.bean.entity.base.BasePO;

import java.util.Collections;
import java.util.List;

/**
 * 定义Service 的公用方法
 *
 * @author zhangxianchao
 * @since 2018/7/22 0022
 */
public interface ICommonService<T2 extends BaseDTO,T extends BasePO,T3>  extends IService<T> {

    /**
     * 分页查询对应数据
     *
     * @param page
     * @param queryCondition
     * @return com.baomidou.mybatisplus.core.metadata.IPage<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    default IPage<T2> selectPageBysql(Page page, T2 queryCondition){
        return new Page();
    }

    /**
     * 根据查询条件返回结果集
     *
     * @param queryCondition
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    default List<T2> selectListBySql(T2 queryCondition){
        return Collections.emptyList();
    }

    /**
     * 根据查询条件返回结果集
     *
     * @param id
     * @return Map<String, Object>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    default T2 getById(T3 id){
        return null;
    }


    /**
     * 根据DTO 进行数据新增
     *
     * @param t
     * @return void
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    default int insert(T2 t){
        return 0;
    }

    /**
     * 根据DTO 及ID 进行数据更新
     *
     * @param id
     * @param t
     * @return void
     * @throws
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    default int update(T3 id, T2 t){
        return 0;
    }

    /**
     * 根据ID 进行删除数据
     *
     * @param id
     * @return void
     * @author zhangxianchao
     * @since 2018/8/25 0025
     */
    default int deleteById(T3 id){return 0;}


    /**
     * 根据ids 进行删除
     *
     * @param ids
     * @return void
     * @author zhangxianchao
     * @since 2018/8/25 0025
     */
    default int deleteBatchIds(String ids){return 0;}

}
