package vladislavmaltsev.paymenttaskapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    long id;
    @Column(name = "amount")
    BigDecimal amount;
    @Column(name = "payment_date", nullable = false)
    Date date;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "name")
    @ToString.Exclude
    User user;

}
