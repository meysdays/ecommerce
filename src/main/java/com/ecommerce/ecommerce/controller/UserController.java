package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.DTO.ResponseDtos;
import com.ecommerce.ecommerce.DTO.user.SignInDto;
import com.ecommerce.ecommerce.DTO.user.SignInResponseDto;
import com.ecommerce.ecommerce.DTO.user.SignupDtos;
import com.ecommerce.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseDtos signup(@RequestBody SignupDtos signupDtos){
        return userService.signUp(signupDtos);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) throws NoSuchAlgorithmException {
        return userService.signIn(signInDto);
    }
}
