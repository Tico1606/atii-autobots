package com.autobots.automanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entities.PhoneNumber;

public interface PhoneRepository extends JpaRepository<PhoneNumber, Long> {
}