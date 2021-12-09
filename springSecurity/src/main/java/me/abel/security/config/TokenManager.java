package me.abel.security.config;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TokenManager {

    private long tokenExpiration = 24*60*60*1000;

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String createToken (String username){

        return Jwts.builder().setSubject(username)
                .setExpiration(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().plusMillis(tokenExpiration)))
                .signWith(key)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    public String getUsernameFromToken (String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public static void main(String[] args) {
        TokenManager tokenManager = new TokenManager();
        String token = tokenManager.createToken("dy01");
        System.out.println(token);
        System.out.println(tokenManager.getUsernameFromToken(token));
    }
}
