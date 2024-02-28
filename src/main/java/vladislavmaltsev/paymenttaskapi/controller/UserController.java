package vladislavmaltsev.paymenttaskapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vladislavmaltsev.paymenttaskapi.dto.UserDTO;
import vladislavmaltsev.paymenttaskapi.service.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task1")
public class UserController {
    private final UserPaymentService userPaymentService;
    private final AuthService authService;
    private final RestTemplate restTemplate;

    @PostMapping("/register")
    private ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ){
        System.out.println("register");
        return ResponseEntity.ok(authService.register(registerRequest));
    }
    @GetMapping("/authenticate")
    private ResponseEntity<String> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.authenticate(authenticationRequest).getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8080/api/payment/"+userPaymentService.loadUserByUsername(authenticationRequest.getName()).getUsername();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("authenticate");
        return response;
    }

    @PostMapping("/demo")
    private ResponseEntity<String> demo(
    ){
        return ResponseEntity.ok("Hello epta");
    }
}
