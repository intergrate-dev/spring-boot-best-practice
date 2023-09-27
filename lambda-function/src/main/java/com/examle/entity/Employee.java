package com.example.entity;

import lombok.Data;

/**
 * Employee
 *
 * @author xxx
 * @since 2023/9/20 14:16
 */
@Data
public class Employee {
    private int employNo;
    private String name;
    private int age;
    private int workAge;
    private int holidayCount;

    private String firstName;
    private String lastName;
    private Communication communication;
    private Address address;

    public Employee(int employNo, String name, int age, int workAge) {
        this.employNo = employNo;
        this.name = name;
        this.age = age;
        this.workAge = workAge;
    }

    public Employee() {}
}
