package vladislavmaltsev.paymenttaskapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vladislavmaltsev.paymenttaskapi.service.*;
import vladislavmaltsev.paymenttaskapi.service.serviceutil.AuthenticationRequest;
import vladislavmaltsev.paymenttaskapi.service.serviceutil.RegisterRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
    private final UserService userService;
    private final AuthService authService;
    private final LogoutService logoutService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/register")
    private ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        var result = authService.register(registerRequest);
        if (result.getToken().isEmpty()) {
            return ResponseEntity.ok("Already registered");
        }
        return ResponseEntity.ok("Registered token: " + result.getToken());
    }

    @GetMapping("/login")
    private ResponseEntity<String> login(@RequestBody AuthenticationRequest authenticationRequest) {
        var token = authService.authenticate(authenticationRequest).getToken();
        jwtTokenService.deleteTokenFromBlackList(token);
        return ResponseEntity.ok("Logged in with token: " + token);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
                                         HttpServletResponse response) {
        logoutService.logout(request, response);
        return ResponseEntity.ok("Logged out");
    }

    @PostMapping("/payment")
    public ResponseEntity<String> payment(HttpServletRequest request) {
        return ResponseEntity.ok(userService.subtractAmountFromUser(request).toString());
    }
}
