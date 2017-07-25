package com.cresset.asimjofaofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.ProductAddons;
import com.cresset.asimjofaofficial.productdetail.ProductDetail;

import java.util.ArrayList;


public class AddonsAdapter extends ArrayAdapter<ProductAddons> {
    private ArrayList<ProductAddons> productAddons;
    private Context mContext;
    private TextView productPrice;
    public static ArrayList<ProductAddons> selectedProductAddons;


    public AddonsAdapter(Context context, ArrayList<ProductAddons> addons,TextView productPrice){
        super(context, R.layout.addons_detial, addons);
        this.mContext = context;
        this.productAddons = addons;
        this.productPrice = productPrice;
    }

    private static class CheckboxHolder{
        public TextView addonPrices;
        public TextView addonName;
        public TextView mappingId;
        public TextView mappingAttribute;
        public CheckBox addonCheckbox;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        CheckboxHolder holder = new CheckboxHolder();
        selectedProductAddons = new ArrayList<ProductAddons>();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.addons_detial, null);



            holder.addonPrices = (TextView) view.findViewById(R.id.addons_price);
            holder.mappingId = (TextView) view.findViewById(R.id.addons_mapping_id);
            holder.mappingAttribute = (TextView) view.findViewById(R.id.addons_attribute_id);
            holder.addonName = (TextView) view.findViewById(R.id.addons_text);
            holder.addonCheckbox = (CheckBox) view.findViewById(R.id.addon_checkbox);

            //convertView.setTag(holder);
            //holder.addonCheckbox.setOnCheckedChangeListener(new ProductDetail());
            holder.addonCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    float price = Float.parseFloat(productPrice.getText().toString());
                    ProductAddons checkAddons = productAddons.get(position);
                    float addonPrice = Float.parseFloat(checkAddons.getAddonsPrice().toString());

                    if (buttonView.isChecked()){
                        selectedProductAddons.add(checkAddons);
                        productPrice.setText(Float.toString(addonPrice + price));
                        checkAddons.setSelected(true);

                        Toast.makeText(getContext(),"value add " , Toast.LENGTH_LONG).show();
                    }else{
                        selectedProductAddons.remove(checkAddons);
                        productPrice.setText(Float.toString(price - addonPrice));
                        checkAddons.setSelected(false);
                        Toast.makeText(getContext(),"value reversed", Toast.LENGTH_LONG).show();
                    }

                }
            });
//Toast.makeText(getContext(),"Check Clickhed ", Toast.LENGTH_LONG).show();
        } else {
            holder = (CheckboxHolder) view.getTag();
        }
        view.setTag(holder);

        ProductAddons p = productAddons.get(position);
        holder.addonName.setText( p.getAddonsName());
//        holder.mappingId.setText(p.getProductMappingAttributeId());
       // holder.mappingAttribute.setText(p.getProductMappingAttributeId());
        holder.addonPrices.setText(p.getAddonsPrice());
        holder.addonCheckbox.setChecked(p.isSelected());
        holder.addonCheckbox.setTag(p);

        return view;
    }


}
