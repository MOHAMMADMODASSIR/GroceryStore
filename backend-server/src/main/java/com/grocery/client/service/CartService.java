package com.grocery.client.service;

import com.grocery.client.entity.Cart;
import com.grocery.client.entity.User;

import java.util.List;

public interface CartService {

    public Cart createUserCart(Cart cart);

    public List<Cart> fetchUserCarts(int userId);

    public Cart findCartFromUserProductId(int userId, int productId);

    public String addToCart(String email, int productId);

}
