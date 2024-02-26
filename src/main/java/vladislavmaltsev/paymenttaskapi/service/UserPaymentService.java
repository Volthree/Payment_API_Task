package vladislavmaltsev.paymenttaskapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.repository.UserPaymentRepository;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class UserPaymentService {

    private final UserPaymentRepository userPaymentRepository;

    public UserPaymentService(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }

    @Transactional
    public User getMoney(int id){
        User user = userPaymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id + "not exists"));
        user.setUsd(user.getUsd().subtract(new BigDecimal("1.1")));
        userPaymentRepository.save(user);
        return user;
    }
    @Transactional
    public User getUser(int id){
        return userPaymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id + "not exists"));
    }
}
