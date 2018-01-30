package com.whb.db.datasource;

import java.util.Random;

/**
 * @author whb
 * @date 2018年1月30日 下午8:23:22 
 * @Description: 数据源预处理
 */
public class DataSourceContextHolder {
	
	public static final String MASTER_DATABASE_NAME = "masterDataSource";
	public static final String SLAVE1_DATABASE_NAME = "slave1DataSource";
	public static final String SLAVE2_DATABASE_NAME = "slave2DataSource";

    public enum DbType {
        MASTER, SLAVE1, SLAVE2
    }

    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<>();

    public static void setDbType(DbType dbType) {
        if (dbType == null) throw new NullPointerException();
        contextHolder.set(dbType);
    }

    public static DbType getDbType() {
        return contextHolder.get() == null ? DbType.MASTER : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }

    private static final Random random = new Random();

    public static DbType getSlaveRandom() {
        DbType[] dbTypes = new DbType[]{DbType.SLAVE1, DbType.SLAVE2};
        return dbTypes[random.nextInt(dbTypes.length)];
    }

}
