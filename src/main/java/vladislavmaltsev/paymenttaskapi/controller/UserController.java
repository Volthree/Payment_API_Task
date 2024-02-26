package vladislavmaltsev.paymenttaskapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.service.UserPaymentService;

@RestController
public class UserController {
    private final UserPaymentService userPaymentService;

    public UserController(UserPaymentService userPaymentService) {
        this.userPaymentService = userPaymentService;
    }

    @GetMapping(value = "/login")
    public User logIn() {
        return User.builder().build();
    }
    @GetMapping(value = "/logout")
    public User logOut() {
        return User.builder().build();
    }
    @GetMapping(value = "/payment")
    public User payment() {
        var user = userPaymentService.getUser(3);
        System.out.println(user);
        user  = userPaymentService.getMoney(2);
        return user;
    }
}
