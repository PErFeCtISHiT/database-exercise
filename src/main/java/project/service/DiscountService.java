package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.DiscountRepository;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 16:03 2018/10/12
 */
@Service
@Transactional
public class DiscountService extends PublicService{
    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.repository = discountRepository;
        this.discountRepository = discountRepository;
    }
}
