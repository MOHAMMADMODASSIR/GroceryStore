package com.grocery.client.dto;

import lombok.Data;

@Data
public class CartsDTO {

    private int id;
    private String product_name;
    private int price;
    private int quantity;
    private int unit;
    private int product_id;
    private int user_id;

}
