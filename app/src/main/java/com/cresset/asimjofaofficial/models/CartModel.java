package com.cresset.asimjofaofficial.models;

import java.util.ArrayList;
import java.util.List;


public class CartModel {
    public String Message;
    public String Status;

    public CartDetailModel TotalDetail;

    public List<CartModelItems> CartItems;

    public List<CartModelItems> getCartItems() {
        return CartItems;
    }

    public void setCartItems(List<CartModelItems> cartItems) {
        CartItems = cartItems;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public CartDetailModel getTotalDetail() {
        return TotalDetail;
    }

    public void setTotalDetail(CartDetailModel totalDetail) {
        TotalDetail = totalDetail;
    }

}
