package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.BillRepository;
import project.dao.CallRecordRepository;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:23 2018/10/23
 */
@Service
@Transactional
public class CallRecordService extends PublicService{
    private CallRecordRepository callRecordRepository;
    @Autowired
    public CallRecordService(CallRecordRepository callRecordRepository) {
        this.repository = callRecordRepository;
        this.callRecordRepository = callRecordRepository;
    }
}
