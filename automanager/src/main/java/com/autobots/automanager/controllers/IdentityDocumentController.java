package com.autobots.automanager.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entities.Customer;
import com.autobots.automanager.entities.IdentityDocument;
import com.autobots.automanager.models.IdentityDocumentUpdater;
import com.autobots.automanager.repositories.CustomerRepository;
import com.autobots.automanager.repositories.DocumentRepository;
import com.autobots.automanager.services.IdentityDocumentLinkAdder;

import lombok.Data;

@RestController
@RequestMapping("/document")
public class IdentityDocumentController {

  @Autowired
  private DocumentRepository repository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private IdentityDocumentLinkAdder linkAdder;

  @Autowired
  private IdentityDocumentUpdater updater;

  @Data
  private static class CreateDocumentRequest {
    private IdentityDocument document;
    private Long customerId;
  }

  @GetMapping("/{id}")
  public ResponseEntity<IdentityDocument> getDocument(@PathVariable Long id) {
    Optional<IdentityDocument> doc = repository.findById(id);
    if (doc.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    linkAdder.addLink(doc.get());
    return ResponseEntity.ok(doc.get());
  }

  @GetMapping
  public ResponseEntity<List<IdentityDocument>> getAllDocuments() {
    List<IdentityDocument> documents = repository.findAll();
    if (documents.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    linkAdder.addLink(documents);
    return ResponseEntity.ok(documents);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerDocument(@RequestBody CreateDocumentRequest request) {
    Optional<Customer> optCustomer = customerRepository.findById(request.getCustomerId());
    if (optCustomer.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer not found.");
    }

    IdentityDocument document = request.getDocument();
    Customer customer = optCustomer.get();

    document.setCustomer(customer);
    IdentityDocument saved = repository.save(document);

    customer.getDocuments().add(saved);
    customerRepository.save(customer);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateDocument(@PathVariable Long id, @RequestBody IdentityDocument update) {
    Optional<IdentityDocument> existing = repository.findById(id);
    if (existing.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found.");
    }

    updater.update(existing.get(), update);
    repository.save(existing.get());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
    Optional<IdentityDocument> doc = repository.findById(id);
    if (doc.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found.");
    }
    repository.delete(doc.get());
    return ResponseEntity.ok().build();
  }
}
