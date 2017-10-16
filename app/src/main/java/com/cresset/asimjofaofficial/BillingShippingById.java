package com.cresset.asimjofaofficial;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.fragments.BillingAddress;
import com.cresset.asimjofaofficial.models.BillingShippingDetailModel;
import com.cresset.asimjofaofficial.models.BillingShippingModel;
import com.cresset.asimjofaofficial.models.CountryList;
import com.cresset.asimjofaofficial.models.CustomerAddressModel;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.ShippingModel;
import com.cresset.asimjofaofficial.models.StateList;
import com.cresset.asimjofaofficial.models.UpdateBillingAndShippingModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BillingShippingById extends AppCompatActivity {

    private EditText sfullname,sEmail,sAdress,sPhoneNo,sCity,sPostalCode,sDay,sMonth,sYear;
    private Spinner sCountry,sProvince;
    private Button btnShippingAddress;
    private ShippingModel shippingModel;
    private CountryList countryListItem;
    private StateList stateListItem;
    private TextView shippingId;
    private ProgressDialog progressDialog;
    private BillingShippingModel userDetailModel;
    private String addressId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_shipping_byid);

        sfullname = (EditText) findViewById(R.id.shipping_input_full_name);
        shippingId = (TextView) findViewById(R.id.shipping_input_id);
        sEmail = (EditText) findViewById(R.id.shipping_input_email);
        sAdress = (EditText) findViewById(R.id.shipping_input_Address);
        sPhoneNo = (EditText) findViewById(R.id.shipping_input_phone);
        sCity = (EditText) findViewById(R.id.shipping_input_city);
        sPostalCode = (EditText) findViewById(R.id.shipping_input_zipcode);
        //spinner view
        sCountry = (Spinner) findViewById(R.id.shipping_size_country_select);
        sProvince = (Spinner) findViewById(R.id.shipping_spinner_select_province);
        btnShippingAddress = (Button)findViewById(R.id.shipping_save);

        sDay = (EditText) findViewById(R.id.shipping_birthday_day);
        sMonth = (EditText) findViewById(R.id.shipping_birthday_month);
        sYear = (EditText) findViewById(R.id.shipping_birthday_year);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("please wait...");
        //CountryList();

        addressId = getIntent().getStringExtra("addressId");
        getShippingDetail();

        btnShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = sfullname.getText().toString().trim();
                String laddress = sAdress.getText().toString().trim();
                String email =sEmail.getText().toString().trim();
                String phone = sPhoneNo.getText().toString().trim();
                String city = sCity.getText().toString().trim();
                String postCode = sPostalCode.getText().toString().trim();
                //String city = bCity.getText().toString().trim();
//                String day = sDay.getText().toString().trim();
//                String month = sMonth.getText().toString().trim();
//                String year = sYear.getText().toString().trim();

//                Log.d("fname ", fname);
//                Log.d("laddress ", laddress);
//                Log.d("email ", email);
//                Log.d("phone ", phone);
//                Log.d("city ", city);
//                Log.d("postCode ", postCode);

                if (!fname.isEmpty() && !laddress.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !city.isEmpty()&& !postCode.isEmpty()
                    //&& !day.isEmpty() && !month.isEmpty() && !year.isEmpty()
                        ){

                    BillingShippingDetailModel model = new BillingShippingDetailModel();
                    model.setId(Integer.parseInt(shippingId.getText().toString()));
                    model.setFullName(fname);
                    model.setEmail(email);
                    model.setCity(city);
                    model.setAddress1(laddress);
                    model.setZipPostalCode(postCode);
                    model.setPhoneNumber(phone);
                    model.setCountryId(GlobalClass.shippingModel.getCountryId());
                    model.setStateProvinceId(GlobalClass.shippingModel.getStateProvinceId());

                    updateShippingAddress(model);

//                    BillingAddress billingAddress=new BillingAddress();
//                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame_content,billingAddress); // give your fragment container id in first parameter
//                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                    transaction.commit();


                }else {
                    Toast.makeText(getApplicationContext(), "Please enter the required fields!", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    public void getShippingDetail() {
        //progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        //creating json array list
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());
        params.put("AddressId", addressId);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_BILLING_SHIPPING_BYID, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        Gson gson = new Gson();
                        userDetailModel = gson.fromJson(response.toString(), new TypeToken<BillingShippingModel>(){}.getType());

                        //final CustomerAddressModel customerDetailModel = userDetailModel.getCustomerAddress();
                        ArrayList<CustomerAddressModel> customerDetailModel = new ArrayList<CustomerAddressModel>(userDetailModel.getCustomerAddress());

                        String userAddress =customerDetailModel.get(0).getAddress1();
                        String userCity =customerDetailModel.get(0).getCity();
                        String userName =customerDetailModel.get(0).getFirstName();
                        String userEmail =customerDetailModel.get(0).getEmail();
                        String userPhone=customerDetailModel.get(0).getPhoneNumber();
                        String postCode=customerDetailModel.get(0).getZipPostalCode();


                        sfullname.setText(userName);
                        sEmail.setText(userEmail);
                        sAdress.setText(userAddress);
                        sPhoneNo.setText(userPhone);
                        sCity.setText(userCity);
                        sPostalCode.setText(postCode);

                        ShippingModel model = new ShippingModel();
                        model.setFullName(customerDetailModel.get(0).getFirstName());
                        model.setEmail(customerDetailModel.get(0).getEmail());
                        model.setCity(customerDetailModel.get(0).getCity());
                        model.setAddress1(customerDetailModel.get(0).getAddress1());
                        model.setZipPostalCode(customerDetailModel.get(0).getZipPostalCode());
                        model.setPhoneNumber(customerDetailModel.get(0).getPhoneNumber());
                        model.setCountryId(customerDetailModel.get(0).getCountryId());
                        model.setStateProvinceId(customerDetailModel.get(0).getStateProvinceId());
                        shippingId.setText(customerDetailModel.get(0).getId());

                        GlobalClass.shippingModel = model;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                progressDialog.dismiss();
                //progressBar.setVisibility(View.GONE);
                //progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(objectRequest);

    }

    public void updateShippingAddress(final BillingShippingDetailModel shippingAddress) {
        //progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        //creating json array list
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);

        UpdateBillingAndShippingModel model = new UpdateBillingAndShippingModel();

        model.setProjectID(Config.PROJECTID);
        model.setShippingAddress(shippingAddress);

        Gson gson = new Gson();
        String json = gson.toJson(model);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_UPDATE_SHIPPING_DETAIL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        Gson gson = new Gson();
                        //BillingShippingModel userDetailModel = gson.fromJson(response.toString(), new TypeToken<BillingShippingModel>(){}.getType());

                        ShippingModel model = new ShippingModel();
                        model.setFullName(shippingAddress.getFullName());
                        model.setEmail(shippingAddress.getEmail());
                        model.setCity(shippingAddress.getCity());
                        model.setAddress1(shippingAddress.getAddress1());
                        model.setZipPostalCode(shippingAddress.getZipPostalCode());
                        model.setPhoneNumber(shippingAddress.getPhoneNumber());
                        model.setCountryId(GlobalClass.shippingModel.getCountryId());
                        model.setStateProvinceId(GlobalClass.shippingModel.getStateProvinceId());

                        GlobalClass.shippingModel = model;
                        Toast.makeText(getApplicationContext(), "Info save successfully! Also edit Billing Address", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                progressDialog.dismiss();
                //progressBar.setVisibility(View.GONE);
                //progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(objectRequest);

    }

}
