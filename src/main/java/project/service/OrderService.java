package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.OrderRepository;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 16:02 2018/10/12
 */
@Service
@Transactional
public class OrderService extends PublicService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.repository = orderRepository;
        this.orderRepository = orderRepository;
    }
}
