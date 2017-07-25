package com.cresset.asimjofaofficial.models;

import java.util.List;

/**
 * Created by Attaullah Khizar on 20/06/2017.
 */

public class StateModel {
    String Status;
    String Message;
    public List<StateList> StateProvinceList;

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

    public List<StateList> getStateList() {
        return StateProvinceList;
    }

    public void setStateList(List<StateList> stateProvinceList) {
        StateProvinceList = stateProvinceList;
    }
}
