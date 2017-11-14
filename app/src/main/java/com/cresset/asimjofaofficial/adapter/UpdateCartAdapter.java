package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.cresset.asimjofaofficial.models.CartModelItems;
import com.cresset.asimjofaofficial.models.UpdateProductQuantity;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.volley.AppController;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import timber.log.Timber;



public class UpdateCartAdapter extends RecyclerView.Adapter<UpdateCartAdapter.MyViewHolder> {

    private Context mContext;
    private GetCart getCart;
    public ArrayList<CartModelItems> cartModelItemses;
    private TextView totalPrice, subTotal;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView proName, proPrice, proSkuCode,productSkuName, proSize, proQuantity,proCurrencyName,proSizeName;
        public Button btnIncrement, btnDecrement;
        public ImageView thumbnailImage;
        public CheckBox listSelected;
        //public CardView cardView;
        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            proName = (TextView) itemView.findViewById(R.id.product_name);
            proSkuCode = (TextView) itemView.findViewById(R.id.product_sku_code);
            productSkuName = (TextView) itemView.findViewById(R.id.product_sku_name);
            proCurrencyName = (TextView) itemView.findViewById(R.id.product_cart_price_name);
            proPrice = (TextView) itemView.findViewById(R.id.product_cart_price);
            proSize = (TextView) itemView.findViewById(R.id.product_cart_size);
            //proSizeName = (TextView) itemView.findViewById(R.id.cart_product_size_name);
            proQuantity = (TextView) itemView.findViewById(R.id.cart_product_quantity);
            thumbnailImage = (ImageView) itemView.findViewById(R.id.cart_thumbnail);
            btnDecrement = (Button) itemView.findViewById(R.id.quantity_decrement);
            btnIncrement = (Button) itemView.findViewById(R.id.quantity_increment);
            listSelected = (CheckBox) itemView.findViewById(R.id.select_cart_list);

            proCurrencyName.setText("USD");
            productSkuName.setText("SKU:");
           // proSizeName.setText("Size:");
//            deleteItem.setOnClickListener((View.OnClickListener) this);
            //cardView = (CardView) itemView.findViewById(R.id.card_view);

            listSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    if (buttonView.isChecked()) {
                        CartModelItems cartModelItems = cartModelItemses.get(position);
                    }
                }
            });

