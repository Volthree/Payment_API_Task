package vladislavmaltsev.paymenttaskapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vladislavmaltsev.paymenttaskapi.dto.UserDTO;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.service.UserPaymentService;

@Controller
public class UserController {
    private final UserPaymentService userPaymentService;

    public UserController(UserPaymentService userPaymentService) {
        this.userPaymentService = userPaymentService;
    }

    @GetMapping(value = "/loginn")
    public String logIn() {
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDD");
        return "src/main/webapp/WEB-INF/views/loginpage.html";
    }

    @GetMapping(value = "/logout")
    public User logOut() {
        return User.builder().build();
    }

    @GetMapping(value = "/payment")
    @ResponseBody
    public UserDTO payment() {
        var user = userPaymentService.getMoney(3);
        System.out.println(user);
        return user;
    }
}
