package vladislavmaltsev.paymenttaskapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vladislavmaltsev.paymenttaskapi.controller.AuthenticationRequest;
import vladislavmaltsev.paymenttaskapi.controller.AuthenticationResponse;
import vladislavmaltsev.paymenttaskapi.controller.RegisterRequest;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.repository.UserPaymentRepository;
import vladislavmaltsev.paymenttaskapi.util.Role;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserPaymentRepository userPaymentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTotenService jwtTotenService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .name(registerRequest.getName())
                .usd(new BigDecimal("8"))
                .pass(passwordEncoder.encode(registerRequest.getPass()))
                .role(Role.USER)
                .build();
        userPaymentRepository.save(user);
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
        var user = userPaymentRepository.findByName(authenticationRequest.getName()).orElseThrow();
        var token = jwtTotenService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
