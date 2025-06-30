package com.autobots.automanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entities.Address;
import com.autobots.automanager.entities.Customer;
import com.autobots.automanager.entities.IdentityDocument;
import com.autobots.automanager.entities.PhoneNumber;
import com.autobots.automanager.models.CustomerUpdater;
import com.autobots.automanager.models.CustomerSelector;
import com.autobots.automanager.repositories.AddressRepository;
import com.autobots.automanager.repositories.CustomerRepository;
import com.autobots.automanager.repositories.DocumentRepository;
import com.autobots.automanager.repositories.PhoneRepository;
import com.autobots.automanager.services.CustomerLinkAdder;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private DocumentRepository documentRepository;

  @Autowired
  private PhoneRepository phoneRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private CustomerSelector customerSelector;

  @Autowired
  private CustomerLinkAdder linkAdder;

  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
    List<Customer> customers = customerRepository.findAll();
    Customer customer = customerSelector.selectById(customers, id);
    if (customer == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    linkAdder.addLink(customer);
    return ResponseEntity.ok(customer);
  }

  @GetMapping
  public ResponseEntity<List<Customer>> getAllCustomers() {
    List<Customer> customers = customerRepository.findAll();
    if (customers.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    linkAdder.addLink(customers);
    return ResponseEntity.ok(customers);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
    if (customer.getId() != null && customerRepository.existsById(customer.getId())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer with given ID already exists.");
    }

    Customer savedCustomer = customerRepository.save(customer);

    if (savedCustomer.getDocuments() != null) {
      for (IdentityDocument doc : savedCustomer.getDocuments()) {
        doc.setCustomer(savedCustomer);
        documentRepository.save(doc);
      }
    }

    if (savedCustomer.getPhones() != null) {
      for (PhoneNumber phone : savedCustomer.getPhones()) {
        phone.setCustomer(savedCustomer);
        phoneRepository.save(phone);
      }
    }

    Address address = savedCustomer.getAddress();
    if (address != null) {
      address.setCustomer(savedCustomer);
      addressRepository.save(address);
    }

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer update) {
    List<Customer> customers = customerRepository.findAll();
    Customer existingCustomer = customerSelector.selectById(customers, id);
    if (existingCustomer == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    CustomerUpdater updater = new CustomerUpdater();
    updater.update(existingCustomer, update);
    customerRepository.save(existingCustomer);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
    List<Customer> customers = customerRepository.findAll();
    Customer customer = customerSelector.selectById(customers, id);
    if (customer == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    customerRepository.delete(customer);
    return ResponseEntity.ok().build();
  }
}
