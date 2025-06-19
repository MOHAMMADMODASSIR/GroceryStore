package com.grocery.client.controller;

import com.grocery.client.dto.CartsDTO;
import com.grocery.client.dto.OrdersDTO;
import com.grocery.client.entity.Orders;
import com.grocery.client.entity.Products;
import com.grocery.client.entity.User;
import com.grocery.client.service.OrdersService;
import com.grocery.client.service.ProductsService;
import com.grocery.client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class OrdersController {

    @Autowired
    OrdersService ordersService;
    @Autowired
    UserService userService;
    @Autowired
    ProductsService productsService;

    @PostMapping("/placeOrder")
    private ResponseEntity<Map<String, String>> placeOrder(ArrayList<CartsDTO> cartsDTO) {
        System.out.println("Entered");
        System.out.println(cartsDTO.size());
        for(int i=0;i<cartsDTO.size();i++) {
            System.out.println(cartsDTO.get(i).getId()+" "+cartsDTO.get(i).getProduct_name());
        }
        Orders order = new Orders();
        order.setOrderDate(LocalDateTime.now());
        User user = userService.searchUserByEmail("a@gmail.com");
        if(user==null){
            System.out.println("User not found");
            return new ResponseEntity<>(
                    Map.of("message", "User not found"),
                    HttpStatus.OK
            );
        }
        order.setUser(user);
        List<Products> productsList = new ArrayList<>();
        for (CartsDTO cart : cartsDTO) {
            Products product = productsService.findById(cart.getId());
            if (product != null) {
                // Optionally check stock quantity and reduce it
                productsList.add(product);
            }
        }

        order.setProducts(productsList);
        ordersService.storeOrders(order);
        return new ResponseEntity<>(
                Map.of("message", "Order Placed"),
                HttpStatus.OK
        );
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, List<OrdersDTO>>> fetchAllOrders() {
        List<Orders> ordersList = ordersService.findAll();
        List<OrdersDTO> ordersDTOList = getOrdersDTOS(ordersList);
        return new ResponseEntity<>(
                Map.of("data", ordersDTOList),
                HttpStatus.OK
        );
    }

    @PostMapping("/order")
    public ResponseEntity<Map<String, List<OrdersDTO>>> fetchUserOrders(@RequestBody String theEmail) {
        User user = userService.searchUserByEmail(theEmail);
        if(user != null) {
            List<Orders> ordersList = ordersService.fetchUserOrders(user.getId());
            List<OrdersDTO> ordersDTOList = getOrdersDTOS(ordersList);
            return new ResponseEntity<>(
                    Map.of("data", ordersDTOList),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                Map.of("message", null),
                HttpStatus.NOT_FOUND
        );
    }

    private static List<OrdersDTO> getOrdersDTOS(List<Orders> ordersList) {
        List<OrdersDTO> ordersDTOList = new ArrayList<>();

        for (Orders order : ordersList) {
            for (Products product : order.getProducts()) {
                OrdersDTO dto = new OrdersDTO();
                dto.setProduct_id(product.getId());
                dto.setProduct_name(product.getProductName());
                dto.setPrice(product.getRate());
                dto.setQuantity(product.getQuantity());
                dto.setUnit(Integer.parseInt(product.getUnit()));
                ordersDTOList.add(dto);
            }
        }
        return ordersDTOList;
    }


}
