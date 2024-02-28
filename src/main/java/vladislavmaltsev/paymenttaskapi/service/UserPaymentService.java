package vladislavmaltsev.paymenttaskapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vladislavmaltsev.paymenttaskapi.dto.UserDTO;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.repository.UserPaymentRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static vladislavmaltsev.paymenttaskapi.util.MappingDTOClass.mapDTOAndClass;

@Service
public class UserPaymentService implements UserDetailsService {

    private final UserPaymentRepository userPaymentRepository;

    public UserPaymentService(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }

    @Transactional
    public UserDTO getMoney(String name) {
        System.out.println("Enter getMoney");
        UserDTO userDTO =
                mapDTOAndClass(
                        userPaymentRepository.findByName(name).orElseThrow(() -> new NoSuchElementException(name + " does not exists")),
                        UserDTO.class).orElseThrow();

        //logic
        userDTO.setUsd(userDTO.getUsd().subtract(new BigDecimal("1.1")));
        System.out.println("End getMoney");
        return save(userDTO).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Enter loadUserByUsername");
        var v = userPaymentRepository.findByName(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        String.valueOf(user.getUsername()),
                        user.getPass(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new NoSuchElementException(username + " does not exists"));
        System.out.println("End loadUserByUsername");
        return v;
    }

    public Optional<UserDTO> save(UserDTO userParametersDTO) {
        return Optional.ofNullable(
                        mapDTOAndClass(
                                userPaymentRepository.save(
                                        Objects.requireNonNull(mapDTOAndClass(userParametersDTO,
                                                User.class).orElseThrow())
                                ),
                                UserDTO.class))
                .orElseThrow();
    }
}
