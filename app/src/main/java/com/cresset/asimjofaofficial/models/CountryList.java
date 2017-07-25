package com.cresset.asimjofaofficial.models;

import java.util.ArrayList;

/**
 * Created by Attaullah Khizar on 20/06/2017.
 */

public class CountryList {
    public String Id;
    public String Name;
    public int DisplayOrder;
    public boolean AllowsBilling;
    public boolean AllowsShipping;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getDisplayOrder() {
        return DisplayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        DisplayOrder = displayOrder;
    }

    public boolean isAllowsBilling() {
        return AllowsBilling;
    }

    public void setAllowsBilling(boolean allowsBilling) {
        AllowsBilling = allowsBilling;
    }

    public boolean isAllowsShipping() {
        return AllowsShipping;
    }

    public void setAllowsShipping(boolean allowsShipping) {
        AllowsShipping = allowsShipping;
    }
}
