package com.zzqfsy.service.core.repository;

import com.zzqfsy.config.mysql.mutil.TargetDataSource;
import com.zzqfsy.service.core.domain.QrtzRecordExecute;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 17-1-3.
 */
public interface QrtzRecordExecuteMapper {
    @TargetDataSource(name = TargetDataSource.quartzDataSource)
    public QrtzRecordExecute getByPrimaryKey(Integer id);

    @TargetDataSource(name = TargetDataSource.quartzDataSource)
    public void create(QrtzRecordExecute qrtzRecordExecute);

    @TargetDataSource(name = TargetDataSource.quartzDataSource)
    public int count(Map<String, Object> params);

    @TargetDataSource(name = TargetDataSource.quartzDataSource)
    public List<QrtzRecordExecute> getList(Map<String, Object> params);
}
