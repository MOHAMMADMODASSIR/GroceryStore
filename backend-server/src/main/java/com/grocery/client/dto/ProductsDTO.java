package com.grocery.client.dto;

import lombok.Data;

@Data
public class ProductsDTO {

    private int id;
    private String product_name;
    private String manufacture_date;
    private String expiry_date;
    private int rate;
    private String unit;
    private int quantity;
    private int section_id;

}
