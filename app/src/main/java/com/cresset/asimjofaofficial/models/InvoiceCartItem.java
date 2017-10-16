package com.cresset.asimjofaofficial.models;

import java.util.List;

/**
 * Created by attaullahkhizar on 10/14/17.
 */

public class InvoiceCartItem {
    public String ProductName;
    public String ProductSku;
    public String ProductPrice;
    public float ProductOldPrice;
    public String Quantity;
    public int CartItemId;
    public String ImageLink;
    public String AttributeDescription;


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

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public float getProductOldPrice() {
        return ProductOldPrice;
    }

    public void setProductOldPrice(int productOldPrice) {
        ProductOldPrice = productOldPrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
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

    public String getAttributeDescription() {
        return AttributeDescription;
    }

    public void setAttributeDescription(String attributeDescription) {
        AttributeDescription = attributeDescription;
    }
}
