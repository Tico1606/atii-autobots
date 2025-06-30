package com.autobots.automanager.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class IdentityDocument extends RepresentationModel<IdentityDocument> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @Column(unique = true)
    private String number;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
}
