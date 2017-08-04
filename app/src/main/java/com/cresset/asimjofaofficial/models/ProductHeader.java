package com.cresset.asimjofaofficial.models;

import java.util.List;

/**
 * Created by cresset on 02/08/2017.
 */

public class ProductHeader {
    public String name;
    public List<CartModelItems> CartItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CartModelItems> getCartItems() {
        return CartItems;
    }

    public void setCartItems(List<CartModelItems> cartItems) {
        CartItems = cartItems;
    }
}
