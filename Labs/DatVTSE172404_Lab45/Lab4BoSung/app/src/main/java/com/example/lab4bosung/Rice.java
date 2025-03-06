package com.example.lab4bosung;

import java.io.Serializable;

public class Rice implements Serializable {
    private String name;
    private int price;

    public Rice(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
