package com.grocery.client.service;

import com.grocery.client.entity.Products;
import com.grocery.client.entity.Sections;

import java.util.List;

public interface ProductsService {

    Products findById(int id);

    Products createProduct(Products product);

    void deleteProductById(int productId);

    List<Products> fetchAllProducts();

    List<Products> fetchProductBySection(int id);

    List<Products> fetchSearchedProducts(String query);

    List<Products> fetchSearchedProductsBySectionId(String query, int sectionId);
}
