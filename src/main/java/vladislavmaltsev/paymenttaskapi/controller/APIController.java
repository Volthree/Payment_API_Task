package vladislavmaltsev.paymenttaskapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vladislavmaltsev.paymenttaskapi.dto.PaymentDTO;
import vladislavmaltsev.paymenttaskapi.service.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class APIController {
    private final UserService userService;
    private final AuthService authService;
    private final RestTemplate restTemplate;
    private final JwtTotenService jwtTotenService;
    private final SecurityContextLogoutHandler securityContextLogoutHandler;

    @PostMapping("/register")
    private ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        System.out.println("register");
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @GetMapping("/login")
    private ResponseEntity<String> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        System.out.println("Enter login ");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authService.authenticate(authenticationRequest).getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8080/api/payment/" + userService.loadUserByUsername(authenticationRequest.getName()).getUsername();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("End login");
        return response;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        jwtTotenService.invalidateToken(request.getHeader("Authorization"));
        securityContextLogoutHandler.logout(request, response, authentication);
        return "redirect:/login";
    }

    @GetMapping(value = "/payment/{name}")
    @ResponseBody
    public PaymentDTO payment(@PathVariable String name) {
        return userService.getMoney(name);
    }
}
