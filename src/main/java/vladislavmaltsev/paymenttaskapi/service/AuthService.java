package vladislavmaltsev.paymenttaskapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vladislavmaltsev.paymenttaskapi.dto.PaymentDTO;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.repository.UserRepository;
import vladislavmaltsev.paymenttaskapi.util.Role;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTotenService jwtTotenService;
    private final AuthenticationManager authenticationManager;
    private final PaymentService paymentService;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .name(registerRequest.getName())
//                .usd(new BigDecimal("8"))
                .pass(passwordEncoder.encode(registerRequest.getPass()))
                .role(Role.USER)
//                .paymentList(List.of(Payment.builder()
//                                .amount(new BigDecimal("8"))
//                                .date(new Date())
//                                .user()
//                        .build()))
                .build();
        if (userRepository.findByName(registerRequest.getName()).isPresent()) {
            return null;
        }
        userRepository.save(user);
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .amount(new BigDecimal("8"))
                .user(user)
                .date(new Date())
                .build();
        paymentService.savePayment(paymentDTO);

        var token = jwtTotenService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getName(),
                        authenticationRequest.getPass()
                )
        );
        var user = userRepository.findByName(authenticationRequest.getName()).orElseThrow();
        var token = jwtTotenService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
