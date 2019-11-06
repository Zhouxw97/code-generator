package com.yonyou.cyx.framework.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 基础业务处理类
 * 
 * @author BENJAMIN
 *
 * @param <M>
 * @param <T>
 */
public abstract class CZYJBaseService<M extends BaseMapper<T>, T> {

    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    /**
     * 查询单个
     * 
     * @param entity
     * @return
     */
    public T selectOne(Wrapper<T> entity) {
        return mapper.selectOne(entity);
    }


    /**
     * 根据主键查询
     * 
     * @param id
     * @return
     */
    public T selectById(Serializable id) {
        return mapper.selectById(id);
    }


    /**
     * 根据entity查询list
     * 
     * @param entity
     * @return
     */
    public List<T> selectList(Wrapper<T> entity) {
        return mapper.selectList(entity);
    }

    public List<T> selectByMap(Map<String, Object> entity) {
        return mapper.selectByMap(entity);
    }

    public List<T> selectListAll() {
        return mapper.selectList(null);
    }


    public Long selectCount(Wrapper<T> entity) {
        return Long.valueOf(mapper.selectCount(entity));
    }


    public void insert(T entity) {
        mapper.insert(entity);
    }

    public void delete(Wrapper<T> entity) {
        mapper.delete(entity);
    }


    public void deleteById(Serializable id) {
        mapper.deleteById(id);
    }


    public void updateById(T entity) {
        mapper.updateById(entity);
    }

    /**
     * 分页查
     * 
     * @param query
     * @return
     */
    @SuppressWarnings("unchecked")
	public IPage<T> selectPage(IPage<T> page,Wrapper<T>  query) {
        return mapper.selectPage(page,query);
    }

}
