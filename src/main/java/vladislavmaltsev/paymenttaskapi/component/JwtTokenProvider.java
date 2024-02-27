package vladislavmaltsev.paymenttaskapi.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.security.auth.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.security.PrivateECKey;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "mySecretKey";
    public String generateToken(Authentication authentication) {
        System.out.println("Enter generateToken");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername() + " generating token process");
        Date expiryDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        var re = JWT.create()
                .withSubject("User Payments Test")
                .withClaim("username", userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(SECRET_KEY));
        System.out.println("End generateToken");
        return re;

    }

    public String validateToken(String token) {
        System.out.println("Enter validateToken");
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .withSubject("User Payments Test")
                .build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        var s = decodedJWT.getClaim("username").asString();
        System.out.println("End validateToken");
        return s;
    }
}
