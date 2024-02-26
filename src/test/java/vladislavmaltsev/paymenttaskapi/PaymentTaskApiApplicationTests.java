package vladislavmaltsev.paymenttaskapi;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.repository.UserPaymentRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class PaymentTaskApiApplicationTests {

    @MockBean
    UserPaymentRepository userPaymentRepository;

    static User user;
    @BeforeAll
    static void setUser(){
        user = User.builder()
                .id(3)
                .name("Bob")
                .role("user")
                .usd(new BigDecimal(Double.toString(8)))
                .build();
    }
    @Test
    void repositoryOk() {
        when(userPaymentRepository.findById(3)).thenReturn(Optional.of(user));
        User optionalUser = userPaymentRepository.findById(3).orElse(User.builder().build());
        Assertions.assertEquals(user, optionalUser);
    }

}
