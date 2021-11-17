package ug.payway.technicalmaintenanceschedule.config.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtProvider {
    private static final String FIRST_NAME = "first name";
    private static final String LAST_NAME = "last name";
    private static final String ACTIVE = "active";
    private static final String ON_DUTY = "duty";
    private static final String ROLE = "role";
    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String generateToken(String email, String firstName, String lastName, String active,
                                String onDuty, String userRole, Long userId) {
        Date expirationDate = Date.from(
                LocalDate.now()
                        .plusDays(15)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(email)
                .claim(FIRST_NAME, firstName)
                .claim(LAST_NAME, lastName)
                .claim(ACTIVE, active)
                .claim(ON_DUTY, onDuty)
                .claim(ROLE, userRole)
                .claim("id", userId)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported JWT token");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed JWT token");
        } catch (SignatureException sEx) {
            log.error("Invalid signature");
        } catch (Exception e) {
            log.error("invalid JWT token");
        }
        return false;
    }

    public Map<String, String> getClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Map.of(
                "sub", claims.getSubject(),
                FIRST_NAME, (String) claims.get(FIRST_NAME),
                LAST_NAME, (String) claims.get(LAST_NAME),
                ACTIVE, (String) claims.get(ACTIVE),
                ON_DUTY, (String) claims.get(ON_DUTY),
                ROLE, (String) claims.get(ROLE)
        );
    }
}
