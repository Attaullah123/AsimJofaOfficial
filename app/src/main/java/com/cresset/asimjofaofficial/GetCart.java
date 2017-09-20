package com.cresset.asimjofaofficial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.adapter.CartAdapter;
import com.cresset.asimjofaofficial.adapter.ProductListAdapter;
import com.cresset.asimjofaofficial.models.CartDetailModel;
import com.cresset.asimjofaofficial.models.CartModel;
import com.cresset.asimjofaofficial.models.CartModelItems;
import com.cresset.asimjofaofficial.models.CategoryList;
import com.cresset.asimjofaofficial.models.CategoryModel;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.ProductModel;
import com.cresset.asimjofaofficial.recylerview.RecyclerDivider;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.volley.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class GetCart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView totalPrice,subTotal,cartEdit,itemCount;
    Toolbar toolbar;
    private CartAdapter cartAdapter;
    //private ProgressDialog progressDialog;
    private String prodId;
    private View emptyCart;
    private CartModel cartModelData;
    private TextView currencyName;
    private Button checkOut;
    private ProgressBar progressBar;
    private float totalP,subTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        emptyCart = findViewById(R.id.cart_empty);
        checkOut = (Button) findViewById(R.id.pay);
        itemCount = (TextView) findViewById(R.id.cart_total_item);
        currencyName = (TextView) findViewById(R.id.detail_currency);
        subTotal = (TextView) findViewById(R.id.sub_total);
        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
        ImageView cross = (ImageView) findViewById(R.id.img_back);
        cartEdit = (TextView) findViewById(R.id.edit_cart);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        currencyName.setText("USD");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new RecyclerDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartAdapter);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading.....");
//        progressDialog.setCancelable(false);

        getCartDetail();
        //initialize cart count
        GetCartItemsCount();

        cartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UpdateCart.class);
                startActivity(intent);
            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cartAdapter.cartModelItemses.size()>0){
                    Intent intent = new Intent(getApplicationContext(),CheckOutActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public CartModel getCartDetail() {
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String,String> params = new HashMap<>();
        if (GlobalClass.userData != null) {
            params.put("ProjectId", Config.PROJECTID);
            params.put("CustomerId", GlobalClass.userData.getUserID());

            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_GET_CART, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //CategoryModel categoryModel = new CategoryModel();

                            Log.d("Response", response.toString());
                            //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                            Gson gson = new Gson();
                            cartModelData = gson.fromJson(response.toString(), new TypeToken<CartModel>() {}.getType());


                            Log.d("CartModel ", gson.toJson(cartModelData));

                            //totalP = (cartModelData.getTotalDetail().getSubTotalAmount());
                            subTo = (cartModelData.getTotalDetail().getSubTotalAmount());


                            //set currency
                            if(GlobalClass.currency != null){
                                subTo = subTo * GlobalClass.currency.getRate();
                                currencyName.setText(GlobalClass.currency.CurrencyCode);
                            }
                            else{
                                currencyName.setText("USD");
                            }

                            subTotal.setText(Float.toString(subTo));

                            ArrayList<CartModelItems> cartItems = new ArrayList<>(cartModelData.getCartItems());

                            if (cartModelData.getCartItems() == null || cartModelData.getCartItems().size() == 0) {
                                setCartVisibility(false);
                            } else {
                                setCartVisibility(true);
                                //cartAdapter.refreshItems(cartDetailModel);
                            }

                            cartAdapter = new CartAdapter(getApplicationContext(), cartItems, totalPrice, subTotal);
                            recyclerView.setAdapter(cartAdapter);
                            progressBar.setVisibility(View.GONE);


                        }


                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                    Log.d("Error", error.toString());
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

            AppController.getInstance().addToRequestQueue(objectRequest);
        }else {
            Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
        }

        return cartModelData;
    }

    //call cart count item
    public void GetCartItemsCount() {
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_Cart_Count, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());
                            GlobalClass.CartCount = jsonObject.getInt("CartCount");
                            //UpdateCartCount();
                            itemCount.setText(Integer.toString(GlobalClass.CartCount)+ " " + "items");
                            //itemCount.setLetterSpacing(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
            }
        });
        CustomVolleyRequest.getInstance(getApplicationContext()).getRequestQueue().add(objectRequest);
    }

    private void setCartVisibility(boolean visible) {
        if (visible) {
            if (emptyCart != null) emptyCart.setVisibility(View.GONE);
            if (recyclerView != null) recyclerView.setVisibility(View.VISIBLE);
            //if (cartFooter != null) cartFooter.setVisibility(View.VISIBLE);
        } else {
            if (cartAdapter != null) cartAdapter.clearCart();
            if (emptyCart != null) emptyCart.setVisibility(View.VISIBLE);
            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getCartDetail();
    }

    public void UpdateTotal(String totalP,String subT,TextView sTotal){
        if(totalP != null){
            //tPrice.setText(totalP);
            sTotal.setText(subT);
        }
    }

    public void Show(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}