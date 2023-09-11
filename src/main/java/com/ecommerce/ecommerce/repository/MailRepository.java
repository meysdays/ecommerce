package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Mailing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mailing, Integer> {
}
