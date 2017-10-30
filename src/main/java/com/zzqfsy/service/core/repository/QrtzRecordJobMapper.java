package com.zzqfsy.service.core.repository;

import com.zzqfsy.config.mysql.mutil.TargetDataSource;
import com.zzqfsy.service.core.domain.QrtzRecordJob;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 17-1-3.
 */
public interface QrtzRecordJobMapper {
    @TargetDataSource(name = TargetDataSource.quartzDataSource)
    public QrtzRecordJob getByPrimaryKey(Integer id);

    @TargetDataSource(name = TargetDataSource.quartzDataSource)
    public void create(QrtzRecordJob qrtzRecordJob);

    @TargetDataSource(name = TargetDataSource.quartzDataSource)
    public int count(Map<String, Object> params);

    @TargetDataSource(name = TargetDataSource.quartzDataSource)
    public List<QrtzRecordJob> getList(Map<String, Object> params);
}
