package com.vistas.service;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.model.Role;
import co.vistafoundation.vlearning.auth.model.RoleName;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.repository.RoleRepository;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentSubscriptionRepository studentSubscriptionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RazorpayService razorpayService;

    public User findOrCreateByPhone(String phoneNumber) {
        User user = userRepository.findByMobileNumber(phoneNumber);
        if (user != null) {
            return user;
        }

        // Create new User
        Role studentRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
        if (studentRole == null) {
            throw new RuntimeException("Student Role not found in database.");
        }

        User newUser = new User();
        newUser.setFirstName("Student");
        newUser.setLastName("User");
        newUser.setUsername(phoneNumber);
        newUser.setEmail(phoneNumber + "@vistaslearning.com");
        newUser.setPassword(passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(10)));
        newUser.setRegisteredAs("Student");
        newUser.setMobileNumber(phoneNumber);
        newUser.setActiveFlag(Boolean.TRUE);
        newUser.setRoles(Collections.singleton(studentRole));

        User savedUser = userRepository.save(newUser);

        // Create corresponding Student
        Student student = new Student();
        student.setUser(savedUser);
        student.setIsProfileEdited(Boolean.FALSE);
        studentRepository.save(student);

        return savedUser;
    }

    public boolean hasActiveSubscription(Long userId) {
        StudentSubscription subscription = studentSubscriptionRepository
                .findByUserSurIdAndIdproductLineAndActiveFlag(userId, 11L, Boolean.TRUE);
        if (subscription == null) {
            return false;
        }

        Instant nextPayment = subscription.getNextPaymentDate();
        Instant endDate = subscription.getSubscriptionEndDate();
        Instant now = Instant.now();

        if (nextPayment == null || endDate == null) {
            return false;
        }

        ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");
        java.time.LocalDate dateNextPaymentDate = nextPayment.atZone(zoneIndia).toLocalDate();
        java.time.LocalDate dateEndDate = endDate.atZone(zoneIndia).toLocalDate();
        java.time.LocalDate dateToday = now.atZone(zoneIndia).toLocalDate();

        return (dateNextPaymentDate.isAfter(dateToday) || dateNextPaymentDate.equals(dateToday))
                && (dateEndDate.isAfter(dateToday) || dateEndDate.equals(dateToday));
    }

    public StudentSubscription getActiveSubscription(Long userId) {
        return studentSubscriptionRepository
                .findByUserSurIdAndIdproductLineAndActiveFlag(userId, 11L, Boolean.TRUE);
    }

    public User findById(Long id) {
        return userRepository.findByUserSurId(id);
    }

    public void activateSubscription(Long userId, String plan, int months) {
        Student student = studentRepository.findByUser_userSurId(userId);
        if (student == null) {
            User user = userRepository.findByUserSurId(userId);
            if (user == null) {
                throw new RuntimeException("User not found: " + userId);
            }
            student = new Student();
            student.setUser(user);
            student.setIsProfileEdited(Boolean.FALSE);
            student = studentRepository.save(student);
        }

        StudentSubscription subscription = studentSubscriptionRepository
                .findByUserSurIdAndIdproductLine(userId, 11L);

        if (subscription == null) {
            subscription = new StudentSubscription();
            subscription.setUserSurId(userId);
            subscription.setIdproductLine(11L); // Standard student subscription product line
            subscription.setPurchaseType("NEW");
        } else {
            subscription.setPurchaseType("RENEWAL");
        }

        Instant now = Instant.now();
        Instant nextPaymentDate = now.atZone(ZoneId.of("Asia/Kolkata"))
                .toLocalDateTime()
                .plusMonths(months)
                .atZone(ZoneId.of("Asia/Kolkata"))
                .toInstant();

        subscription.setIdStudent(student.getIdStudent());
        subscription.setIdProduct(93L); // Default product ID for the subscription
        subscription.setActiveFlag(Boolean.TRUE);
        subscription.setFreeFlag(Boolean.FALSE);
        subscription.setPurchaseAmount(razorpayService.getPlanAmount(plan).toString());
        subscription.setPurchaseDate(now);
        subscription.setLastPaymentDate(now);
        subscription.setNextPaymentDate(nextPaymentDate);
        subscription.setSubscriptionEndDate(nextPaymentDate);
        subscription.setSubscriptionType(plan);
        subscription.setPurchaseLevel("PRODUCT");

        studentSubscriptionRepository.save(subscription);
    }
}
