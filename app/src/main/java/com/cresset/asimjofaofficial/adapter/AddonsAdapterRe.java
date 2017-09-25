package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.ProductAddons;
import com.cresset.asimjofaofficial.utilities.GlobalClass;

import java.util.ArrayList;
import java.util.List;


public class AddonsAdapterRe extends RecyclerView.Adapter<AddonsAdapterRe.ViewHolder> {

    private ArrayList<ProductAddons> productAddons;
    private Context mContext;
    private TextView productPrice;
    public static ArrayList<ProductAddons> selectedProductAddons;


    public AddonsAdapterRe(Context context, ArrayList<ProductAddons> addons,TextView productPrice){
        this.mContext = context;
        this.productAddons = addons;
        this.productPrice = productPrice;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.addons_detial, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindData(productAddons.get(position));
        selectedProductAddons = new ArrayList<ProductAddons>();
        GlobalClass.selectedProductAddons = new ArrayList<ProductAddons>();
        //in some cases, it will prevent unwanted situations
        //holder.addonCheckbox.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
//        holder.addonCheckbox.setChecked(selectedProductAddons.get(position).isSelected());

        holder.addonCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                float price = Float.parseFloat(productPrice.getText().toString());
                ProductAddons checkAddons = productAddons.get(position);

                float addonPrice = Float.parseFloat(checkAddons.getAddonsPrice().toString());

                if (buttonView.isChecked()) {
                    selectedProductAddons.add(checkAddons);
                    GlobalClass.selectedProductAddons.add(checkAddons);
                    productPrice.setText(Float.toString(addonPrice + price));
                    checkAddons.setSelected(true);

                    Toast.makeText(mContext, "value add ", Toast.LENGTH_LONG).show();
                } else {
                    selectedProductAddons.remove(checkAddons);
                    GlobalClass.selectedProductAddons.remove(checkAddons);
                    productPrice.setText(Float.toString(price - addonPrice));
                    checkAddons.setSelected(false);
                    Toast.makeText(mContext, "value reversed", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return productAddons.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView addonPrices;
        public TextView addonName;
        public TextView mappingId;
        public TextView mappingAttribute;
        public CheckBox addonCheckbox;

        public ViewHolder(View v) {
            super(v);
            addonPrices = (TextView) v.findViewById(R.id.addons_price);
            mappingId = (TextView) v.findViewById(R.id.addons_mapping_id);
            mappingAttribute = (TextView) v.findViewById(R.id.addons_attribute_id);
            addonName = (TextView) v.findViewById(R.id.addons_text);
            addonCheckbox = (CheckBox) v.findViewById(R.id.addon_checkbox);
        }

        public void bindData(ProductAddons productAddons) {
            addonName.setText( productAddons.getAddonsName());
//        holder.mappingId.setText(p.getProductMappingAttributeId());
            // holder.mappingAttribute.setText(p.getProductMappingAttributeId());
           // addonPrices.setText("+" + productAddons.getAddonsPrice());
            //addonPrices.setText(Html.fromHtml("&ldquo[" + productAddons.getAddonsPrice() + "&rdquo]"));
            addonCheckbox.setChecked(productAddons.isSelected());
            addonCheckbox.setTag(productAddons);

            float adonPrice = Float.parseFloat(productAddons.getAddonsPrice());
            if(GlobalClass.currency != null){
                adonPrice = adonPrice * GlobalClass.currency.getRate();
            }

            addonPrices.setText("+" + String.format("%.0f",adonPrice));
        }
    }
}
