package vladislavmaltsev.paymenttaskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vladislavmaltsev.paymenttaskapi.entity.User;

import java.util.Optional;

@Repository
public interface UserPaymentRepository extends JpaRepository<User, Integer> {
        Optional<User> findById(int id);
        Optional<User> findByName(String name);
}
