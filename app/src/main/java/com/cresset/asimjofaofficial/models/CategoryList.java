package com.cresset.asimjofaofficial.models;

import java.util.ArrayList;


public class CategoryList {
    public String Id;
    public String Name;
    public String PictureURL;
    public ArrayList<ChildCategoryList> Child;

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

    public ArrayList<ChildCategoryList> getChild() {
        return Child;
    }

    public void setChild(ArrayList<ChildCategoryList> child) {
        Child = child;
    }

    public String getPictureURL() {
        return PictureURL;
    }

    public void setPictureURL(String pictureURL) {
        PictureURL = pictureURL;
    }
}
