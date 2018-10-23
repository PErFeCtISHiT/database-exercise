package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.CallRecordRepository;
import project.dao.FlowRecordRepository;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 11:23 2018/10/23
 */
@Service
@Transactional
public class FlowRecordService extends PublicService{
    private FlowRecordRepository flowRecordRepository;
    @Autowired
    public FlowRecordService(FlowRecordRepository flowRecordRepository) {
        this.repository = flowRecordRepository;
        this.flowRecordRepository = flowRecordRepository;
    }
}
