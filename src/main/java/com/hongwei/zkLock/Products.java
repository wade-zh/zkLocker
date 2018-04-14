package com.hongwei.zkLock;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Products {
    public static Map<Long, Integer> getProducts() {
        return products;
    }

    public static void setProducts(Map<Long, Integer> products) {
        products = products;
    }

    private static Map<Long,Integer> products = new HashMap<>();

}
