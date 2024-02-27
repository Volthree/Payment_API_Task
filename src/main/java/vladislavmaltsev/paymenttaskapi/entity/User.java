package vladislavmaltsev.paymenttaskapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vladislavmaltsev.paymenttaskapi.util.Role;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "name")
    String name;
    @Column(name = "usd")
    BigDecimal usd;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;
    @Column(name = "pass")
    String pass;
}
