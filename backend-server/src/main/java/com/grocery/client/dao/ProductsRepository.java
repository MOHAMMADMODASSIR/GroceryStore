package com.grocery.client.dao;

import com.grocery.client.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Integer> {

    List<Products> findAll();

    List<Products> findBySections_SectionId(int id);

    List<Products> findByProductNameContaining(String query);

    List<Products> findBySections_SectionIdAndProductNameContaining(int id, String query);
}
