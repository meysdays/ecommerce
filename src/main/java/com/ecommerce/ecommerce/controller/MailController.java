package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.DTO.MailDto;
import com.ecommerce.ecommerce.common.ApiResponse;
import com.ecommerce.ecommerce.repository.MailRepository;
import com.ecommerce.ecommerce.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMail(@RequestBody MailDto mailDto) {
        mailService.addMail(mailDto);
        return new ResponseEntity<>(new ApiResponse(true, "mail has been added"), HttpStatus.CREATED);
    }
}
