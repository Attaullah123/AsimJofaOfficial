package com.cresset.asimjofaofficial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cresset.asimjofaofficial.models.ResourceValueModel;
import com.cresset.asimjofaofficial.models.ResourceValueResponse;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.volley.AppController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;


public class FinalOrderActivity extends AppCompatActivity {

    private TextView oderId,order_message;
    private Button backButton;
    private static boolean userPressedBackAgain = false;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_place_screen);

        oderId = (TextView) findViewById(R.id.order_id);
        order_message = (TextView) findViewById(R.id.order_message);
        backButton = (Button) findViewById(R.id.back_shop);

        Intent intent = getIntent();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("please wait progressing...");

        String id = intent.getStringExtra("orderId");
        String paymentMethod = intent.getStringExtra("paymentMethod");
        oderId.setText(id);

        if(paymentMethod.equals("BankTransfer")){
            order_message.setText("");
            OrderPlace("checkout.banktransferdetail");
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void OrderPlace(String paymentMethod){
        progressDialog.show();
        ResourceValueModel model = new ResourceValueModel();
        model.setProjectId(Config.PROJECTID);
        model.setResourceName(paymentMethod);

        Gson gson = new Gson();
        String json = gson.toJson(model);

        Log.i("Order Placed",json);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_BANK_TRANSCTION, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Gson gson = new Gson();
                            ResourceValueResponse resourceResponse = gson.fromJson(response.toString(), new TypeToken<ResourceValueResponse>(){}.getType());

                            String json = gson.toJson(resourceResponse);
                            progressDialog.dismiss();

                            Log.i("resourceResponse",json);

                            if(!resourceResponse.getResourceValue().equals(null) && !resourceResponse.getResourceValue().equals("")){
                                order_message.setText(Html.fromHtml(resourceResponse.getResourceValue()));
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
                 progressDialog.dismiss();
            }


        });

        objectRequest.setRetryPolicy(AppController.getDefaultRetryPolice());
        objectRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(objectRequest, Config.tag_json_obj);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
