package com.ls.pms.web.dal.conf;

/**
 * @author yejd
 * @date 2023-04-19 16:13
 * @description
 */
public class DataSourceContext {
    public enum DbType {
        MASTER,
        SLAVE
    }

    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<>();

    public static void setDbType(DbType dbType) {
        if(dbType == null){
            throw new NullPointerException();
        }
        contextHolder.set(dbType);
    }

    public static DbType getDbType() {
        return contextHolder.get() == null ? DbType.MASTER : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
