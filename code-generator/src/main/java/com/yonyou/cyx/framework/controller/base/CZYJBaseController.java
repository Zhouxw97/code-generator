package com.yonyou.cyx.framework.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yonyou.cyx.framework.bean.dto.framework.RestResultResponse;
import com.yonyou.cyx.framework.service.CZYJBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 基础controller
 *
 * 默认实现增删改查
 *
 * @author BENJAMIN
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class CZYJBaseController<Service extends CZYJBaseService,Entity> {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected Service baseService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public RestResultResponse<Entity> add(@RequestBody Entity entity){
        baseService.insert(entity);
        return new RestResultResponse<Entity>().success(true);
    }


    @RequestMapping(value = "query",method = RequestMethod.GET)
    @ResponseBody
    public RestResultResponse<Entity> getByEntity(@RequestParam Map<String, Object> params){
        return new RestResultResponse<Entity>().success(true).data(baseService.selectByMap(params));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public RestResultResponse<Entity> get(@PathVariable String id) {
        Long tid = null;
        if (!StringUtils.isEmpty(id)) {
            tid = Long.valueOf(id);
        }
        return new RestResultResponse<Entity>().success(true).data(baseService.selectById(tid));
    }

    @RequestMapping(value = "/",method = RequestMethod.PUT)
    @ResponseBody
    public RestResultResponse<Entity> update(@RequestBody Entity entity){
        baseService.updateById(entity);
        return new RestResultResponse<Entity>().success(true);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public RestResultResponse<Entity> remove(@PathVariable String id) {
        Long tid = null;
        if (!StringUtils.isEmpty(id)) {
            tid = Long.valueOf(id);
        }
        baseService.deleteById(tid);
        return new RestResultResponse<Entity>().success(true);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public RestResultResponse<List<Entity>> all(){
        return new RestResultResponse<Entity>().success(true).data(baseService.selectListAll());
    }

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public IPage<Entity> list(@RequestParam(required=false) Entity entity,
                              @RequestParam(required=false) int currentPage,@RequestParam(required=false) int pageSize){
        //查询列表数据
        Page<Entity> page = new Page(currentPage, pageSize);
        QueryWrapper<Entity> query = new QueryWrapper<Entity>();
        return baseService.selectPage(page,query);
    }

    public String getCurrentUserName(){
        String authorization = request.getHeader("Authorization");
        return new String(Base64Utils.decodeFromString(authorization));
    }
}
