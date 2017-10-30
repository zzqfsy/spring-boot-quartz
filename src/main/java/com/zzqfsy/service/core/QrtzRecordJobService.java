package com.zzqfsy.service.core;

import com.zzqfsy.service.core.domain.QrtzRecordJob;
import com.zzqfsy.service.core.repository.QrtzRecordJobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 17-1-3.
 */
@Service
public class QrtzRecordJobService {
    @Autowired
    private QrtzRecordJobMapper qrtzRecordJobMapper;

    public QrtzRecordJob getByPrimaryKey(Integer id) {
        return qrtzRecordJobMapper.getByPrimaryKey(id);
    }

    public void create(QrtzRecordJob qrtzRecordJob) {
        qrtzRecordJobMapper.create(qrtzRecordJob);
    }

    public int count(Map<String, Object> params) {
        return qrtzRecordJobMapper.count(params);
    }

    public List<QrtzRecordJob> getList(Map<String, Object> params) {
        return qrtzRecordJobMapper.getList(params);
    }
}
