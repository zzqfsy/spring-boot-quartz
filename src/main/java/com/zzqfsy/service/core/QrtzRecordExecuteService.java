package com.zzqfsy.service.core;

import com.zzqfsy.service.core.domain.QrtzRecordExecute;
import com.zzqfsy.service.core.repository.QrtzRecordExecuteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 17-1-3.
 */
@Service
public class QrtzRecordExecuteService {
    @Autowired
    private QrtzRecordExecuteMapper qrtzRecordExecuteMapper;

    public QrtzRecordExecute getByPrimaryKey(Integer id) {
        return qrtzRecordExecuteMapper.getByPrimaryKey(id);
    }

    public void create(QrtzRecordExecute qrtzRecordExecute) {
        qrtzRecordExecuteMapper.create(qrtzRecordExecute);
    }

    public int count(Map<String, Object> params) {
        return qrtzRecordExecuteMapper.count(params);
    }

    public List<QrtzRecordExecute> getList(Map<String, Object> params) {
        return qrtzRecordExecuteMapper.getList(params);
    }
}