//            deleteItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    //Context context = v.getContext();
//                    CartModelItems cartModel = cartModelItemses.get(position);
//                    UpdateCart(cartModel, position);
//                }
//            });

        }
    }

    public UpdateCartAdapter(Context mContext, ArrayList<CartModelItems> cartList) {
        this.mContext = mContext;
        this.cartModelItemses = cartList;
        this.getCart = new GetCart();
//        this.totalPrice = totalPrice;
//        this.subTotal = subTotal;
    }

    @Override
    public UpdateCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.update_cart_list, parent, false);

        return new UpdateCartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UpdateCartAdapter.MyViewHolder holder, final int position) {

        final CartModelItems cartListModel = cartModelItemses.get(position);

        Gson gson = new Gson();
        Log.d("CartModel List", gson.toJson(cartListModel));

        holder.proName.setText(cartListModel.getProductName());
        holder.proSkuCode.setText(cartListModel.getProductSku());
        holder.proQuantity.setText(String.valueOf(cartListModel.getQuantity()));
        holder.listSelected.setChecked(cartModelItemses.get(position).isSelected);

        //quantity button
        holder.btnIncrement.setOnClickListener(new View.OnClickListener() {
            //int count;
            @Override
            public void onClick(View v) {
                //count = 0;
                if (cartListModel.getQuantity() <= 4) {
                    cartModelItemses.get(position).setQuantity(cartModelItemses.get(position).getQuantity() + 1);
                    holder.proQuantity.setText(String.valueOf(cartModelItemses.get(position).getQuantity()));

                    UpdateCart(cartModelItemses.get(position), position);
//                    UpdateProductQuantity updatemode = new UpdateProductQuantity();
//                    updatemode.setQuantity(Integer.toString(cartModelItemses.get(position).getQuantity()));
//                    updatemode.setCartItemId(Integer.toString(cartModelItemses.get(position).getCartItemId()));
//                    UpdateCartData(updatemode);
                }
            }
        });

        holder.btnDecrement.setOnClickListener(new View.OnClickListener() {
            //int count;
            @Override
            public void onClick(View v) {
                //count = 0;

                if (cartListModel.getQuantity() > 1) {
                    cartModelItemses.get(position).setQuantity(cartModelItemses.get(position).getQuantity() - 1);
                    holder.proQuantity.setText(String.valueOf(cartModelItemses.get(position).getQuantity()));

                    UpdateCart(cartModelItemses.get(position), position);
//                    UpdateProductQuantity updatemode = new UpdateProductQuantity();
//                    updatemode.setQuantity(Integer.toString(cartModelItemses.get(position).getQuantity()));
//                    updatemode.setCartItemId(Integer.toString(cartModelItemses.get(position).getCartItemId()));
//                    UpdateCartData(updatemode);
                }
            }
        });
        //selected list initialization

        holder.listSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    GlobalClass.deleteSelectedCartItems.add(cartModelItemses.get(position).CartItemId);
                    //notifyItemRemoved(position);
                    //Toast.makeText(mContext, "position", Toast.LENGTH_SHORT).show();
                } else {
                    if(GlobalClass.deleteSelectedCartItems.size() > 0){
                        int index = 0;

                        Iterator<Integer> val = GlobalClass.deleteSelectedCartItems.iterator();
                       // for (int val = GlobalClass.deleteSelectedCartItems.size() - 1; val >= 0; val--) {
                            while (val.hasNext()){
                                int i = val.next();
                            if(i  == cartModelItemses.get(position).CartItemId){
                                val.remove();
                            }

                        }
                    }
                }
            }
        });

        String strAttributes = "";
        float productPrice = cartListModel.getProductPrice();

        if (!cartListModel.getAttributes().isEmpty()) {
            for (CartAttributes attribute : cartListModel.getAttributes()) {
                strAttributes += attribute.getAttributeValue() + " <br />";
                if (attribute.getAttributeprice() > 0) {
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
        //Glide.with(mContext).load(cartListModel.getImageLink()).into(holder.thumbnailImage);
        if (cartListModel.getImageLink().isEmpty()){
            holder.thumbnailImage.setImageResource(R.drawable.placeholder_loading);
        }
        Picasso.with(mContext).load(cartListModel.getImageLink())
                .placeholder(R.drawable.placeholder_loading)
                .fit().centerInside()
                .into(holder.thumbnailImage);
    }

    /*public void UpdateCartData(UpdateProductQuantity updateModel){
        boolean cartItemAdded = false;
        int loopIndex = 0;
        if(GlobalClass.updateProductQuantity.size() > 0){
            for (UpdateProductQuantity model: GlobalClass.updateProductQuantity
                 ) {
                if(model.getCartItemId() == updateModel.getCartItemId()){
                    GlobalClass.updateProductQuantity.get(loopIndex).setQuantity(updateModel.getQuantity());
                    cartItemAdded = true;
                }
                loopIndex++;
            }

            if(!cartItemAdded){
                GlobalClass.updateProductQuantity.add(updateModel);
            }
        }
        else{
            GlobalClass.updateProductQuantity.add(updateModel);
        }
    }*/

    public void UpdateCart(final CartModelItems cartModel, final int position) {
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());
        params.put("CartItemId", String.valueOf(cartModel.getCartItemId()));
        params.put("Quantity", String.valueOf(cartModel.getQuantity()));
        params.put("IsRemoveItem", "false");

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_UPDATE_CART, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cartModelItemses.size());

                        // Show the removed item label
                        //Toast.makeText(mContext,"Quantity update", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext.getApplicationContext(), "Couldn't delete, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
            }
        });
        objectRequest.setRetryPolicy(AppController.getDefaultRetryPolice());
        objectRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(objectRequest, Config.tag_json_obj);
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

}