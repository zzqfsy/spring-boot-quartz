package com.zzqfsy.config.mysql.mutil;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by john on 16-6-6.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSource();
    }
}
