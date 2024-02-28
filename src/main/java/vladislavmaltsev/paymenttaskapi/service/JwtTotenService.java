package vladislavmaltsev.paymenttaskapi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTotenService {

    private static final String secterKey = "dGhpcyBpcyBuYW1lIGhlcmUgYW5kIGkgaG9wZSBpdCB3aWxsIGhlbHA=";
    public String getUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
            final Claims claims = getClaims(token);
            return claimsTFunction.apply(claims);
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSecretKey() {
        byte[] bytesKey =Decoders.BASE64.decode(secterKey);
        return Keys.hmacShaKeyFor(bytesKey);
    }

    public String generateToken(UserDetails userDetails){
            return generateToken(Map.of(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
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
            UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .issuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
