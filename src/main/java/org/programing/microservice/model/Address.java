package org.programing.microservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "t_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
