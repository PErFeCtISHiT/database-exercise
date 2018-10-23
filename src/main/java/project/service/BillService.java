package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.BillRepository;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 13:58 2018/10/13
 */
@Service
@Transactional
public class BillService extends PublicService{
    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.repository = billRepository;
        this.billRepository = billRepository;
    }
}
