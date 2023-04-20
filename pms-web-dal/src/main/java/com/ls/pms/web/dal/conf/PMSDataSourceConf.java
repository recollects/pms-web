package com.ls.pms.web.dal.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author yejd
 * @date 2023-04-19 16:16
 * @description
 */
@Configuration
@MapperScan(basePackages = "com.ls.pms.web.dal.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
@Slf4j
public class PMSDataSourceConf extends BaseDataSourceConf{

    @Autowired
    private DataSourceConf dataSourceConf;

    @Bean(name = "dataSource")
    @Primary
    @DependsOn({"writeDataSource", "readDataSource"})
    public ReadWriteSplitRoutingDataSource dataSource() throws Exception {
        log.info("-------------------- DataSource init ---------------------");
        ReadWriteSplitRoutingDataSource datasource = new ReadWriteSplitRoutingDataSource();
        Map<Object, Object> map = ImmutableMap.of(DataSourceContext.DbType.MASTER, writeDataSource(),
            DataSourceContext.DbType.SLAVE, readDataSource());
        datasource.setTargetDataSources(map);
        return datasource;
    }

    @Bean(name = "writeDataSource", initMethod = "init")
    public DruidDataSource writeDataSource() throws Exception {
        log.info("-------------------- writeDataSource init ---------------------");
        return buildDataSource(dataSourceConf.getWriteConf());
    }

    @Bean(name = "readDataSource", initMethod = "init")
    public DruidDataSource readDataSource() throws Exception {
        log.info("-------------------- readDataSource init ---------------------");
        return buildDataSource(dataSourceConf.getReadConf());
    }

    @Bean
    public DataSourceTransactionManager itemCenterTransactionManager() throws Exception {
        return new DataSourceTransactionManager(dataSource());
    }


    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactoryBean sqlSessionFactory(DataSource itemCenterDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(itemCenterDataSource);
        sessionFactory.setTypeAliasesPackage("com.ls.pms.web.dal.entity");
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "mapper/*Mapper*.xml";
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(packageSearchPath));
        return sessionFactory;
    }
}
