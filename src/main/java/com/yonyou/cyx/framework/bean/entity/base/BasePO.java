package com.yonyou.cyx.framework.bean.entity.base;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.yonyou.cyx.framework.bean.dto.base.BaseDTO;
import com.yonyou.cyx.function.utils.bean.BeanMapperUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * //所有PO 的基类
 *
 * @author zhangxianchao
 * @since 2018/7/22 0022
 */
public abstract class BasePO<T extends Model> extends Model<T> {

    @Override
    protected Serializable pkVal() {
        return null;
    }

    /**
     * 将PO 信息转化为DTO
     *
     * @param dtoClass 需要转化的DTO 信息
     * @return T
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    protected <T extends BaseDTO> T transDtoToPo(Class<T> dtoClass) {
        return BeanMapperUtil.copyProperties(this, dtoClass);
    }

    /**
     * 将PO 信息转化为DTO
     *
     * @return T
     * @author zhangxianchao
     * @since 2018/7/22 0022
     */
    protected <T extends BaseDTO> void transDtoToPo(BasePO po, T dto) {
        BeanMapperUtil.copyProperties(po, dto);
    }

    /**
     * 定义赋值的抽象方法，以便子类继承并实现此方法
     *
     * @param po
     * @return void
     * @author zhangxianchao
     * @since 2018/10/8 0008
     */
    protected <T extends BaseDTO> void populateValueByMethod(T po) {
    }

    /**
     * 定义赋值的抽象方法，以便子类继承并实现此方法
     */
    public Map<String, Object> toMaps() {
        return BeanMapperUtil.toMap(this);
    }

}
