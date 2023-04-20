package com.ls.pms.web.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ls.pms.web.UserAPI;

/**
 * @author yejd
 * @date 2023-04-20 09:25
 * @description
 */
@Service
public class UserAPIImpl implements UserAPI {

    @Override
    public Boolean login(String userId, String pwd) {
        return Boolean.TRUE;
    }
}
