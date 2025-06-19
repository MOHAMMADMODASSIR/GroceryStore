package com.grocery.client.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "sections")
@Data
public class Sections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int sectionId;

    @Column(name = "section_name")
    private String sectionName;

    @OneToMany(mappedBy = "sections", cascade = CascadeType.ALL)
    private List<Products> products;

}
