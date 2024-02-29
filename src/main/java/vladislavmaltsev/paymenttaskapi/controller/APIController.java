package vladislavmaltsev.paymenttaskapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vladislavmaltsev.paymenttaskapi.service.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class APIController {
    private final UserService userService;
    private final AuthService authService;
    private final RestTemplate restTemplate;
    private final JwtTotenService jwtTotenService;
    private final SecurityContextLogoutHandler securityContextLogoutHandler;

    @PostMapping("/register")
    private ResponseEntity<String> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        System.out.println("register");
        var result = authService.register(registerRequest);
        if (result == null) {
            return ResponseEntity.ok("Already registered");
        }
        return ResponseEntity.ok("Registered token: " + result.getToken());
    }

    @GetMapping("/login")
    private ResponseEntity<String> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        System.out.println("Enter login ");
        HttpHeaders headers = new HttpHeaders();
        System.out.println("Bearer " + authService.authenticate(authenticationRequest).getToken());
//        headers.set("Authorization", "Bearer " + authService.authenticate(authenticationRequest).getToken());
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        String url = "http://localhost:8080/api/payment/" + userService.loadUserByUsername(authenticationRequest.getName()).getUsername();
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println("End login");
        return ResponseEntity.ok("Logged in");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
                         HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        jwtTotenService.invalidateToken(request.getHeader("Authorization").substring(7));
        securityContextLogoutHandler.logout(request, response, authentication);
        return ResponseEntity.ok("success");
    }

    //    @GetMapping(value = "/payment/{name}")
//    @ResponseBody
//    public  ResponseEntity<String> payment(@PathVariable String name) {
//        System.out.println("Enter payment");
//        userService.subtrackAmount(name);
//        return ResponseEntity.ok("Payment complete");
//    }
    @PostMapping("/payment")
    public ResponseEntity<String> payment(
            HttpServletRequest request
    ) {

        System.out.println("Enter payment");
//        System.out.println(authenticationRequest.getName());
        System.out.println(request.getHeader("Authorization"));
        return ResponseEntity.ok(userService.subtrackAmount(request).toString());
//        return ResponseEntity.ok("success");
    }
}
