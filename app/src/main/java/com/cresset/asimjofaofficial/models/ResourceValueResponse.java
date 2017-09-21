package com.cresset.asimjofaofficial.models;

/**
 * Created by mehboob.khan on 9/20/2017.
 */

public class ResourceValueResponse {
    public String Message;
    public String Status;
    public String ResourceValue;

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

    public String getResourceValue() {
        return ResourceValue;
    }

    public void setResourceValue(String resourceValue) {
        ResourceValue = resourceValue;
    }
}
