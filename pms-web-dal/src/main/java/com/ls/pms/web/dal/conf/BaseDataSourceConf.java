package com.ls.pms.web.dal.conf;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;

import java.sql.SQLException;
import java.util.Collections;

/**
 * @author yejd
 * @date 2023-04-19 16:10
 * @description
 */
public class BaseDataSourceConf {

    private final static String MYSQL_SUFFIX = "?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&allowMultiQueries=true&tinyInt1isBit=false&useSSL=false";

    @Value("${mysql.jdbc.initialSize}")
    private int initSize;

    @Value("${mysql.jdbc.maxActive}")
    private int maxActive;

    @Value("${mysql.jdbc.minIdle}")
    private int minIdle;

    protected DruidDataSource buildDataSource(ConnConf connConf) throws SQLException {
        try (DruidDataSource datasource = new DruidDataSource()) {
            datasource.setUrl(connConf.getJdbcUrl() + MYSQL_SUFFIX);
            datasource.setUsername(connConf.getUsername());
            datasource.setPassword(connConf.getPassword());
            //配置连接池的初始化大小，最大值，最小值
            datasource.setInitialSize(initSize);
            datasource.setMaxActive(maxActive);
            datasource.setMinIdle(minIdle);
            //配置获取连接等待超时的时间
            datasource.setMaxWait(5000);
            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            datasource.setTimeBetweenEvictionRunsMillis(60000);
            //配置一个连接在池中最小生存的时间，单位是毫秒
            datasource.setMinEvictableIdleTimeMillis(300000);
            //推荐的配置
            datasource.setValidationQuery("SELECT 'x'");//用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
            datasource.setTestOnBorrow(false);//申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
            datasource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
            datasource.setTestOnReturn(false);//归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
            datasource.setPoolPreparedStatements(false);//是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭
            datasource.setMaxPoolPreparedStatementPerConnectionSize(-1);//要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
            //慢sql的记录
            datasource.setFilters("stat");
            StatFilter statFilter = new StatFilter();
            statFilter.setSlowSqlMillis(50);
            statFilter.setLogSlowSql(true);
            datasource.setProxyFilters(Collections.singletonList(statFilter));
            return datasource;
        }
    }
}
