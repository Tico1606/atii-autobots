package com.autobots.automanager.services;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controllers.IdentityDocumentController;
import com.autobots.automanager.entities.IdentityDocument;

@Component
public class IdentityDocumentLinkAdder implements LinkAdder<IdentityDocument> {

  @Override
  public void addLink(List<IdentityDocument> documents) {
    for (IdentityDocument doc : documents) {
      Long id = doc.getId();

      Link selfLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(IdentityDocumentController.class).getDocument(id))
          .withSelfRel();

      Link editLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(IdentityDocumentController.class).updateDocument(id, null))
          .withRel("edit");

      Link deleteLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(IdentityDocumentController.class).deleteDocument(id))
          .withRel("delete");

      doc.add(selfLink, editLink, deleteLink);
    }
  }

  @Override
  public void addLink(IdentityDocument doc) {
    Long id = doc.getId();

    Link collectionLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(IdentityDocumentController.class).getAllDocuments())
        .withRel("documents");

    Link editLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(IdentityDocumentController.class).updateDocument(id, null))
        .withRel("edit");

    Link deleteLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(IdentityDocumentController.class).deleteDocument(id))
        .withRel("delete");

    doc.add(collectionLink, editLink, deleteLink);
  }
}
