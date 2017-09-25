package com.cresset.asimjofaofficial;

import android.app.ProgressDialog;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.adapter.PaymentAdapter;
import com.cresset.asimjofaofficial.adapter.ShippingMethodAdapter;
import com.cresset.asimjofaofficial.models.PaymentMethodModel;
import com.cresset.asimjofaofficial.models.PaymentModel;
import com.cresset.asimjofaofficial.models.ShippingMethodModel;
import com.cresset.asimjofaofficial.models.ShippingmethodList;
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


public class PaymentMethod extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PaymentAdapter paymentAdapter;
    private ImageView back;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_method);
        toolbar = (Toolbar) findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new RecyclerDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(paymentAdapter);

        getpaymentMethod();

        back = (ImageView) findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getpaymentMethod(){
            progressBar.setVisibility(View.VISIBLE);
            //creating json array list
            Map<String, String> params = new HashMap<String, String>();
            params.put("ProjectId", Config.PROJECTID);
            params.put("CountryId", GlobalClass.billingModel.getCountryId());

            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_PAYMENT_METHOD, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //CategoryModel categoryModel = new CategoryModel();

                            Log.d("Response", response.toString());
                            //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                            Gson gson = new Gson();
                            PaymentModel paymentModel= gson.fromJson(response.toString(), new TypeToken<PaymentModel>(){}.getType());

                            ArrayList<PaymentMethodModel> paymentLists = new ArrayList<PaymentMethodModel>(paymentModel.getPaymentMethod());

                            //shippingLists.add(paymentLists.getShippingmethod());
                            Log.d("Response12", gson.toJson(paymentLists));

                            paymentAdapter = new PaymentAdapter(getApplicationContext(), paymentLists);
                            recyclerView.setAdapter(paymentAdapter);
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
        CustomVolleyRequest.getInstance(getApplicationContext()).getRequestQueue().add(objectRequest);
        }
}
