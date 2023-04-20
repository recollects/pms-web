package com.ls.pms.web.dal.conf;

import lombok.Builder;
import lombok.Data;

/**
 * @author yejd
 * @date 2023-04-19 16:10
 * @description
 */
@Data
@Builder
public class ConnConf {
    private String jdbcUrl;
    private String username;
    private String password;
}
