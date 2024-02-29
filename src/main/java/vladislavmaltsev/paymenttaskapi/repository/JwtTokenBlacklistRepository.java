package vladislavmaltsev.paymenttaskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vladislavmaltsev.paymenttaskapi.entity.JwtTokenBlacklist;
import vladislavmaltsev.paymenttaskapi.entity.Payment;

import java.util.Optional;

@Repository
public interface JwtTokenBlacklistRepository extends JpaRepository<JwtTokenBlacklist, Long> {

    JwtTokenBlacklist findByToken(String token);
    JwtTokenBlacklist deleteJwtTokenBlacklistByToken(String token);
}
