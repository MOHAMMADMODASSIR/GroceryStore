package com.grocery.client.dto;

import lombok.Data;

@Data
public class OrdersDTO {

    private int product_id;
    private int user_id;
    private String product_name;
    private int price;
    private int quantity;
    private int product_stock;
    private int unit;

}
