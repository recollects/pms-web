package com.ls.pms.web.api.impl;

import com.ls.pms.web.BaseTest;
import com.ls.pms.web.UserAPI;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author yejd
 * @date 2023-04-20 09:26
 * @description
 */
public class UserAPIImplTest extends BaseTest {

    @Resource
    private UserAPI userAPI;

    @Test
    public void testLogin() {
        //初始数据

        //调用接口 case1
        Boolean result = userAPI.login("", "");
        Assert.assertTrue(!result);

        //调用接口 case2
        result = userAPI.login("", "");
        Assert.assertTrue(result);
        //....
    }
}
