package com.yonyou.cyx.framework.test;

import com.yonyou.cyx.function.utils.common.CommonUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 测试基类
 *
 * @author: zhangxianchao
 * @since: 2018/7/24 0024
 * @updateDate:
 * @updateRemark:
 * @version:1.0 Copyright: Copyright (c) 2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional(rollbackFor = Exception.class)
@Rollback
public abstract class BaseTest {

    //注入mvc
    @Autowired
    protected MockMvc mvc;

    /**
     * 执行初始化
     *
     * @param
     * @return void
     * @throws
     * @author zhangxianchao
     * @since 2018/7/24 0024
     */
    @Before
    public void setUp() {
        if (CommonUtils.isNullOrEmpty(getTestDataList())) {
            insertTestData();
        }
    }

    /**
     * 插入数据
     *
     * @param
     * @return void
     * @throws
     * @author zhangxianchao
     * @since 2018/7/24 0024
     */
    public abstract void insertTestData();

    /**
     * 获取测试数据
     *
     * @param
     * @return java.util.List<java.lang.Object>
     * @throws
     * @author zhangxianchao
     * @since 2018/7/24 0024
     */
    public abstract List getTestDataList();

}
