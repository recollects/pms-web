package com.ls.pms.web.dal.conf;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author yejd
 * @date 2023-04-19 16:15
 * @description
 */
public class ReadWriteSplitRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getDbType();
    }
}
