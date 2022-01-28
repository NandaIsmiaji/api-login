package com.apilogin.security;

import java.io.Serializable;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Component
public class TokenProvider implements Serializable{
	
	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 168*60*60;
    public static final String SIGNING_KEY = "NLE2021";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";
    
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }

    UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final UserDetails userDetails) {

        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public UsernamePasswordAuthenticationToken getIdentityAuthentication(final UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
//    
//    private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS512);
//    private static final byte[] secretBytes = secret.getEncoded();
//    private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);
//    
    public String generateCustomToken(String platform_id) {
        final Date createdDate = new Date(System.currentTimeMillis());
        final Date expirationDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000);
        
        String token = Jwts.builder()
        		  .claim("id", platform_id)
        	      .setSubject("seamlesslogin")
        	      .setIssuedAt(createdDate)
        	      .setExpiration(expirationDate)
        	      .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
        	      .compact();
        
//        String token = Jwts.builder()
//            .setId(platform_id)
//            .signWith(SignatureAlgorithm.HS512, base64SecretBytes)
//            .setExpiration(new Date(System.currentTimeMillis() + (600*1000)))
//            .compact();
        return token;
    }
    
    public String verifyCustomToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(SIGNING_KEY)
            .parseClaimsJws(token).getBody();
        System.out.println("Expiration: " + claims.getExpiration());
        return claims.getId();
    }
}
