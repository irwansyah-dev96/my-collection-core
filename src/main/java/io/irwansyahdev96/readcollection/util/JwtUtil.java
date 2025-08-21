package io.irwansyahdev96.readcollection.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class JwtUtil {

    @Value("${secret.key}")
    private String key;

    public String generated(Map<String, Object> claims){
        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,key.getBytes())
                .compact();
    }
    public Map<String, Object> parse(String token){
        return Jwts.parser()
                .setSigningKey(key.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

}
