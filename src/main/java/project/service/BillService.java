package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.BillRepostiory;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 13:58 2018/10/13
 */
@Service
@Transactional
public class BillService extends PublicService{
    private final BillRepostiory billRepostiory;

    @Autowired
    public BillService(BillRepostiory billRepostiory) {
        this.repository = billRepostiory;
        this.billRepostiory = billRepostiory;
    }
}
