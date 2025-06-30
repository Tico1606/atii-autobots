package com.autobots.automanager.models;

import com.autobots.automanager.entities.Customer;

public class CustomerUpdater {
    private final NullStringChecker checker = new NullStringChecker();
    private final AddressUpdater addressUpdater = new AddressUpdater();
    private final IdentityDocumentUpdater documentUpdater = new IdentityDocumentUpdater();
    private final PhoneNumberUpdater phoneUpdater = new PhoneNumberUpdater();

    private void updateBasicInfo(Customer existing, Customer updates) {
        if (!checker.isNullOrEmpty(updates.getName())) {
            existing.setName(updates.getName());
        }
        if (!checker.isNullOrEmpty(updates.getSocialName())) {
            existing.setSocialName(updates.getSocialName());
        }
        if (updates.getCreatedAt() != null) {
            existing.setCreatedAt(updates.getCreatedAt());
        }
        if (updates.getBirthDate() != null) {
            existing.setBirthDate(updates.getBirthDate());
        }
    }

    public void update(Customer existing, Customer updates) {
        updateBasicInfo(existing, updates);
        addressUpdater.update(existing.getAddress(), updates.getAddress());
        documentUpdater.update(existing.getDocuments(), updates.getDocuments());
        phoneUpdater.update(existing.getPhones(), updates.getPhones());
    }
}
