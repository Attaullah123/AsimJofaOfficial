package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.CountryList;

import java.util.ArrayList;

/**
 * Created by cresset on 19/06/2017.
 */

public class BillingCountrySpinnerAdapter extends ArrayAdapter<CountryList> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<CountryList> billingCountryModels;
    public TextView country;

    public BillingCountrySpinnerAdapter(Context context, int textViewResourceId, ArrayList<CountryList> billingCountryModel) {
        super(context, textViewResourceId, billingCountryModel);
        this.context = context;
        this.billingCountryModels = billingCountryModel;
    }

    public int getCount(){
        return billingCountryModels.size();
    }

    public CountryList getItem(int position){
        return billingCountryModels.get(position);
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
        View row=inflater.inflate(R.layout.spinner_billing_shipping_country, parent, false);

        country = (TextView) row.findViewById(R.id.spinner_country);
        CountryList countryList = billingCountryModels.get(position);
        country.setText(countryList.getName());
        return row;

    }
}
