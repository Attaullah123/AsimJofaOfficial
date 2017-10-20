package com.cresset.asimjofaofficial.userinfo.activity;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.adapter.MultipleAdressAdapter;
import com.cresset.asimjofaofficial.adapter.ProductListAdapter;
import com.cresset.asimjofaofficial.models.MultpleAddressList;
import com.cresset.asimjofaofficial.models.MultpleAddressModel;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.ProductModel;
import com.cresset.asimjofaofficial.recylerview.RecyclerDivider;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MultipleAdressBook extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MultipleAdressAdapter multipleAdressAdapter;
    private ProgressBar progressBar;
    private ImageView back;
    private View emptyCart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_address_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        emptyCart = findViewById(R.id.cart_empty);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new RecyclerDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(multipleAdressAdapter);

        back = (ImageView) findViewById(R.id.img_cross);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getUserAddress();


    }

    public void getUserAddress() {
        progressBar.setVisibility(View.VISIBLE);

        //creating json array list
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_MULTI_ADDRESS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        Gson gson = new Gson();
                        MultpleAddressModel addressModel = gson.fromJson(response.toString(), new TypeToken<MultpleAddressModel>(){}.getType());

                        ArrayList<MultpleAddressList> detailLists = new ArrayList<MultpleAddressList>(addressModel.getCustomerAddress());

                        if (addressModel.getCustomerAddress() == null || addressModel.getCustomerAddress().size() == 0) {
                            setCartVisibility(false);
                        } else {
                            setCartVisibility(true);
                            //cartAdapter.refreshItems(cartDetailModel);
                        }

                        multipleAdressAdapter = new MultipleAdressAdapter(getApplicationContext(), detailLists);
                        recyclerView.setAdapter(multipleAdressAdapter);
                        progressBar.setVisibility(View.GONE);
                        //progressDialog.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, internet connection slow", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                progressBar.setVisibility(View.GONE);
                //progressDialog.dismiss();
            }
        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(objectRequest);
        CustomVolleyRequest.getInstance(getApplicationContext()).getRequestQueue().add(objectRequest);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getUserAddress();
    }

    private void setCartVisibility(boolean visible) {
        if (visible) {
            if (emptyCart != null) emptyCart.setVisibility(View.GONE);
            if (recyclerView != null) recyclerView.setVisibility(View.VISIBLE);
            //if (cartFooter != null) cartFooter.setVisibility(View.VISIBLE);
        } else {
            if (multipleAdressAdapter != null) multipleAdressAdapter.clearCart();
            if (emptyCart != null) emptyCart.setVisibility(View.VISIBLE);
            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
            if (recyclerView != null) recyclerView.setVisibility(View.GONE);
        }
    }

}
