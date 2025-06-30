package com.autobots.automanager.services;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controllers.PhoneNumberController;
import com.autobots.automanager.entities.PhoneNumber;

@Component
public class PhoneNumberLinkAdder implements LinkAdder<PhoneNumber> {

  @Override
  public void addLink(List<PhoneNumber> phones) {
    for (PhoneNumber phone : phones) {
      Long id = phone.getId();

      Link selfLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getPhoneNumber(id))
          .withSelfRel();

      Link editLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(PhoneNumberController.class).updatePhoneNumber(id, null))
          .withRel("edit");

      Link deleteLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(PhoneNumberController.class).deletePhoneNumber(id))
          .withRel("delete");

      phone.add(selfLink, editLink, deleteLink);
    }
  }

  @Override
  public void addLink(PhoneNumber phone) {
    Long id = phone.getId();

    Link collectionLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(PhoneNumberController.class).getAllPhoneNumbers())
        .withRel("phones");

    Link editLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(PhoneNumberController.class).updatePhoneNumber(id, null))
        .withRel("edit");

    Link deleteLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(PhoneNumberController.class).deletePhoneNumber(id))
        .withRel("delete");

    phone.add(collectionLink, editLink, deleteLink);
  }
}
