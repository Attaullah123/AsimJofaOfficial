package com.cresset.asimjofaofficial.models;

import java.util.List;

/**
 * Created by cresset on 20/06/2017.
 */

public class CategoryIndexImageModel {
    public String Status;
    public String Message;

    public List<IndexImage> IndexImagesList;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<IndexImage> getIndexImagesList() {
        return IndexImagesList;
    }

    public void setIndexImagesList(List<IndexImage> indexImagesList) {
        IndexImagesList = indexImagesList;
    }
}
