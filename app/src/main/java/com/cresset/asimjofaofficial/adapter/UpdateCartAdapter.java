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

import com.bumptech.glide.Glide;
import com.cresset.asimjofaofficial.GetCart;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.CartAttributes;
import com.cresset.asimjofaofficial.models.CartDetailModel;
import com.cresset.asimjofaofficial.models.CartModelItems;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;

import java.util.ArrayList;

import timber.log.Timber;



public class UpdateCartAdapter extends RecyclerView.Adapter<UpdateCartAdapter.MyViewHolder> {

    private Context mContext;
    private GetCart getCart;
    public ArrayList<CartModelItems> cartModelItemses;
    private TextView totalPrice, subTotal;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView proName, proPrice, proSkuCode, proSize, proQuantity;
        public Button btnIncrement,btnDecrement;
        public ImageView thumbnailImage;
        public CheckBox listSelected;
        //public CardView cardView;
        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            proName = (TextView) itemView.findViewById(R.id.product_name);
            proSkuCode = (TextView) itemView.findViewById(R.id.product_sku_code);
            //deleteItem = (TextView) itemView.findViewById(R.id.cart_product_delete);
            proPrice = (TextView) itemView.findViewById(R.id.product_cart_price);
            proSize = (TextView) itemView.findViewById(R.id.product_cart_size);
            proQuantity = (TextView) itemView.findViewById(R.id.cart_product_quantity);
            thumbnailImage = (ImageView) itemView.findViewById(R.id.cart_thumbnail);
            btnDecrement = (Button) itemView.findViewById(R.id.quantity_decrement);
            btnIncrement= (Button) itemView.findViewById(R.id.quantity_increment);
            listSelected = (CheckBox) itemView.findViewById(R.id.select_cart_list);

//            deleteItem.setOnClickListener((View.OnClickListener) this);
            //cardView = (CardView) itemView.findViewById(R.id.card_view);

            listSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    if (buttonView.isChecked()){
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
        //delete item 
        GlobalClass.itemDelete = new ArrayList<Integer>();
        
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

                }
            }
        });
        //selected list initialization

//        holder.listSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (buttonView.isChecked()){
//                    cartModelItemses.get(position);
//                    //notifyItemRemoved(position);
//                    Toast.makeText(mContext, "position " + position, Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(mContext, "unchecked" + position, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

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
        holder.proPrice.setText(Float.toString(productPrice));
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

//    public void UpdateCart(final CartModelItems cartModel, final int position) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("ProjectId", "1");
//        params.put("CustomerId", "1");
//        params.put("CartItemId", String.valueOf(cartModel.getCartItemId()));
//        params.put("Quantity", String.valueOf(cartModel.getQuantity()));
//        params.put("IsRemoveItem", "true");
//
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_UPDATE_CART, new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        cartModelItemses.remove(cartModel);
//                        getUpdatedCartDetail(totalPrice, subTotal);
//
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, cartModelItemses.size());
//
//                        // Show the removed item label
//                        Toast.makeText(mContext, "Item removed", Toast.LENGTH_SHORT).show();
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(mContext.getApplicationContext(), "Couldn't delete, check connection", Toast.LENGTH_SHORT).show();
//                Log.d("Error", error.toString());
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
//        requestQueue.add(objectRequest);
//    }
//
//    public void getUpdatedCartDetail(TextView tPrice, TextView sTotal) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("ProjectId", "1");
//        params.put("CustomerId", "1");
//
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_GET_CART, new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        //CategoryModel categoryModel = new CategoryModel();
//
//                        Log.d("Response", response.toString());
//
//                        Gson gson = new Gson();
//                        CartModel cartModel = gson.fromJson(response.toString(), new TypeToken<CartModel>() {
//                        }.getType());
//
//                        String totalP = String.valueOf(cartModel.getTotalDetail().getSubTotalAmount());
//                        String subTo = String.valueOf(cartModel.getTotalDetail().getSubTotalAmount());
//
//                        getCart.UpdateTotal(totalP, subTo, subTotal);
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(mContext.getApplicationContext(), "Couldn't get cart, check connection", Toast.LENGTH_SHORT).show();
//                Log.d("Error", error.toString());
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
//        requestQueue.add(objectRequest);
//    }

}
