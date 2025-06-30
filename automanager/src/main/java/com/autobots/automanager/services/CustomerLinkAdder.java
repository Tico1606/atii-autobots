package com.autobots.automanager.services;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controllers.CustomerController;
import com.autobots.automanager.entities.Customer;

@Component
public class CustomerLinkAdder implements LinkAdder<Customer> {

  @Override
  public void addLink(List<Customer> users) {
    for (Customer user : users) {
      Long id = user.getId();

      Link selfLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomer(id))
          .withSelfRel();

      Link editLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(CustomerController.class).updateCustomer(id, null))
          .withRel("edit");

      Link deleteLink = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(CustomerController.class).deleteCustomer(id))
          .withRel("delete");

      user.add(selfLink, editLink, deleteLink);
    }
  }

  @Override
  public void addLink(Customer user) {
    Long id = user.getId();

    Link collectionLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers())
        .withRel("users");

    Link editLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(CustomerController.class).updateCustomer(id, null))
        .withRel("edit");

    Link deleteLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(CustomerController.class).deleteCustomer(id))
        .withRel("delete");

    user.add(collectionLink, editLink, deleteLink);
  }
}
