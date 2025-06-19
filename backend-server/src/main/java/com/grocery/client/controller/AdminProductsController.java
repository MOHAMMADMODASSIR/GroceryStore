package com.grocery.client.controller;

import com.grocery.client.dto.ProductsDTO;
import com.grocery.client.entity.Products;
import com.grocery.client.entity.Sections;
import com.grocery.client.service.ProductsService;
import com.grocery.client.service.SectionsService;
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
@RequestMapping("/admin")
public class AdminProductsController {

    @Autowired
    ProductsService productsService;
    @Autowired
    SectionsService sectionsService;

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

    @PostMapping("/addProduct")
    private ResponseEntity<Map<String, String>> addProduct(@RequestBody ProductsDTO addProduct) {
        System.out.println(addProduct.getId()+" "+addProduct.getProduct_name()+" "+addProduct.getSection_id());
        if(productsService.findById(addProduct.getId()) == null) {
            Sections section = sectionsService.searchSection(addProduct.getSection_id());
            if (section == null) {
                return new ResponseEntity<>(
                        Map.of("message","Section not found"),
                        HttpStatus.NOT_FOUND
                );
            }
            Products newProduct = getProducts(addProduct, section);
            productsService.createProduct(newProduct);
            return new ResponseEntity<>(
                    Map.of("message", "Products added succesfully"),
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<>(
                    Map.of("message", "Products already exists"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping("/editProduct")
    private ResponseEntity<Map<String,String>> editProduct(@RequestBody ProductsDTO product) {
        if(productsService.findById(product.getId())!=null) {
            Sections section = sectionsService.searchSection(product.getSection_id());
            if(section!=null) {
               Products editProduct = getProducts(product, section);
               productsService.createProduct(editProduct);
               return new ResponseEntity<>(
                       Map.of("message", "Product edit success"),
                       HttpStatus.OK
               );
            }
            else {
                return new ResponseEntity<>(
                        Map.of("message", "section id does not exist"),
                        HttpStatus.NOT_FOUND
                );
            }
        }
        else {
            return new ResponseEntity<>(
                    Map.of("message", "product id does not exist"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    private ResponseEntity<Map<String,String>> deleteProduct(@PathVariable int id) {
        System.out.println(id);
        if(productsService.findById(id) != null) {
            productsService.deleteProductById(id);
            return new ResponseEntity<>(
                    Map.of("message", "Deleted"),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                Map.of("message", "Error"),
                HttpStatus.NOT_FOUND
        );
    }

    private static Products getProducts(ProductsDTO addProduct, Sections section) {
        Products newProduct = new Products();
        newProduct.setId(addProduct.getId());
        newProduct.setProductName(addProduct.getProduct_name());
        newProduct.setManufactureDate(addProduct.getManufacture_date());
        newProduct.setExpiryDate(addProduct.getExpiry_date());
        newProduct.setRate(addProduct.getRate());
        newProduct.setUnit(addProduct.getUnit());
        newProduct.setQuantity(addProduct.getQuantity());
        newProduct.setSections(section);
        return newProduct;
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
