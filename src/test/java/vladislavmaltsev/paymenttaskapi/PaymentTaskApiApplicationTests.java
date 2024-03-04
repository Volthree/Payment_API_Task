package vladislavmaltsev.paymenttaskapi;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.repository.UserRepository;
import vladislavmaltsev.paymenttaskapi.service.JwtTokenService;
import vladislavmaltsev.paymenttaskapi.util.Role;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class PaymentTaskApiApplicationTests {

    @MockBean
    UserRepository userRepository;
    @Autowired
    JwtTokenService jwtTokenService;
    static User user;
    @BeforeAll
    static void setUser(){
        user = User.builder()
                .id(3)
                .name("Bob")
                .role(Role.USER)
                .build();
    }
    @Test
    void repositoryOk() {
        when(userRepository.findByName("Bob")).thenReturn(Optional.of(user));
        User optionalUser = userRepository.findByName("Bob").orElse(User.builder().build());
        Assertions.assertEquals(user, optionalUser);
    }
    @Test
    void validateToken(){
        String token = "eyJhbGciOiJub25lIn0.eyJzdWIiOiJxd2VyIiwiaWF0IjoxNzA5MjQ3NjY5LCJleHAiOjE3MDkyNjIwNjl9.";
        String name = jwtTokenService.getUserNameFromToken(token);
        System.out.println(name);
        Assertions.assertEquals(name, "7");
    }

}
