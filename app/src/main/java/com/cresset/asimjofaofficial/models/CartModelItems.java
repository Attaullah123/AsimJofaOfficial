package com.cresset.asimjofaofficial.models;

import java.util.ArrayList;
import java.util.List;


public class CartModelItems {
    public String ProductName;
    public String ProductSku;
    public float ProductPrice;
    public float ProductOldPrice;
    public int Quantity;
    public int CartItemId;
    public String ImageLink;
    public boolean isSelected;

    public List<CartAttributes> Attributes;

    public List<CartAttributes> getAttributes() {
        return Attributes;
    }

    public void setAttributes(List<CartAttributes> attributes) {
        Attributes = attributes;
    }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductSku() {
        return ProductSku;
    }

    public void setProductSku(String productSku) {
        ProductSku = productSku;
    }

    public float getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public float getProductOldPrice() {
        return ProductOldPrice;
    }

    public void setProductOldPrice(int productOldPrice) {
        ProductOldPrice = productOldPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getCartItemId() {
        return CartItemId;
    }

    public void setCartItemId(int cartItemId) {
        CartItemId = cartItemId;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
