package vladislavmaltsev.paymenttaskapi;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import vladislavmaltsev.paymenttaskapi.component.JwtTokenProvider;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.repository.UserPaymentRepository;
import vladislavmaltsev.paymenttaskapi.util.Role;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class PaymentTaskApiApplicationTests {

    @MockBean
    UserPaymentRepository userPaymentRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    static User user;
    @BeforeAll
    static void setUser(){
        user = User.builder()
                .id(3)
                .name("Bob")
                .role(Role.USER)
                .usd(new BigDecimal(Double.toString(8)))
                .build();
    }
    @Test
    void repositoryOk() {
        when(userPaymentRepository.findById(3)).thenReturn(Optional.of(user));
        User optionalUser = userPaymentRepository.findById(3).orElse(User.builder().build());
        Assertions.assertEquals(user, optionalUser);
    }
    @Test
    void validateToken(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIFBheW1lbnRzIFRlc3QiLCJ1c2VybmFtZSI6IjciLCJpYXQiOjE3MDkwMzA0MDcsImV4cCI6MTcwOTAzNDAwN30.9771KmALIMlUZFcqeKLk0URuYO_32hGOMuEHYQ--Wvs";
        String name = jwtTokenProvider.validateToken(token);
        Assertions.assertEquals(name, "7");
    }

}
