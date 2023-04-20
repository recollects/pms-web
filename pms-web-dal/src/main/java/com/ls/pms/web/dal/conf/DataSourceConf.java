package com.ls.pms.web.dal.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author yejd
 * @date 2023-04-19 16:12
 * @description
 */
@Configuration
public class DataSourceConf {

    /**
     * 写KEY
     */
    @Value("${mysql.jdbc.write.url}")
    private String jdbcUrl;
    @Value("${mysql.jdbc.write.username}")
    private String username;
    @Value("${mysql.jdbc.write.password}")
    private String password;

    /**
     * 只读KEY
     */
    @Value("${mysql.jdbc.read.url}")
    private String readJdbcUrl;
    @Value("${mysql.jdbc.read.username}")
    private String readUsername;
    @Value("${mysql.jdbc.read.password}")
    private String readPassword;

    public ConnConf getWriteConf() {
        return ConnConf.builder().jdbcUrl(jdbcUrl).password(password).username(username).build();
    }

    public ConnConf getReadConf() {
        return ConnConf.builder().jdbcUrl(readJdbcUrl).password(readPassword).username(readUsername).build();
    }

}
