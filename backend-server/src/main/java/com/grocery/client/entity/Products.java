package com.grocery.client.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "manufacture_date")
    private String manufactureDate;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "rate")
    private int rate;

    @Column(name = "unit")
    private String unit;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Sections sections;

    @ManyToMany(mappedBy = "products")
    private List<Orders> orders = new ArrayList<>();

}
