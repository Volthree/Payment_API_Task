package vladislavmaltsev.paymenttaskapi.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vladislavmaltsev.paymenttaskapi.util.Role;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    long id;
    String name;
    BigDecimal usd;
    Role role;
    String pass;
}
