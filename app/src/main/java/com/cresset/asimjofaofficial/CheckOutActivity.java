package com.cresset.asimjofaofficial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.adapter.CartAdapter;
import com.cresset.asimjofaofficial.adapter.CartSpinnerAdapter;
import com.cresset.asimjofaofficial.adapter.CheckoutCartAdapter;
import com.cresset.asimjofaofficial.adapter.CheckoutProductAdapter;
import com.cresset.asimjofaofficial.adapter.IndexAdapter;
import com.cresset.asimjofaofficial.adapter.SizeSpinnerAdapter;
import com.cresset.asimjofaofficial.models.CartModel;
import com.cresset.asimjofaofficial.models.CartModelItems;
import com.cresset.asimjofaofficial.models.IndexImage;
import com.cresset.asimjofaofficial.models.OrderPlaceModel;
import com.cresset.asimjofaofficial.models.OrderPlaceResponse;
import com.cresset.asimjofaofficial.models.ProductDetailSize;
import com.cresset.asimjofaofficial.models.ProductHeader;
import com.cresset.asimjofaofficial.recylerview.RecyclerDivider;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.volley.AppController;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView selectShippingandBillingAdd,selectShippingMethod, selectPaymentMethod,selectShippingMethodName, paymentName,
            totalPrice,totalProductPrice,shippingPrice;
    private TextView finaliseOrder,itemCount;
    private CartModel cartModel;
    private ExpandableRelativeLayout expandableAccuracy;
    private ImageView accuracyMinus, accuracyPlus;
    private TextView cancel;
    private CheckoutCartAdapter cartAdapter;
    private CartModel cartModelData;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_activity);

        selectShippingandBillingAdd = (TextView) findViewById(R.id.shipping_edit_method);
        selectShippingMethod = (TextView) findViewById(R.id.shipping_edit);
        selectPaymentMethod = (TextView) findViewById(R.id.payment_edit);
        paymentName = (TextView) findViewById(R.id.payment_method_name);
        totalPrice = (TextView) findViewById(R.id.total_price);
        totalProductPrice = (TextView) findViewById(R.id.total_product_price);
        selectShippingMethodName = (TextView) findViewById(R.id.select_shipping_method);
        shippingPrice = (TextView) findViewById(R.id.country_shipping_price);
        itemCount = (TextView) findViewById(R.id.cart_items);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.addItemDecoration(new RecyclerDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cartAdapter);

       // expandableAccuracy.collapse();
        expandableAccuracy = (ExpandableRelativeLayout) findViewById(R.id.expandable_in_store);

        accuracyMinus = (ImageView) findViewById(R.id.img_minus_accuracy);
        accuracyPlus = (ImageView) findViewById(R.id.img_plus_accuracy);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        cancel = (TextView) findViewById(R.id.txt_cancel);

        accuracyMinus.setOnClickListener(this);
        accuracyPlus.setOnClickListener(this);

        //initialize cart method getting detail from cart activity
        getCartDetail();

        //initialize cart count
        GetCartItemsCount();

        finaliseOrder = (TextView) findViewById(R.id.finalise_order);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //shipping & billing select method call also check credential

        expandableAccuracy.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                accuracyMinus.setVisibility(View.VISIBLE);
                accuracyPlus.setVisibility(View.GONE);
            }

            @Override
            public void onPreClose() {
                accuracyMinus.setVisibility(View.GONE);
                accuracyPlus.setVisibility(View.VISIBLE);
            }
        });
        selectShippingandBillingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //use check for get info back to checkout screen
                if(GlobalClass.shippingModel != null){
                }

                if(GlobalClass.billingModel != null){
                }

                Intent intent = new Intent(getApplicationContext(), ShippingBillingAddress.class);
                startActivity(intent);
            }
        });

        //shipping select method call also check credential

        selectShippingMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(GlobalClass.shippingModel != null){
                    String countryId = GlobalClass.shippingModel.getCountryId();

                    if(!countryId.equals(null) && countryId != ""){
                        Intent intent = new Intent(getApplicationContext(), ShippingMethod.class);
                        startActivity(intent);
                    }
                    else
                        Show("Please select shipping country!");
                }
                else{
                    Show("Please select shipping country!");
                }
            }
        });

        //select payment method call also check credential
        selectPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalClass.billingModel != null){
                    String countryId = GlobalClass.billingModel.getCountryId();
                    if(!countryId.equals(null) && countryId != ""){
                        Intent intent = new Intent(getApplicationContext(), PaymentMethod.class);
                        startActivity(intent);
                    }
                    else
                        Show("Please select billing country!");
                }
                else{
                    Show("Please select billing country!");
                }
            }
        });
        //use check for get selected item name to checkout screen

        if (GlobalClass.shippingMethod!= null){
            shippingPrice.setText(GlobalClass.shippingMethod.getPrice());
        }

        if (GlobalClass.shippingMethod!= null){
            selectShippingMethodName.setText(GlobalClass.shippingMethod.getName());
        }

        if (GlobalClass.paymentModel!= null ){
            paymentName.setText(GlobalClass.paymentModel.getName());
        }

        //initialize finalise order with credentials

        finaliseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GlobalClass.shippingModel != null && GlobalClass.billingModel != null)
                {
                    if(GlobalClass.paymentModel != null && GlobalClass.shippingMethod != null){
                        OrderPlace();
                    }
                    else{
                        Show("Please select payement and shipping method first");
                    }
                }
                else{
                    Show("Please fill billing & shipping address first");
                }
                //EmptyStaticObjects();
            }
        });
    }

    public void OrderPlace(){
        OrderPlaceModel model = new OrderPlaceModel();
        model.setProjectId(Config.PROJECTID);
        model.setCustomerId(GlobalClass.userData.getUserID());
        model.setShippingBillingAddressSame(true);
        model.setPaymentMethodSystemName(GlobalClass.paymentModel.getSystemName());
        model.setShippingMethodSystemName(GlobalClass.shippingMethod.getShippingRateComputationMethodSystemName());

        model.setBillingAddress(GlobalClass.billingModel);
        model.setShippingAddress(GlobalClass.shippingModel);

        Gson gson = new Gson();
        String json = gson.toJson(model);

        Log.i("Order Placed",json);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_PlaceOrder, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Gson gson = new Gson();
                            OrderPlaceResponse ordResponse = gson.fromJson(response.toString(), new TypeToken<OrderPlaceResponse>(){}.getType());

                            if(!ordResponse.getOrderId().equals(null)){
                                Show("Order placed: " + ordResponse.getOrderId());
                                Intent intent = new Intent(getApplicationContext(), FinalOrderActivity.class);
                                intent.putExtra("orderId",  ordResponse.getOrderId());
                                startActivity(intent);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Couldn't place order, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                // progressDialog.dismiss();
            }


        });

        AppController.getInstance().addToRequestQueue(objectRequest);

    }

    public void getCartDetail() {
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

                        Gson gson = new Gson();
                        cartModel = gson.fromJson(response.toString(), new TypeToken<CartModel>(){}.getType());

                        float total = cartModel.getTotalDetail().getSubTotalAmount();
                        totalProductPrice.setText(Float.toString(total));
                        if(GlobalClass.shippingMethod != null){
                           total = total + Float.parseFloat(GlobalClass.shippingMethod.getPrice());
                        }
                        totalPrice.setText(Float.toString(total));

                        ArrayList<CartModelItems> cartItems = new ArrayList<CartModelItems>(cartModel.getCartItems());

                        cartAdapter = new CheckoutCartAdapter(getApplicationContext(), cartItems);
                        recyclerView.setAdapter(cartAdapter);
//
//                        indexAdapter = new CheckoutProductAdapter(getApplicationContext(), cartItems);
//                        expandList.setAdapter(indexAdapter);
//                        cartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    //get cart item in cart

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

    public void Show(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalClass.shippingMethod!= null){
            shippingPrice.setText(GlobalClass.shippingMethod.getPrice());
        }
        if (GlobalClass.shippingMethod!= null){
            selectShippingMethodName.setText(GlobalClass.shippingMethod.getName());
            getCartDetail();
        }

        if (GlobalClass.paymentModel!= null ){
            paymentName.setText(GlobalClass.paymentModel.getName());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_minus_accuracy:
                expandableAccuracy.collapse();
                break;
            case R.id.img_plus_accuracy:
                expandableAccuracy.expand();
                break;
        }
    }
}
