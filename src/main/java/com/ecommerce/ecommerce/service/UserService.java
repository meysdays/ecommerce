package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.DTO.ResponseDtos;
import com.ecommerce.ecommerce.DTO.user.SignInDto;
import com.ecommerce.ecommerce.DTO.user.SignInResponseDto;
import com.ecommerce.ecommerce.DTO.user.SignupDtos;
import com.ecommerce.ecommerce.common.ApiResponse;
import com.ecommerce.ecommerce.exceptions.AuthenticationFailException;
import com.ecommerce.ecommerce.exceptions.CustomException;
import com.ecommerce.ecommerce.model.AuthenticationToken;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

//    @Autowired
//    AuthenticationToken authenticationToken;

    @Transactional
    public ResponseDtos signUp(SignupDtos signupDtos) {

        if (Objects.nonNull(userRepository.findByEmail(signupDtos.getEmail()))){
            throw new CustomException("User already present");
        }

        String encryptedpassword = signupDtos.getPassword();

        try {
            encryptedpassword = hashPassword(signupDtos.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(
                signupDtos.getFirstName(),
                signupDtos.getLastName(),
                signupDtos.getEmail(),
                encryptedpassword
        );

        userRepository.save(user);

        //create token

       final AuthenticationToken authenticationToken = new AuthenticationToken(user);

       authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDtos responseDtos = new ResponseDtos("success", "User created successfully");
        return responseDtos;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws NoSuchAlgorithmException {
        // find by email

        User user = userRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)){
            throw new AuthenticationFailException("user is not valid");
        }

        //hash the password

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                throw new AuthenticationFailException("Wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //compare the password in db

        //if password match

        AuthenticationToken token = authenticationService.getToken(user);

        if (Objects.isNull(token)){
            throw new CustomException("token is not present");
        }

        return new SignInResponseDto("success", token.getToken());

        //retrieve the token
    }
}
