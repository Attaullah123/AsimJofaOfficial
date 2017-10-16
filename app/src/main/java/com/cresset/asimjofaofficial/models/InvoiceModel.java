package com.cresset.asimjofaofficial.models;

import java.util.List;


public class InvoiceModel {
    public String Message;
    public String Status;


    public InvoiceOrderDetailModel OrderDetail;


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

    public InvoiceOrderDetailModel getOrderDetail() {
        return OrderDetail;
    }

    public void setOrderDetail(InvoiceOrderDetailModel orderDetail) {
        OrderDetail = orderDetail;
    }


}
