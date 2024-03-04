package vladislavmaltsev.paymenttaskapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vladislavmaltsev.paymenttaskapi.dto.PaymentDTO;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.repository.UserRepository;
import vladislavmaltsev.paymenttaskapi.service.serviceutil.AuthenticationRequest;
import vladislavmaltsev.paymenttaskapi.service.serviceutil.AuthenticationResponse;
import vladislavmaltsev.paymenttaskapi.service.serviceutil.RegisterRequest;
import vladislavmaltsev.paymenttaskapi.util.Role;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final PaymentService paymentService;
    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .name(registerRequest.getName())
                .pass(passwordEncoder.encode(registerRequest.getPass()))
                .role(Role.USER)
                .build();
        if (userRepository.findByName(registerRequest.getName()).isPresent())
            return AuthenticationResponse.builder().build();
        userRepository.save(user);
        var paymentDTO = PaymentDTO.builder()
                .amount(new BigDecimal("8"))
                .user(user)
                .date(new Date())
                .build();
        paymentService.savePayment(paymentDTO);
        return AuthenticationResponse.builder()
                .token(jwtTokenService.generateToken(user))
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
        var token = jwtTokenService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
