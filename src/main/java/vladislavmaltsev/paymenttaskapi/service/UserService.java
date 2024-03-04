package vladislavmaltsev.paymenttaskapi.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vladislavmaltsev.paymenttaskapi.dto.PaymentDTO;
import vladislavmaltsev.paymenttaskapi.dto.UserDTO;
import vladislavmaltsev.paymenttaskapi.repository.UserRepository;

import java.math.BigDecimal;
import java.util.*;

import static vladislavmaltsev.paymenttaskapi.util.MappingDTOClass.mapDTOAndClass;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final JwtTokenService jwtTokenService;


    public PaymentDTO subtractAmountFromUser(String name) {
        UserDTO userDTO =
                mapDTOAndClass(
                        userRepository.findByName(name).orElseThrow(() -> new NoSuchElementException(name + " does not exists")),
                        UserDTO.class).orElseThrow();
        var paymentDto = paymentService.getPayment(userDTO.getName());
        paymentDto.setAmount(paymentDto.getAmount().subtract(new BigDecimal("1.1")));
        paymentDto.setDate(new Date());
        return paymentService.savePayment(paymentDto);
    }
    @Transactional
    public PaymentDTO subtractAmountFromUser(HttpServletRequest request) {
        var userName = jwtTokenService.getUserNameFromToken(request.getHeader("Authorization").substring(7));
       return subtractAmountFromUser(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        String.valueOf(user.getUsername()),
                        user.getPass(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new NoSuchElementException(username + " does not exists"));
    }
}
