package vladislavmaltsev.paymenttaskapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vladislavmaltsev.paymenttaskapi.component.JwtTokenProvider;
import vladislavmaltsev.paymenttaskapi.dto.UserDTO;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.service.UserPaymentService;

@Controller
public class UserController {
    private final UserPaymentService userPaymentService;
    private final JwtTokenProvider jwtTokenProvider;
    public UserController(UserPaymentService userPaymentService, JwtTokenProvider jwtTokenProvider,
                          JwtTokenProvider jwtTokenProvider1) {
        this.userPaymentService = userPaymentService;
        this.jwtTokenProvider = jwtTokenProvider1;
    }

    @GetMapping(value = "/login")
    public String logIn() {
        System.out.println("Enter in controller //login");
        return "logpage";
    }
    @PostMapping(value = "/login")
    @ResponseBody
    public String logInPost(@RequestParam String username,
                            @RequestParam String password){
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
        return null;
    }

//    @GetMapping(value = "/logout")
//    public User logOut() {
//        return User.builder().build();
//    }

    @GetMapping(value = "/payment/{id}")
    @ResponseBody
    public UserDTO payment(@PathVariable long id) {
        var user = userPaymentService.getMoney(id);
        System.out.println(user);
        return user;
    }
}
