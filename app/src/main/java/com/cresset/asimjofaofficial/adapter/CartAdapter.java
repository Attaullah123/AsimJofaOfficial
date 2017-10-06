package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
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
import com.cresset.asimjofaofficial.GetCart;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.CartAttributes;
import com.cresset.asimjofaofficial.models.CartDetailModel;
import com.cresset.asimjofaofficial.models.CartModel;
import com.cresset.asimjofaofficial.models.CartModelItems;
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


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private GetCart getCart;
    public ArrayList<CartModelItems> cartModelItemses;
    private TextView totalPrice,subTotal;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView proName, proPrice, proSkuCode,proSize,proCurrencyName,proQuantity,proQuantityName;
        public ImageView thumbnailImage;
        //public CardView cardView;
        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            proName = (TextView) itemView.findViewById(R.id.product_name);
            proSkuCode = (TextView) itemView.findViewById(R.id.product_sku_code);
            proCurrencyName = (TextView) itemView.findViewById(R.id.product_cart_price_name);
            proPrice = (TextView) itemView.findViewById(R.id.product_cart_price);
            proSize = (TextView) itemView.findViewById(R.id.product_cart_size);
            proQuantity = (TextView) itemView.findViewById(R.id.cart_product_quantity);
            proQuantityName = (TextView) itemView.findViewById(R.id.cart_product_quantity_name);
            thumbnailImage = (ImageView) itemView.findViewById(R.id.cart_thumbnail);

            proCurrencyName.setText("USD");
            proQuantityName.setText("Qnty:");

        }
    }

    public CartAdapter(Context mContext, ArrayList<CartModelItems> cartList,TextView totalPrice,TextView subTotal) {
        this.mContext = mContext;
        this.cartModelItemses = cartList;
        this.getCart = new GetCart();
        this.totalPrice = totalPrice;
        this.subTotal = subTotal;
    }

    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_row, parent, false);

        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyViewHolder holder, int position) {

        CartModelItems cartListModel = cartModelItemses.get(position);

        Gson gson = new Gson();
        Log.d("CartModel List",gson.toJson(cartListModel));

        holder.proName.setText(cartListModel.getProductName());
        holder.proSkuCode.setText(cartListModel.getProductSku());
        holder.proQuantity.setText(String.valueOf(cartListModel.getQuantity()));

        String strAttributes = "";
        float productPrice = cartListModel.getProductPrice();

        if(!cartListModel.getAttributes().isEmpty())
        {
            for (CartAttributes attribute: cartListModel.getAttributes())
            {
                strAttributes += attribute.getAttributeValue() + " <br />";
                if(attribute.getAttributeprice() > 0){
                    productPrice += attribute.getAttributeprice();
                }
            }
        }
        holder.proSize.setText(Html.fromHtml(strAttributes));

        if(GlobalClass.currency != null){
            productPrice = productPrice * GlobalClass.currency.getRate();
            holder.proCurrencyName.setText(GlobalClass.currency.CurrencyCode);
        }
        else{
            holder.proCurrencyName.setText("USD");
        }

        //holder.proPrice.setText(String.format("%.0f",productPrice));
        holder.proPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(productPrice));
        Glide.with(mContext).load(cartListModel.getImageLink()).into(holder.thumbnailImage);
    }

    public void refreshItems(CartDetailModel cartDetailModel) {
        if (cartDetailModel != null) {
            cartModelItemses.clear();
            cartModelItemses.clear();
            //cartModelItemses.addAll(cartDetailModel.getCartItems());
            //cartModelItemses.addAll(cart.getDiscounts());
            notifyDataSetChanged();
        } else {
            Timber.e("Setting cart content with null cart");
        }
    }

    public void clearCart() {
        cartModelItemses.clear();
        cartModelItemses.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartModelItemses.size();
    }

    public void UpdateCart(final CartModelItems cartModel, final int position){
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());
        params.put("CartItemId", String.valueOf(cartModel.getCartItemId()));
        params.put("Quantity", String.valueOf(cartModel.getQuantity()));
        params.put("IsRemoveItem", "true");

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_UPDATE_CART, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        cartModelItemses.remove(cartModel);
                        getUpdatedCartDetail(totalPrice,subTotal);

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,cartModelItemses.size());

                        // Show the removed item label
                        Toast.makeText(mContext,"Item removed", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext.getApplicationContext(), "Couldn't delete, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        requestQueue.add(objectRequest);
    }

    public void getUpdatedCartDetail(TextView tPrice,TextView sTotal) {
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_GET_CART, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());

                        Gson gson = new Gson();
                        CartModel cartModel = gson.fromJson(response.toString(), new TypeToken<CartModel>(){}.getType());

                        String totalP = String.valueOf(cartModel.getTotalDetail().getSubTotalAmount());
                        String subTo = String.valueOf(cartModel.getTotalDetail().getSubTotalAmount());

                        getCart.UpdateTotal(totalP,subTo,subTotal);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext.getApplicationContext(), "Couldn't get cart, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
            }
        });
        CustomVolleyRequest.getInstance(mContext.getApplicationContext()).getRequestQueue().add(objectRequest);
    }


}
