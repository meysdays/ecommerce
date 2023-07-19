package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.exceptions.AuthenticationFailException;
import com.ecommerce.ecommerce.model.AuthenticationToken;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUser(String token) {
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if (Objects.isNull(authenticationToken)){
            return null;
        }
        return authenticationToken.getUser();
    }

    public void authenticate(String token) {
        if (Objects.isNull(token)){
            //throw an exception
            throw new AuthenticationFailException("token not present");
        }
        if (Objects.isNull(getUser(token))){
            throw new AuthenticationFailException("token not valid");
        }
    }
}
