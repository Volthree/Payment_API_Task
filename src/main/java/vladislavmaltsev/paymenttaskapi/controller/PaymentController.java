package vladislavmaltsev.paymenttaskapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vladislavmaltsev.paymenttaskapi.dto.UserDTO;
import vladislavmaltsev.paymenttaskapi.service.AuthService;
import vladislavmaltsev.paymenttaskapi.service.UserPaymentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {

    private final UserPaymentService userPaymentService;
    @GetMapping(value = "/payment/{name}")
    @ResponseBody
    public UserDTO payment(@PathVariable String name) {
        var user = userPaymentService.getMoney(name);
        System.out.println(user);
        return user;
    }
}
