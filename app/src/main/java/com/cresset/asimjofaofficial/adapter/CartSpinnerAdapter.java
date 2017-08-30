package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.CartModelItems;
import com.cresset.asimjofaofficial.models.ProductDetailSize;

import java.util.ArrayList;



public class CartSpinnerAdapter extends ArrayAdapter<CartModelItems> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<CartModelItems> modelItemses;
    public TextView name, price;
    public ImageView imageView;

    public CartSpinnerAdapter(Context context ,int resouceId, ArrayList<CartModelItems> values) {
        super(context, resouceId, values);
        this.context = context;
        this.modelItemses = values;
    }

    public int getCount() {
        return modelItemses.size();
    }

    public CartModelItems getItem(int position) {
        return modelItemses.get(position);
    }

    public long getItemId(int position) {
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
        View row = inflater.inflate(R.layout.spinner_cart_detail, parent, false);

        name = (TextView) row.findViewById(R.id.product_name);
        price = (TextView) row.findViewById(R.id.product_cart_price);
        imageView = (ImageView) row.findViewById(R.id.cart_thumbnail);

        CartModelItems cartModelItems = modelItemses.get(position);
        name.setText(cartModelItems.getProductName());
        float total = cartModelItems.getProductPrice();
        price.setText(Float.toString(total));

        Glide.with(context).load(cartModelItems.getImageLink()).into(imageView);
        return row;

    }
}