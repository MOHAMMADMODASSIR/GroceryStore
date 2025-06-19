package com.grocery.client.service.impl;

import com.grocery.client.dao.ProductsRepository;
import com.grocery.client.entity.Products;
import com.grocery.client.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsRepository productsRepository;

    @Override
    public Products findById(int id) {
        Products product = productsRepository.findById(id).orElse(null);
        return product;
    }

    @Override
    public Products createProduct(Products product) {
        return productsRepository.save(product);
    }

    @Override
    public void deleteProductById(int productId) {
        productsRepository.deleteById(productId);
    }

    @Override
    public List<Products> fetchAllProducts() {
        List<Products> productsList = productsRepository.findAll();
        return productsList;
    }

    @Override
    public List<Products> fetchProductBySection(int id) {
        List<Products> productsList = productsRepository.findBySections_SectionId(id);
        return productsList;
    }

    @Override
    public List<Products> fetchSearchedProducts(String query) {
        List<Products> productsList = productsRepository.findByProductNameContaining(query);
        return productsList;
    }

    @Override
    public List<Products> fetchSearchedProductsBySectionId(String query, int sectionId) {
        List<Products> productsList = productsRepository.findBySections_SectionIdAndProductNameContaining(sectionId, query);
        return productsList;
    }
}
