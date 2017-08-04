package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.CurrencyListModel;
import com.cresset.asimjofaofficial.models.ProductAddons;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.productdetail.ProductDetail;

import java.util.ArrayList;
import java.util.Locale;


public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<CurrencyListModel> currencyListModels;

//    public String productName;
//    private ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView currencyName;
        public CheckBox selectCurrency;

        public MyViewHolder(View itemView) {
            super(itemView);
            currencyName = (TextView) itemView.findViewById(R.id.curruncy_name);
            selectCurrency = (CheckBox) itemView.findViewById(R.id.select_currency);
            //overflow = (ImageView) view.findViewById(R.id.overflow);
        }

    }
    public CurrencyAdapter(Context mContext, ArrayList<CurrencyListModel> currencyList) {
        this.mContext = mContext;
        this.currencyListModels = currencyList;
    }
    @Override
    public CurrencyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_select_item, parent, false);

        return new CurrencyAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final CurrencyAdapter.MyViewHolder holder, final int position) {
        CurrencyListModel listModel = currencyListModels.get(position);

        //holder.currencyName.setText(listModel.getId());
        holder.currencyName.setText(listModel.getName());
        //holder.price.setText(listModel.getPrice());
        holder.selectCurrency.setChecked(currencyListModels.get(position).isSelected);

        holder.selectCurrency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    Toast.makeText(mContext, "select currency" ,Toast.LENGTH_LONG ).show();
                }else {
                    Toast.makeText(mContext, "UnSelect currency" ,Toast.LENGTH_LONG ).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyListModels.size();
    }


}
