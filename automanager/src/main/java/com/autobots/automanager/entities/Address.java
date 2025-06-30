package com.autobots.automanager.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Address extends RepresentationModel<Address> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String state;

    @Column(nullable = false)
    private String city;

    @Column
    private String district;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    @Column(name = "zip_code")
    private String postalCode;

    @Column(name = "additional_info")
    private String additionalInfo;

    @OneToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
}
