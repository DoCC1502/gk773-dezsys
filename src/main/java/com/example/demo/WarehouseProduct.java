package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // erzeugt Getter, Setter, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseProduct {
    private String productID;
    private String productName;
    private String productCategory;
    private int productQuantity;
    private String productUnit;
}
