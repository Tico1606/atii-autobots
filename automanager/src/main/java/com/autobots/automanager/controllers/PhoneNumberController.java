package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entities.PhoneNumber;
import com.autobots.automanager.entities.Customer;
import com.autobots.automanager.models.PhoneNumberUpdater;
import com.autobots.automanager.repositories.PhoneRepository;
import com.autobots.automanager.repositories.CustomerRepository;
import com.autobots.automanager.services.PhoneNumberLinkAdder;

import lombok.Data;

@RestController
@RequestMapping("/phone")
public class PhoneNumberController {

  @Autowired
  private PhoneRepository repository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private PhoneNumberUpdater updater;

  @Autowired
  private PhoneNumberLinkAdder linkAdder;

  @Data
  private static class CreatePhoneRequest {
    private PhoneNumber phone;
    private Long customerId;
  }

  @GetMapping("/{id}")
  public ResponseEntity<PhoneNumber> getPhoneNumber(@PathVariable Long id) {
    PhoneNumber phone = repository.findById(id).orElse(null);
    if (phone == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    linkAdder.addLink(phone);
    return ResponseEntity.ok(phone);
  }

  @GetMapping
  public ResponseEntity<List<PhoneNumber>> getAllPhoneNumbers() {
    List<PhoneNumber> phones = repository.findAll();
    if (phones.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    linkAdder.addLink(phones);
    return ResponseEntity.ok(phones);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerPhoneNumber(@RequestBody CreatePhoneRequest request) {
    Optional<Customer> optCustomer = customerRepository.findById(request.getCustomerId());
    if (optCustomer.isEmpty()) {
      return ResponseEntity.badRequest().body("Customer not found");
    }

    PhoneNumber phone = request.getPhone();
    Customer customer = optCustomer.get();

    phone.setCustomer(customer);
    PhoneNumber saved = repository.save(phone);

    customer.getPhones().add(saved);
    customerRepository.save(customer);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePhoneNumber(@PathVariable Long id, @RequestBody PhoneNumber update) {
    PhoneNumber phone = repository.findById(id).orElse(null);
    if (phone == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    updater.update(phone, update);
    repository.save(phone);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePhoneNumber(@PathVariable Long id) {
    PhoneNumber phone = repository.findById(id).orElse(null);
    if (phone == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    repository.delete(phone);
    return ResponseEntity.ok().build();
  }
}
