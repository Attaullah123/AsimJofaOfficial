package com.cresset.asimjofaofficial.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.ShippingmethodList;
import com.cresset.asimjofaofficial.productdetail.ProductDetail;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ShippingMethodAdapter  extends RecyclerView.Adapter<ShippingMethodAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ShippingmethodList> shippingmethodList;
//    public String productName;
//    private ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView shippingName, shippingPrice,productId,productCurrencyName;
        //private TextView freeShipping;
        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            shippingName = (TextView) itemView.findViewById(R.id.shipping_method_name);
            shippingPrice = (TextView) itemView.findViewById(R.id.shipping_price);
            productCurrencyName = (TextView) itemView.findViewById(R.id.shipping_currency_name);
            //freeShipping = (TextView) itemView.findViewById(R.id.shipping_charge_within_country);
            //proId = (TextView) itemView.findViewById(R.id.product_list_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Context context = v.getContext();
                   // Gson gson = new Gson();
                    GlobalClass.shippingMethod = shippingmethodList.get(position);
                    Toast.makeText(context.getApplicationContext(),shippingmethodList.get(position).getName(), Toast.LENGTH_LONG).show();

//                    Intent intent = new Intent();
//                    intent.putExtra("editTextValue", "value_here");
//                    context.setResult(RESULT_OK, intent);
                    ((Activity)context).finish();
                    //GlobalClass
                }
            });
        }

    }
    public ShippingMethodAdapter(Context mContext, ArrayList<ShippingmethodList> shippingList) {
        this.mContext = mContext;
        this.shippingmethodList = shippingList;
    }
    @Override
    public ShippingMethodAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipping_method_list, parent, false);

        return new ShippingMethodAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ShippingMethodAdapter.MyViewHolder holder, int position) {
        ShippingmethodList list = shippingmethodList.get(position);

        holder.shippingName.setText(list.getName());

//        if (list.getShippingRateComputationMethodSystemName().equals("0")){
//            holder.freeShipping.setText("Free Shipping Within Pakistan");
//        }

        float productPrice = list.getPrice();
        //holder.price.setText(productListModel.getPrice());
        if(GlobalClass.currency != null){
            productPrice = productPrice * GlobalClass.currency.getRate();
            holder.productCurrencyName.setText(GlobalClass.currency.CurrencyCode);
        }
        else{
            holder.productCurrencyName.setText("USD");
        }
        //holder.shippingPrice.setText(String.format("%.0f",productPrice));
        holder.shippingPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(productPrice));
    }



    @Override
    public int getItemCount() {
        return shippingmethodList.size();
    }

}
