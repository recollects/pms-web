package com.ls.pms.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yejd
 * @date 2023-04-19 15:54
 * @description
 */
@RestController
@RequestMapping
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "success";
    }

}
