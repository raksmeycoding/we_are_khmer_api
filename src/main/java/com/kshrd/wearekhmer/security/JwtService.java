package com.kshrd.wearekhmer.security;

import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class JwtService {
    private final static Logger logger = LoggerFactory.getLogger(JwtService.class);

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private List<String> mapAuthoryToList(Collection<? extends GrantedAuthority > authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }


    public <T> String generateToken(T t) {
        UserApp userApp = (UserApp) t;
        Map<String, Object> extraClaims = new HashMap<>();
        List<String> authories = mapAuthoryToList(((UserApp) t).getAuthorities());
        String userId = ((UserApp) t).getUserId();
        extraClaims.put("authorities", authories);
        extraClaims.put("userId", userId);
        extraClaims.put("photo_url", userApp.getPhotoUrl());
        extraClaims.put("email", userApp.getEmail());
        extraClaims.put("username", userApp.getUserName());
        return generateToken(extraClaims,(UserDetails) t);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date expirationDate = calendar.getTime();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 ))
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    public boolean isTokenValid(String token, UserDetails userDetails) {
        if(isTokenExpired(token)) {
            throw new ValidateException("Token had been already expired.", HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value());
        }
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
