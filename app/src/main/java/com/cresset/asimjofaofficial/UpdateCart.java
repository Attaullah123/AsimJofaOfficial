package com.cresset.asimjofaofficial;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.adapter.CartAdapter;
import com.cresset.asimjofaofficial.adapter.UpdateCartAdapter;
import com.cresset.asimjofaofficial.models.CartModel;
import com.cresset.asimjofaofficial.models.CartModelItems;
import com.cresset.asimjofaofficial.models.UpdateProductQuantity;
import com.cresset.asimjofaofficial.recylerview.RecyclerDivider;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.volley.AppController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class UpdateCart extends AppCompatActivity{

    private RecyclerView recyclerView;
    //private TextView totalPrice,subTotal;
    Toolbar toolbar;
    private TextView cartDone;
    private UpdateCartAdapter cartAdapter;
    //private ProgressDialog progressDialog;
    private String prodId;
    private View emptyCart;
    private CartModel cartModelData;
    private TextView deleteList;
    private ProgressBar progressBar;
    private float totalP,subTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_cart);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //delete item
        GlobalClass.deleteSelectedCartItems = new ArrayList<Integer>();
        GlobalClass.updateProductQuantity = new ArrayList<UpdateProductQuantity>();

        cartDone = (TextView) findViewById(R.id.cart_done);
        emptyCart = findViewById(R.id.cart_empty);
        deleteList = (TextView) findViewById(R.id.btn_delete);
        //totalPrice = (TextView) findViewById(R.id.total_amount);
        //subTotal = (TextView) findViewById(R.id.sub_total);
        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
        //ImageView cross = (ImageView) findViewById(R.id.img_back);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(cartAdapter);

        //delete item
        //GlobalClass.itemDelete = new ArrayList<Integer>();

        getCartDetail();
        cartDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                    }
        });


        deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalClass.deleteSelectedCartItems != null){
                    if(GlobalClass.deleteSelectedCartItems.size() > 0){
                        DeleteSelectedCartItems();
                    }
                    else{

                    }
                }
                else{

                }
            }
        });

    }

    public CartModel getCartDetail() {

        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_GET_CART, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        CartDetailList(response.toString());

                        progressBar.setVisibility(View.GONE);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest);

        return cartModelData;
    }

    public void DeleteSelectedCartItems() {

        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());

        String deletedIds = android.text.TextUtils.join(",", GlobalClass.deleteSelectedCartItems);
        params.put("CartItemIds", deletedIds );

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_Delete_Cart_Items, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        CartDetailList(response.toString());
                        progressBar.setVisibility(View.GONE);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
        AppController.getInstance().addToRequestQueue(objectRequest);
    }

    public void CartDetailList(String response){
        Gson gson = new Gson();
        cartModelData = gson.fromJson(response, new TypeToken<CartModel>(){}.getType());

        Log.d("CartModel ",gson.toJson(cartModelData));

        subTo = (cartModelData.getTotalDetail().getSubTotalAmount());

        //totalPrice.setText(Float.toString(totalP));
        //subTotal.setText( Float.toString(subTo));

        ArrayList<CartModelItems> cartItems = new ArrayList<>(cartModelData.getCartItems());

        if (cartModelData.getCartItems() == null || cartModelData.getCartItems().size() == 0){
            setCartVisibility(false);
        } else {
            setCartVisibility(true);
            //cartAdapter.refreshItems(cartDetailModel);
        }

        cartAdapter = new UpdateCartAdapter(getApplicationContext(),cartItems);
        recyclerView.setAdapter(cartAdapter);
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
