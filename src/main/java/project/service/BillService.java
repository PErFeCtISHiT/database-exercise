package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import project.dao.BillRepostiory;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 13:58 2018/10/13
 */
public class BillService extends PublicService{
    private final BillRepostiory billRepostiory;

    @Autowired
    public BillService(BillRepostiory billRepostiory) {
        this.repository = billRepostiory;
        this.billRepostiory = billRepostiory;
    }
}
