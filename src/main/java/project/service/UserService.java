package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.UserRepository;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 16:00 2018/10/12
 */
@Service
public class UserService extends PublicService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
        this.userRepository = userRepository;
    }
}
