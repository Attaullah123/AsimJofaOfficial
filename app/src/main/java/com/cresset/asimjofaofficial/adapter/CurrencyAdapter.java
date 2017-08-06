package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.productdetail.ProductDetail;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<CurrencyListModel> currencyListModels;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    CurrencyListModel listModel;
    Gson gson;

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

        sharedPreferencesEditor =  mContext.getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE).edit();
        sharedPreferences = mContext.getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE);

        return new CurrencyAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final CurrencyAdapter.MyViewHolder holder, final int position) {
        listModel = currencyListModels.get(position);

        //holder.currencyName.setText(listModel.getId());
        holder.currencyName.setText(listModel.getName());
        //holder.price.setText(listModel.getPrice());
        holder.selectCurrency.setChecked(currencyListModels.get(position).isSelected);

        holder.selectCurrency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listModel = currencyListModels.get(position);
                if (buttonView.isChecked()){
                    if(GlobalClass.currency != null){
                        if(listModel.getId() == GlobalClass.currency.getId()){
                            holder.selectCurrency.setChecked(true);
                            show("Please choose different currency!");
                        }
                        else{
                            //holder.
                            setCurrency(listModel);
                        }
                    }
                    else{
                        setCurrency(listModel);
                    }
                }else {
                    if(listModel.getId() == GlobalClass.currency.getId()){
                        holder.selectCurrency.setChecked(true);
                        show("Please choose different currency!");
                    }
                }
            }
        });
    }

    public void  setCurrency(CurrencyListModel listModel){
        gson = new Gson();
        String json = gson.toJson(listModel);
        sharedPreferencesEditor.putString(Config.CurrencyPreference,json);
        sharedPreferencesEditor.commit();
        GlobalClass.currency = listModel;
        show(listModel.getName() + " currency selected!" );
    }

    @Override
    public int getItemCount() {
        return currencyListModels.size();
    }

    public void show(String message){
        Toast.makeText(mContext, message ,Toast.LENGTH_LONG ).show();
    }
}
