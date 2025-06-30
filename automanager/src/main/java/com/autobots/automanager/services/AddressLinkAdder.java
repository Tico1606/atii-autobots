package com.autobots.automanager.services;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controllers.AddressController;
import com.autobots.automanager.entities.Address;

@Component
public class AddressLinkAdder implements LinkAdder<Address> {

  @Override
  public void addLink(List<Address> addresses) {
    for (Address address : addresses) {
      Long id = address.getId();

      Link selfLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(AddressController.class).getAddress(id))
          .withSelfRel();

      Link editLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(AddressController.class).updateAddress(id, null))
          .withRel("edit");

      Link deleteLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(AddressController.class).deleteAddress(id))
          .withRel("delete");

      address.add(selfLink, editLink, deleteLink);
    }
  }

  @Override
  public void addLink(Address address) {
    Long id = address.getId();

    Link collectionLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(AddressController.class).getAllAddresses())
        .withRel("addresses");

    Link editLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(AddressController.class).updateAddress(id, null))
        .withRel("edit");

    Link deleteLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(AddressController.class).deleteAddress(id))
        .withRel("delete");

    address.add(collectionLink, editLink, deleteLink);
  }
}
