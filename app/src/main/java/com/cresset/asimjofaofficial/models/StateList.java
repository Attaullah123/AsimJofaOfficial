package com.cresset.asimjofaofficial.models;

import java.util.ArrayList;


public class StateList {
    public int Id;
    public String Name;
    public int DisplayOrder;

    //set constructor here as a string
    public String toString(){
        return getName();
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
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
}
