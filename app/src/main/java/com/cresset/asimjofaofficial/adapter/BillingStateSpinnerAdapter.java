package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.BillingModel;
import com.cresset.asimjofaofficial.models.CountryList;
import com.cresset.asimjofaofficial.models.StateList;
import com.cresset.asimjofaofficial.models.StateModel;

import java.util.ArrayList;


public class BillingStateSpinnerAdapter extends ArrayAdapter<StateList> {

    // Your sent context
    private Context context;
    private ArrayList<StateList> billingStateModels;
    public TextView state;

    public BillingStateSpinnerAdapter(Context context, int textViewResourceId, ArrayList<StateList> billingStateModel) {
        super(context, textViewResourceId, billingStateModel);
        this.context = context;
        this.billingStateModels = billingStateModel;
    }

    public int getCount(){
        return billingStateModels.size();
    }

    public StateList getItem(int position){
        return billingStateModels.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.spinner_billing_shipping_state, parent, false);

        state = (TextView) row.findViewById(R.id.spinner_state);
        StateList stateList = billingStateModels.get(position);
        state.setText(stateList.toString());
        return row;
    }

//    public View getCustomView(int position, View convertView, ViewGroup parent) {
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row=inflater.inflate(R.layout.spinner_billing_shipping_state, parent, false);
//
//        state = (TextView) row.findViewById(R.id.spinner_state);
//        StateList stateList = billingStateModels.get(position);
//        state.setText(stateList.getName());
//        return row;
//
//    }
}
