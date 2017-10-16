package com.cresset.asimjofaofficial;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cresset.asimjofaofficial.adapter.InvoiceAdapter;
import com.cresset.asimjofaofficial.adapter.OrderInvoiceAdapter;
import com.cresset.asimjofaofficial.adapter.PolicyAdapter;
import com.cresset.asimjofaofficial.adapter.ProductListAdapter;
import com.cresset.asimjofaofficial.fragments.TermsAndConditions;
import com.cresset.asimjofaofficial.models.InvoiceBillingAddress;
import com.cresset.asimjofaofficial.models.InvoiceCartItem;
import com.cresset.asimjofaofficial.models.InvoiceModel;
import com.cresset.asimjofaofficial.models.InvoiceOrderDetailModel;
import com.cresset.asimjofaofficial.models.OrdersListModel;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.ProductModel;
import com.cresset.asimjofaofficial.recylerview.RecyclerDivider;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InvoiceActivity extends AppCompatActivity {
    private ImageView back;
    private TextView billingName,billingEmail,billingAdress,billingPhone,billingCity, billingCountry,billingState;
    private TextView shippingName,shippingEmail,shippingAdress,shippingPhone,shippingCity,shippingCountry,shippingState;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private InvoiceAdapter invoiceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_activity_new);
        back = (ImageView) findViewById(R.id.img_cross);

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

        //creating json array list
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("OrderId","27356");

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

                        //InvoiceOrderDetailModel invoiceModel = gson.fromJson(response.toString(), new TypeToken<InvoiceModel>(){}.getType());

                        final InvoiceOrderDetailModel invoiceOrderDetailModel = invoiceModel.getOrderDetail();

                        final InvoiceBillingAddress invoiceBillingAddress = invoiceOrderDetailModel.getBillingAddress();

                        ArrayList<InvoiceCartItem> invoiceCartItems = new ArrayList<InvoiceCartItem>(invoiceOrderDetailModel.getOrderItems());

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


//                        String userAddress =invoiceBillingAddress.getAddress1();
//                        String userCity =invoiceBillingAddress.getCity();
//                        String userName =invoiceBillingAddress.getFirstName();
//                        String userEmail =invoiceBillingAddress.getEmail();
//                        String userPhone=invoiceBillingAddress.getPhoneNumber();
//                        String userCountry=invoiceBillingAddress.getCountry();
//                        String userState=invoiceBillingAddress.getStateProvince();
//
//
//                        billingName.setText(userName);
//                        billingEmail.setText(userEmail);
//                        billingAdress.setText(userAddress);
//                        billingPhone.setText(userPhone);
//                        billingCity.setText(userCity);
//                        billingCountry.setText(userCountry);
//                        billingState.setText(userState);

                        invoiceAdapter = new InvoiceAdapter(getApplicationContext(), invoiceCartItems);
                        recyclerView.setAdapter(invoiceAdapter);
                        //progressBar.setVisibility(View.GONE);
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

}
