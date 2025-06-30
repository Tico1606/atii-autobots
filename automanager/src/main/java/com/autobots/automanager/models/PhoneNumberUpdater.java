package com.autobots.automanager.models;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entities.PhoneNumber;

@Component
public class PhoneNumberUpdater {
    private final NullStringChecker checker = new NullStringChecker();

    public void update(PhoneNumber phone, PhoneNumber updates) {
        if (updates != null) {
            if (!checker.isNullOrEmpty(updates.getDdd())) {
                phone.setDdd(updates.getDdd());
            }
            if (!checker.isNullOrEmpty(updates.getNumber())) {
                phone.setNumber(updates.getNumber());
            }
        }
    }

    public void update(List<PhoneNumber> existingPhones, List<PhoneNumber> updates) {
        for (PhoneNumber upd : updates) {
            for (PhoneNumber phone : existingPhones) {
                if (upd.getId() != null && upd.getId().equals(phone.getId())) {
                    update(phone, upd);
                }
            }
        }
    }
}
