package com.cresset.asimjofaofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cresset.asimjofaofficial.HomeActivity;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.ProductDetailSize;

import java.util.ArrayList;


public class SizeSpinnerAdapter extends ArrayAdapter<ProductDetailSize> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<ProductDetailSize> values;
    public TextView size,mappingId,attributeId;

    public SizeSpinnerAdapter(Context context, int textViewResourceId, ArrayList<ProductDetailSize> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public ProductDetailSize getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.spinner_size_value, parent, false);

        size = (TextView) row.findViewById(R.id.spinner_size);
        mappingId = (TextView) row.findViewById(R.id.size_mapping_id);
        attributeId = (TextView) row.findViewById(R.id.size_attribute_id);

        ProductDetailSize productDetialSize = values.get(position);
        size.setText(productDetialSize.getSizeName());
        mappingId.setText(String.valueOf(productDetialSize.getProductMappingAttributeId()));
        attributeId.setText(String.valueOf(productDetialSize.getProductAttributeValueId()));

        return row;

    }

}
