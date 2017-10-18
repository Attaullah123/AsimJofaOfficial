package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cresset.asimjofaofficial.BillingShippingById;
import com.cresset.asimjofaofficial.GetCart;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.CartAttributes;
import com.cresset.asimjofaofficial.models.CartDetailModel;
import com.cresset.asimjofaofficial.models.CartModel;
import com.cresset.asimjofaofficial.models.CartModelItems;
import com.cresset.asimjofaofficial.models.InvoiceCartItem;
import com.cresset.asimjofaofficial.models.MultpleAddressList;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import timber.log.Timber;


public class InvoiceAdapter  extends RecyclerView.Adapter<InvoiceAdapter.MyViewHolder>  {

    private Context mContext;
    private ArrayList<InvoiceCartItem>  invoiceCartItems;


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView proName,proPrice,proQty,proSize,proTotal;
        //public ImageView thumbnail;
        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);

            proName = (TextView) itemView.findViewById(R.id.product_name);

            //proEmail = (TextView) itemView.findViewById(R.id.adress_customer_name);
            proPrice = (TextView) itemView.findViewById(R.id.product_price);
            proQty = (TextView) itemView.findViewById(R.id.product_qnty);
            proSize = (TextView) itemView.findViewById(R.id.product_size);
            //proTotal = (TextView) itemView.findViewById(R.id.product_total);



        }

    }
    public InvoiceAdapter(Context mContext, ArrayList<InvoiceCartItem> invoiceCartItemss) {
        this.mContext = mContext;
        this.invoiceCartItems = invoiceCartItemss;
    }
    @Override
    public InvoiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_cart_item_list, parent, false);

        return new InvoiceAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final InvoiceAdapter.MyViewHolder holder, int position) {
        InvoiceCartItem  cartItems = invoiceCartItems.get(position);

        //holder.userId.setText(multipleAddress.getId());
        holder.proName.setText(cartItems.getProductName());
        //holder.proEmail.setText(cartItems.getEmail());
        //holder.proPrice.setText(cartItems.getProductPrice());
        holder.proQty.setText(cartItems.getQuantity());
        holder.proSize.setText(Html.fromHtml(cartItems.getAttributeDescription()));
        //holder.proTotal.setText(cartItems.get());

        float totalPrice = Float.parseFloat(cartItems.getProductPrice());
        if(GlobalClass.currency != null){
            totalPrice = totalPrice * GlobalClass.currency.getRate();
            holder.proPrice.setText(GlobalClass.currency.CurrencyCode);
        }

        holder.proPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(totalPrice));
    }

    @Override
    public int getItemCount() {
        return invoiceCartItems.size();
    }



}
