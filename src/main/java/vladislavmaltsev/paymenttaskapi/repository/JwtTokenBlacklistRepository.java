package vladislavmaltsev.paymenttaskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vladislavmaltsev.paymenttaskapi.entity.JwtTokenBlacklist;

@Repository
public interface JwtTokenBlacklistRepository extends JpaRepository<JwtTokenBlacklist, Long> {
    JwtTokenBlacklist findByToken(String token);
    void deleteByToken(String token);
}
