package com.autobots.automanager.models;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entities.IdentityDocument;

@Component
public class IdentityDocumentUpdater {
  private final NullStringChecker checker = new NullStringChecker();

  public void update(IdentityDocument doc, IdentityDocument updates) {
    if (updates != null) {
      if (!checker.isNullOrEmpty(updates.getType())) {
        doc.setType(updates.getType());
      }
      if (!checker.isNullOrEmpty(updates.getNumber())) {
        doc.setNumber(updates.getNumber());
      }
    }
  }

  public void update(List<IdentityDocument> existingDocs, List<IdentityDocument> updates) {
    for (IdentityDocument upd : updates) {
      for (IdentityDocument doc : existingDocs) {
        if (upd.getId() != null && upd.getId().equals(doc.getId())) {
          update(doc, upd);
        }
      }
    }
  }
}
