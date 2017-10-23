package com.cresset.asimjofaofficial;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cresset.asimjofaofficial.adapter.InvoiceAdapter;

import com.cresset.asimjofaofficial.models.InvoiceBillingAddress;
import com.cresset.asimjofaofficial.models.InvoiceCartItem;
import com.cresset.asimjofaofficial.models.InvoiceModel;
import com.cresset.asimjofaofficial.models.InvoiceOrderDetailModel;
import com.cresset.asimjofaofficial.models.InvoiceShippingAddress;
import com.cresset.asimjofaofficial.recylerview.RecyclerDivider;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.volley.AppController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class InvoiceActivity extends AppCompatActivity {
    private ImageView back;
    private TextView orderNo, orderStatus, orderTotal,orderDate,currencyName;
    private TextView billingName,billingEmail,billingAdress,billingPhone,billingCity, billingCountry,billingState;
    private TextView shippingName,shippingEmail,shippingAdress,shippingPhone,shippingCity,shippingCountry,shippingState;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private InvoiceAdapter invoiceAdapter;
    private ProgressDialog progressDialog;
    private String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_activity_new);
        back = (ImageView) findViewById(R.id.img_cross);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        orderId = getIntent().getStringExtra("orderId");

        currencyName = (TextView) findViewById(R.id.in_order_total_name);

        orderNo = (TextView) findViewById(R.id.in_order_id);
        orderStatus = (TextView) findViewById(R.id.in_order_status);
        orderTotal = (TextView) findViewById(R.id.in_order_total);
        orderDate = (TextView) findViewById(R.id.in_order_date);

        billingName = (TextView) findViewById(R.id.billing_name);
        billingEmail = (TextView) findViewById(R.id.billing_email);
        billingAdress = (TextView) findViewById(R.id.billing_address);
        billingPhone = (TextView) findViewById(R.id.billing_phone);
        billingCountry = (TextView) findViewById(R.id.billing_country);
        billingState = (TextView) findViewById(R.id.billing_state);
        billingCity = (TextView) findViewById(R.id.billing_city);

        shippingName = (TextView) findViewById(R.id.shipping_name);
        shippingEmail = (TextView) findViewById(R.id.shipping_email);
        shippingAdress = (TextView) findViewById(R.id.shipping_address);
        shippingPhone = (TextView) findViewById(R.id.shipping_phone);
        shippingCountry = (TextView) findViewById(R.id.shipping_country);
        shippingState = (TextView) findViewById(R.id.shipping_state);
        shippingCity = (TextView) findViewById(R.id.shipping_city);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new RecyclerDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(invoiceAdapter);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("please wait...");

        getInvoiceDetail();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getInvoiceDetail(){
        //progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("OrderId",orderId);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_GETORDER_BYID, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        Gson gson = new Gson();
                        //get billing address
                        InvoiceModel invoiceModel = gson.fromJson(response.toString(), new TypeToken<InvoiceModel>(){}.getType());


                        final InvoiceOrderDetailModel invoiceOrderDetailModel = invoiceModel.getOrderDetail();

                        String orderId=invoiceOrderDetailModel.getId();
                        String orderStatus1 =invoiceOrderDetailModel.getOrderStatus();
                        String orderDate1 =invoiceOrderDetailModel.getOrderDate();
                        //String orderTotal1 =invoiceOrderDetailModel.getOrderTotal();
                        float totalPrice = Float.parseFloat(invoiceOrderDetailModel.getOrderTotal());

                        orderNo.setText(orderId);
                        orderStatus.setText(orderStatus1);
                        orderDate.setText(orderDate1);
                        //orderTotal.setText(orderTotal1);

                        if(GlobalClass.currency != null){
                            totalPrice = totalPrice * GlobalClass.currency.getRate();
                            currencyName.setText(GlobalClass.currency.CurrencyCode);
                        }else {
                            currencyName.setText("USD");
                        }

                        orderTotal.setText(NumberFormat.getNumberInstance(Locale.US).format(totalPrice));

                        final InvoiceBillingAddress invoiceBillingAddress = invoiceOrderDetailModel.getBillingAddress();
                        final InvoiceShippingAddress invoiceShippingAddress = invoiceOrderDetailModel.getShippingAddress();

                        ArrayList<InvoiceCartItem> invoiceCartItems = new ArrayList<InvoiceCartItem>(invoiceOrderDetailModel.getOrderItems());

                        //invoice billing address
                        String userAddress =invoiceBillingAddress.getAddress1();
                        String userCity =invoiceBillingAddress.getCity();
                        String userName =invoiceBillingAddress.getFirstName();
                        String userEmail =invoiceBillingAddress.getEmail();
                        String userPhone=invoiceBillingAddress.getPhoneNumber();
                        String userCountry=invoiceBillingAddress.getCountry();
                        String userState=invoiceBillingAddress.getStateProvince();


                        billingName.setText(userName);
                        billingEmail.setText(userEmail);
                        billingAdress.setText(userAddress);
                        billingPhone.setText(userPhone);
                        billingCity.setText(userCity);
                        billingCountry.setText(userCountry);
                        billingState.setText(userState);

                        //invoice shipping address

                        String userName1 =invoiceShippingAddress.getFirstName();
                        String userEmail1 =invoiceShippingAddress.getEmail();
                        String userAddress1 =invoiceShippingAddress.getAddress1();
                        String userCity1 =invoiceShippingAddress.getCity();
                        String userPhone1 =invoiceShippingAddress.getPhoneNumber();
                        String userCountry1 =invoiceShippingAddress.getCountry();
                        String userState1 =invoiceShippingAddress.getStateProvince();


                        shippingName.setText(userName1);
                        shippingEmail.setText(userEmail1);
                        shippingAdress.setText(userAddress1);
                        shippingPhone.setText(userPhone1);
                        shippingCity.setText(userCity1);
                        shippingCountry.setText(userCountry1);
                        shippingState.setText(userState1);

                        invoiceAdapter = new InvoiceAdapter(getApplicationContext(), invoiceCartItems);
                        recyclerView.setAdapter(invoiceAdapter);
                        //progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, internet connection slow", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                //progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(objectRequest);
        objectRequest.setRetryPolicy(AppController.getDefaultRetryPolice());
        objectRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(objectRequest, Config.tag_json_obj);
    }

}
