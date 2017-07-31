package com.cresset.asimjofaofficial.models;

/**
 * Created by Attaullah Khizar on 30/07/2017.
 */

public class UpdateProductQuantity {
    public String CartItemId;
    public String Quantity;
    public String IsRemoveItem;

    public String getCartItemId() {
        return CartItemId;
    }

    public void setCartItemId(String cartItemId) {
        CartItemId = cartItemId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getIsRemoveItem() {
        return IsRemoveItem;
    }

    public void setIsRemoveItem(String isRemoveItem) {
        IsRemoveItem = isRemoveItem;
    }
}
