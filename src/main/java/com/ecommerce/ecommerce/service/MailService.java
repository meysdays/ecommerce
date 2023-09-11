package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.DTO.MailDto;
import com.ecommerce.ecommerce.model.Mailing;
import com.ecommerce.ecommerce.repository.CategoryRepo;
import com.ecommerce.ecommerce.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    MailRepository mailRepository;

    public void addMail(MailDto mailDto) {
        Mailing mailing = new Mailing();
        mailing.setEmail(mailDto.getEmail());
        mailing.setName(mailDto.getName());
        mailing.setPhone(mailDto.getPhone());
        mailing.setMessage(mailDto.getMessage());
        mailRepository.save(mailing);
    }
}
