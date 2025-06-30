package com.autobots.automanager.models;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entities.Customer;

@Component
public class CustomerSelector {
    public Customer selectById(List<Customer> users, long id) {
        for (Customer user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
