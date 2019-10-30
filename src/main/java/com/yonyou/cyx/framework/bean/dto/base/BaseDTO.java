package com.yonyou.cyx.framework.bean.dto.base;

import com.yonyou.cyx.framework.bean.entity.base.BasePO;
import com.yonyou.cyx.function.utils.bean.BeanMapperUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * //实现DTO 的父类
 *
 * @param
 * @author zhangxianchao
 * @return
 * @since 2018/7/22 0022
 */
public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 将DTO 转换为PO
     *
     * @param poClass
     * @return T
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    protected <T extends BasePO> T transDtoToPo(Class<T> poClass) {
        return BeanMapperUtil.copyProperties(this, poClass);
    }

    /**
     * 将DTO 转换为PO
     *
     * @return T
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    protected <T extends BasePO> void transDtoToPo(BaseDTO dto, T po) {
        BeanMapperUtil.copyProperties(dto, po);
    }

    /**
     * 定义赋值的抽象方法，以便子类继承并实现此方法
     */
    protected <T extends BasePO> void populateValueByMethod(T po) {
    }

    /**
     * 定义赋值的抽象方法，以便子类继承并实现此方法
     */
    public Map<String, Object> toMaps() {
        return BeanMapperUtil.toMap(this);
    }

}
