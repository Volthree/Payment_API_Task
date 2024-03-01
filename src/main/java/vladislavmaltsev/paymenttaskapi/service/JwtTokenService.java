package vladislavmaltsev.paymenttaskapi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vladislavmaltsev.paymenttaskapi.entity.JwtTokenBlacklist;
import vladislavmaltsev.paymenttaskapi.repository.JwtTokenBlacklistRepository;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
    public class JwtTokenService {
    @Value("${paymentaskapi.secretkey}")
    private String secretKey;
    private final JwtTokenBlacklistRepository jwtTokenBlacklistRepository;

    public JwtTokenService(JwtTokenBlacklistRepository jwtTokenBlacklistRepository) {
        this.jwtTokenBlacklistRepository = jwtTokenBlacklistRepository;
    }

    public String getUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = getClaims(token);
        System.out.println("CLAIMS " + claims.toString());
        return claimsTFunction.apply(claims);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .unsecured()
                .setSigningKey(getSecretKey())
                .build()
                .parseUnsecuredClaims(token)
                .getPayload();
    }

    @Transactional
    public void invalidateToken(String token) {
        var tokenBlacklist = new JwtTokenBlacklist();
        tokenBlacklist.setToken(token);
        jwtTokenBlacklistRepository.save(tokenBlacklist);
    }

    @Transactional
    public boolean isContainsInBlacklist(String token) {
        return jwtTokenBlacklistRepository.findByToken(token) != null;
    }

    @Transactional
    public void deleteTokenFromBlackList(String token) {
        jwtTokenBlacklistRepository.deleteByToken(token.substring(7));
    }

    private Key getSecretKey() {
        byte[] bytesKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytesKey);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpireDate(token).before(new Date());
    }

    private Date extractExpireDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .compact();
    }
}
