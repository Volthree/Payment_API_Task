package vladislavmaltsev.paymenttaskapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vladislavmaltsev.paymenttaskapi.service.AuthService;
import vladislavmaltsev.paymenttaskapi.service.UserPaymentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task1")
public class UserController {
    private final UserPaymentService userPaymentService;
    private final AuthService authService;

 /*   @GetMapping(value = "/login")
    public String logIn() {
        System.out.println("Enter in controller login");
        return "logpage";
    }

    @GetMapping(value = "/payment/{id}")
    @ResponseBody
    public UserDTO payment(@PathVariable long id) {
        var user = userPaymentService.getMoney(id);
        System.out.println(user);
        return user;
    }*/

    @PostMapping("/register")
    private ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ){
        System.out.println("register");
        return ResponseEntity.ok(authService.register(registerRequest));
    }
    @GetMapping("/authenticate")
    private ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        System.out.println("authenticate");
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }

    @PostMapping("/demo")
    private ResponseEntity<String> demo(
    ){
        return ResponseEntity.ok("Hello epta");
    }
}
