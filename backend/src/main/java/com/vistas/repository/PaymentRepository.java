package com.vistas.repository;

import com.vistas.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByRazorpayOrderId(String orderId);
    
    Optional<Payment> findByRazorpayPaymentId(String paymentId);
    
    boolean existsByRazorpayPaymentId(String paymentId);
}
