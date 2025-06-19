package com.grocery.client.controller;

import com.grocery.client.dto.OrdersDTO;
import com.grocery.client.entity.Cart;
import com.grocery.client.entity.Products;
import com.grocery.client.entity.User;
import com.grocery.client.service.CartService;
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
public class CartController {

    @Autowired
    UserService userService;
    @Autowired
    ProductsService productsService;
    @Autowired
    CartService cartService;

    @PostMapping("/addToCart")
    private ResponseEntity<Map<String,String>> add_To_Cart(@RequestBody Map<String, Object> requestBody) {
        String email = requestBody.get("email").toString();
        int productId = Integer.parseInt(requestBody.get("productId").toString());
        System.out.println(email+" "+productId);

        String response = cartService.addToCart(email, productId);

        if (response.equals("Item added to cart successfully")) {
            return ResponseEntity.ok(Map.of("message", "Item added to cart successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message","Error adding to cart"));
        }
    }

    @PostMapping("/carts")
    private ResponseEntity<Map<String, List<OrdersDTO>>> fetchUserCarts(@RequestBody String email) {
        User user = userService.searchUserByEmail(email);
        if(user==null) {
            return new ResponseEntity<>(
                    Map.of("message", null),
                    HttpStatus.NOT_FOUND
            );
        }
        List<Cart> cartList = cartService.fetchUserCarts(user.getId());
        List<OrdersDTO> ordersDTOList = getOrdersDTOS(cartList);
        return new ResponseEntity<>(
                Map.of("message", ordersDTOList),
                HttpStatus.OK
        );
    }

    private static List<OrdersDTO> getOrdersDTOS(List<Cart> cartList) {
        List<OrdersDTO> ordersDTOList = new ArrayList<>();
        for(Cart cart: cartList) {
            OrdersDTO ordersDTO = new OrdersDTO();
            ordersDTO.setProduct_id(cart.getProduct().getId());
            ordersDTO.setQuantity(cart.getQuantity());
            ordersDTO.setProduct_name(cart.getProduct().getProductName());
            ordersDTO.setPrice(cart.getPrice());
            ordersDTO.setUser_id(cart.getUserId());
            ordersDTO.setProduct_stock(cart.getProduct().getQuantity());
            ordersDTOList.add(ordersDTO);
        }
        return ordersDTOList;
    }
}
