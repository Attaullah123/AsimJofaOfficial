package com.cresset.asimjofaofficial.models;

public class CartDetailModel {
    public float SubTotalAmount;
    public float DiscountAmount;
    public float TotalAmount;

 /*   public List<CartModelItems> CartItems;

    public List<CartModelItems> getCartItems() {
        return CartItems;
    }

    public void setCartItems(List<CartModelItems> cartItems) {
        CartItems = cartItems;
    }*/

    public float getSubTotalAmount() {
        return SubTotalAmount;
    }

    public void setSubTotalAmount(float subTotalAmount) {
        SubTotalAmount = subTotalAmount;
    }

    public float getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        DiscountAmount = discountAmount;
    }

    public float getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        TotalAmount = totalAmount;
    }
}
