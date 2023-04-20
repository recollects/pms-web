package com.ls.pms.web;

/**
 * @author yejd
 * @date 2023-04-20 09:25
 * @description
 */
public interface UserAPI {

    /**
     *
     * @param userId
     * @param pwd
     */
    Boolean login(String userId,String pwd);
}
