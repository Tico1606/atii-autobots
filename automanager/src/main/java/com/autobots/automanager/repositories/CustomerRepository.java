package com.autobots.automanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}