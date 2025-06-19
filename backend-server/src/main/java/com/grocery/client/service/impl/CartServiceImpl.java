package com.grocery.client.service.impl;

import com.grocery.client.dao.CartRepository;
import com.grocery.client.dao.ProductsRepository;
import com.grocery.client.entity.Cart;
import com.grocery.client.entity.Products;
import com.grocery.client.entity.User;
import com.grocery.client.service.CartService;
import com.grocery.client.service.ProductsService;
import com.grocery.client.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductsRepository productsRepository;

    @Override
    public Cart createUserCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> fetchUserCarts(int userId) {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        return cartList;
    }

    @Override
    public Cart findCartFromUserProductId(int userId, int productId) {
        Cart userCart = cartRepository.findByUserIdAndProductId(userId, productId);
        return userCart;
    }

    @Override
    @Transactional
    public String addToCart(String email, int productId) {
        User user = userService.searchUserByEmail(email);
        Products product = productsRepository.findById(productId).orElse(null);
        if(user==null || product == null) {
            return "User or product not found";
        }
        if(product.getQuantity()<=0) {
            return "Product out of stock";
        }
        Cart existingCart = cartRepository.findByUserIdAndProductId(user.getId(), productId);
        if(existingCart == null) {
            Cart newCart = new Cart();
            newCart.setUserId(user.getId());
            newCart.setProduct(product);
            newCart.setQuantity(1);
            newCart.setPrice(product.getRate());
            cartRepository.save(newCart);
        }
        else {
            existingCart.setQuantity(existingCart.getQuantity() + 1);
            cartRepository.save(existingCart);
        }
        product.setQuantity(product.getQuantity() - 1);
        productsRepository.save(product);
        return "Item added to cart successfully";
    }
}
