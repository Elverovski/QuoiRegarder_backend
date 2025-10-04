package org.example.backend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.example.backend.models.User;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class LoginService {

    private final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
    private final UserRepository userRepository;
    private final String SECRET_KEY = "UneCleTresLongueEtAleatoireDePlusDe32Caracteres";

    public LoginService(UserRepository userRepository){
        //this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }

    public String login(String email, String password){
        User user = userRepository.findUserByEmail(email);
        if (user == null){
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Your email or password is incorrect");
        }
        System.out.println(generateToken(user));
        return generateToken(user);
    }

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // expire dans 1h
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenValid(String token) {
        try {

            Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    private Key getSignInKey() {
        //byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
