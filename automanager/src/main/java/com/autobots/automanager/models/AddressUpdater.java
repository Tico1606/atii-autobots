package com.autobots.automanager.models;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entities.Address;

@Component
public class AddressUpdater {
    private final NullStringChecker checker = new NullStringChecker();

    public void update(Address existing, Address updates) {
        if (updates != null) {
            if (!checker.isNullOrEmpty(updates.getState())) {
                existing.setState(updates.getState());
            }
            if (!checker.isNullOrEmpty(updates.getCity())) {
                existing.setCity(updates.getCity());
            }
            if (!checker.isNullOrEmpty(updates.getDistrict())) {
                existing.setDistrict(updates.getDistrict());
            }
            if (!checker.isNullOrEmpty(updates.getStreet())) {
                existing.setStreet(updates.getStreet());
            }
            if (!checker.isNullOrEmpty(updates.getNumber())) {
                existing.setNumber(updates.getNumber());
            }
            if (!checker.isNullOrEmpty(updates.getAdditionalInfo())) {
                existing.setAdditionalInfo(updates.getAdditionalInfo());
            }
        }
    }
}
