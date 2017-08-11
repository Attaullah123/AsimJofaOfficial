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


public class CurrencyAdapter extends ArrayAdapter<CurrencyListModel> {
    private Context context;
    private ArrayList<CurrencyListModel> currencyListModels;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    CurrencyListModel listModel;
    Gson gson;


    public CurrencyAdapter(Context context, ArrayList<CurrencyListModel> currencyList){
        super(context, R.layout.currency_select_item, currencyList);
        this.context = context;
        this.currencyListModels = currencyList;
        //this.productPrice = productPrice;
    }
    private static class CheckboxHolder{
        public TextView currencyName;
        public CheckBox selectCurrency;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        CurrencyAdapter.CheckboxHolder holder = new CurrencyAdapter.CheckboxHolder();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.currency_select_item, null);

            holder.currencyName = (TextView) view.findViewById(R.id.curruncy_name);
            holder.selectCurrency = (CheckBox) view.findViewById(R.id.select_currency);

            sharedPreferencesEditor =  context.getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE).edit();
            sharedPreferences = context.getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE);

            //convertView.setTag(holder);
            //holder.addonCheckbox.setOnCheckedChangeListener(new ProductDetail());
            holder.selectCurrency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    CurrencyAdapter.CheckboxHolder holder = new CurrencyAdapter.CheckboxHolder();

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

//Toast.makeText(getContext(),"Check Clickhed ", Toast.LENGTH_LONG).show();
        } else {
            holder = (CurrencyAdapter.CheckboxHolder) view.getTag();
        }
        view.setTag(holder);

        listModel =currencyListModels.get(position);

        //holder.currencyName.setText(listModel.getId());
        holder.currencyName.setText(listModel.getName());
        //holder.price.setText(listModel.getPrice());
        holder.selectCurrency.setChecked(currencyListModels.get(position).isSelected);
        holder.selectCurrency.setTag(listModel);

        return view;
    }

    public void show(String message){
        Toast.makeText(context, message ,Toast.LENGTH_LONG ).show();
    }

    public void setCurrency(CurrencyListModel listModel){
        gson = new Gson();
        String json = gson.toJson(listModel);
        sharedPreferencesEditor.putString(Config.CurrencyPreference,json);
        sharedPreferencesEditor.commit();
        GlobalClass.currency = listModel;
        show(listModel.getName() + " currency selected!" );
    }
}
