package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.ProductDetailSize;
import com.cresset.asimjofaofficial.models.ShippingModel;
import com.cresset.asimjofaofficial.models.StateList;

import java.util.ArrayList;


public class ShippingStateSpinnerAdapter extends ArrayAdapter<StateList> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<StateList> shippingStateModels;
    public TextView state;

    public ShippingStateSpinnerAdapter(Context context, int textViewResourceId, ArrayList<StateList> shippingStateModel) {
        super(context, textViewResourceId, shippingStateModel);
        this.context = context;
        this.shippingStateModels = shippingStateModel;
    }

    public int getCount(){
        return shippingStateModels.size();
    }

    public StateList getItem(int position){
        return shippingStateModels.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.spinner_billing_shipping_state, parent, false);

        state = (TextView) row.findViewById(R.id.spinner_state);
        StateList stateList = shippingStateModels.get(position);
        state.setText(stateList.toString());
        return row;
    }
}
