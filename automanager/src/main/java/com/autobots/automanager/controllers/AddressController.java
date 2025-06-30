package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entities.Address;
import com.autobots.automanager.entities.Customer;
import com.autobots.automanager.models.AddressUpdater;
import com.autobots.automanager.repositories.AddressRepository;
import com.autobots.automanager.repositories.CustomerRepository;
import com.autobots.automanager.services.AddressLinkAdder;

import lombok.Data;

@RestController
@RequestMapping("/address")
public class AddressController {

  @Autowired
  private AddressRepository repository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private AddressUpdater updater;

  @Autowired
  private AddressLinkAdder linkAdder;

  @Data
  private static class CreateAddressRequest {
    private Address address;
    private Long customerId;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Address> getAddress(@PathVariable Long id) {
    Address address = repository.findById(id).orElse(null);
    if (address == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    linkAdder.addLink(address);
    return ResponseEntity.ok(address);
  }

  @GetMapping
  public ResponseEntity<List<Address>> getAllAddresses() {
    List<Address> addresses = repository.findAll();
    if (addresses.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    linkAdder.addLink(addresses);
    return ResponseEntity.ok(addresses);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerAddress(@RequestBody CreateAddressRequest request) {
    Optional<Customer> optCustomer = customerRepository.findById(request.getCustomerId());

    if (optCustomer.isEmpty()) {
      return ResponseEntity.badRequest().body("Customer not found");
    }

    Customer customer = optCustomer.get();
    Address address = request.getAddress();

    address.setCustomer(customer); 
    customer.setAddress(address); 

    customerRepository.save(customer); 

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody Address update) {
    Address address = repository.findById(id).orElse(null);
    if (address == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    updater.update(address, update);
    repository.save(address);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
    Address address = repository.findById(id).orElse(null);
    if (address == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    repository.delete(address);
    return ResponseEntity.ok().build();
  }
}
