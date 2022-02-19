package com.stx.config.security;

import com.stx.domains.models.User;
import io.jsonwebtoken.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String SPLITTER = ";";

    private final Logger logger = Logger.getLogger(getClass());
    private final String secretKey = "Eequei3cohLah9eu5Jahrah1";
    private final String issued = "stx.ru";

    public String generateToken(User user) {
        Date twoWeek = new Date(System.currentTimeMillis() + 2 * 7 * 24 * 60 * 60 * 1000);
        return Jwts.builder()
                .setSubject("%s%s%s".formatted(user.getId(), SPLITTER, user.getUsername()))
                .setIssuedAt(new Date())
                .setIssuer(issued)
                .setExpiration(twoWeek)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(SPLITTER)[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(SPLITTER)[1];
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - " + ex.getMessage());
        }
        return false;
    }
}
