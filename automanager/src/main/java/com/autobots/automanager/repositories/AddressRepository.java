package com.autobots.automanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}