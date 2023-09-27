package com.example.entity;

import lombok.Data;

/**
 * Adress
 *
 * @author xxx
 * @since 2023/9/20 16:52
 */
@Data
public class Address {
    private String province;
    private String city;
    private String district;
    private String detail;
}
