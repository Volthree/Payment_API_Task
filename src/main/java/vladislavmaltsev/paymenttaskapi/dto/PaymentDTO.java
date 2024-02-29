package vladislavmaltsev.paymenttaskapi.dto;

import lombok.*;
import vladislavmaltsev.paymenttaskapi.entity.User;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    BigDecimal amount;
    Date date;
    @ToString.Exclude
    User user;
}
