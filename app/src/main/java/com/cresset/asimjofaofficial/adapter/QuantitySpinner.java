package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.ProductDetailSize;
import com.cresset.asimjofaofficial.models.QuantityModel;

import java.util.ArrayList;


public class QuantitySpinner extends ArrayAdapter<QuantityModel> {
    private Context context;
    private ArrayList<QuantityModel> quantityModel;
    public TextView quantity;

    public QuantitySpinner(Context context, int textViewResourceId, ArrayList<QuantityModel> quantityValue) {
        super(context, textViewResourceId, quantityValue);
        this.context = context;
    }

    public int getCount(){
        return quantityModel.size();
    }

    public QuantityModel getItem(int position){

        return quantityModel.get(position);
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
        View row = inflater.inflate(R.layout.quantity_spinner_value, parent, false);

        quantity = (TextView) row.findViewById(R.id.quantity_spinner);

        QuantityModel quantityModel = getItem(position);
        quantity.setText(quantityModel.getItem());
        return row;
    }

}
