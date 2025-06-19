package com.grocery.client.controller;

import com.grocery.client.dto.ProductsDTO;
import com.grocery.client.entity.Products;
import com.grocery.client.service.ProductsService;
import com.grocery.client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserProductsController {

    @Autowired
    ProductsService productsService;
    @Autowired
    UserService userService;

    @GetMapping("/products")
    private ResponseEntity<Map<String, List<ProductsDTO>>> fetchAllProducts() {
        System.out.println("Products Retrieved");
        List<Products> productsList = productsService.fetchAllProducts();
        List<ProductsDTO> productsDTOList = getProductsDTOS(productsList);
        return new ResponseEntity<>(
                Map.of("data", productsDTOList),
                HttpStatus.OK
        );
    }

    @PostMapping("/products_by_section")
    private ResponseEntity<Map<String, List<ProductsDTO>>> fetchProductBySectionId(@RequestBody String theId) {
        System.out.println(theId+" received");
        int id = Integer.parseInt(theId);
        List<Products> productsList = productsService.fetchProductBySection(id);
        if(productsList == null) {
            return new ResponseEntity<>(
                    Map.of("message", null),
                    HttpStatus.NOT_FOUND
            );
        }
        List<ProductsDTO> productsDTOList = getProductsDTOS(productsList);
        return new ResponseEntity<>(
                Map.of("data", productsDTOList),
                HttpStatus.OK
        );
    }

    @PostMapping("/search_products")
    private ResponseEntity<Map<String, List<ProductsDTO>>> searchedProductList(@RequestBody String query) {
        System.out.println("Search products query "+query);
        List<Products> productsList = productsService.fetchSearchedProducts(query);
        List<ProductsDTO> productsDTOList = getProductsDTOS(productsList);
        return new ResponseEntity<>(
                Map.of("data", productsDTOList),
                HttpStatus.OK
        );
    }

    @PostMapping("/search_products_by_section")
    private ResponseEntity<Map<String, List<ProductsDTO>>> searchedProductListBySectionId(@RequestBody Map<String, Object> requestBody) {
        String query = requestBody.get("query").toString();
        String theSectionId = requestBody.get("id").toString();
        int sectionId = Integer.parseInt(theSectionId);
        System.out.println("Search products query "+query+" "+sectionId);
        List<Products> productsList = productsService.fetchSearchedProductsBySectionId(query, sectionId);
        List<ProductsDTO> productsDTOList = getProductsDTOS(productsList);
        return new ResponseEntity<>(
                Map.of("data", productsDTOList),
                HttpStatus.OK
        );
    }

    private static List<ProductsDTO> getProductsDTOS(List<Products> productsList) {
        List<ProductsDTO> productsDTOList = new ArrayList<>();
        for(Products products: productsList) {
            ProductsDTO productsDTO = new ProductsDTO();
            productsDTO.setId(products.getId());
            productsDTO.setProduct_name(products.getProductName());
            productsDTO.setManufacture_date(products.getManufactureDate());
            productsDTO.setExpiry_date(products.getExpiryDate());
            productsDTO.setRate(products.getRate());
            productsDTO.setUnit(products.getUnit());
            productsDTO.setSection_id(products.getSections().getSectionId());
            productsDTO.setQuantity(products.getQuantity());
            productsDTOList.add(productsDTO);
        }
        return productsDTOList;
    }

}
