package com.example.internintelligence_potfolioapidevelopment.utility;

import com.example.internintelligence_potfolioapidevelopment.dao.entity.User;
import com.example.internintelligence_potfolioapidevelopment.dao.repo.UserRepo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@Configuration
@PropertySource("classpath:application.yml")
@RequiredArgsConstructor
public class JwtUtil {

    private final UserRepo userRepo;

    @Value("${application.security.jwt.secret-key}")
    private String secret_key;
    @Value("${application.security.jwt.expiration}")
    private long accessTokenValidity;
    private static Key key;

    public Key initializeKey() {
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode(secret_key);
        key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    public String createToken(User user) {
        key = initializeKey();
        user = userRepo.findByEmail(user.getEmail()).orElseThrow();

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("authorities",roles);
        claimsMap.put("user_id", user.getId());

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));

        final JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(tokenValidity)
                .addClaims(claimsMap)
                .signWith(key, SignatureAlgorithm.HS512);
        return jwtBuilder.compact();
    }

    public String resolveToken(HttpServletRequest request) {

        String TOKEN_HEADER = "Authorization";
        String bearerToken = request.getHeader(TOKEN_HEADER);
        String TOKEN_PREFIX = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean isTokenValid(String token, User user){
        final String userEmail = extractEmail(token);
        return userEmail.equals(user.getEmail()) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public Integer getUserId(Claims claims){
        return (Integer) claims.get("user_id");
    }

    public String extractEmail(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(initializeKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }


    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return extractAllClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            log.error("Error due to: {}", ex.getMessage());
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Error due to: {}", ex.getMessage());
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }
//
//
//    public Collection<GrantedAuthority> extractAuthorities(Claims claims) {
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        if (claims.containsKey("authorities")) {
//            List<String> roles = (List<String>) claims.get("authorities");
//            for (String role : roles) {
//                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//            }
//        }
//        return authorities;
//    }


}
