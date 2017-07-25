package com.cresset.asimjofaofficial.models;

import java.util.ArrayList;

/**
 * Created by cresset on 20/06/2017.
 */

public class IndexImage {
    public String Id;
    public String Name;
    public String PictureURL;
    public String DisplayOrder;
    public String CategoryId;
    public String CategoryName;

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

    public String getDisplayOrder() {
        return DisplayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        DisplayOrder = displayOrder;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
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
