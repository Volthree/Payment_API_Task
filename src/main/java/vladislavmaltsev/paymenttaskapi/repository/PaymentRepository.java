package vladislavmaltsev.paymenttaskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vladislavmaltsev.paymenttaskapi.entity.Payment;
import vladislavmaltsev.paymenttaskapi.entity.User;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.user.id = :userName ORDER BY p.date DESC")
    Optional<Payment> findLatestPaymentByUserName(@Param("userName") String userName);
}
