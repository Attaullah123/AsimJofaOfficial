package com.cresset.asimjofaofficial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.models.CustomerDetailResponse;
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.userinfo.activity.OrderHistory;
import com.cresset.asimjofaofficial.userinfo.activity.UserInfoActivity;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MyAccount extends AppCompatActivity {

    private TextView txName,txEmail,userOrderInfo,userAccInfo,contactUs,logout;
    private UserModel globalUserData;
    private ImageView back;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private static final String TAG = IndiviualAccount.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        //txName = (TextView) findViewById(R.id.name);
        txName = (TextView) findViewById(R.id.acc_name);
        userOrderInfo = (TextView) findViewById(R.id.acc_order_placed);
        userAccInfo = (TextView) findViewById(R.id.acc_user_info);
        contactUs = (TextView) findViewById(R.id.contact_us);
        logout = (TextView) findViewById(R.id.log_out);
        back = (ImageView) findViewById(R.id.img_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreferencesEditor = getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE).edit();

        userAccInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(intent);
            }
        });

        userOrderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderHistory.class);
                startActivity(intent);
            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(intent);
            }
        });
        globalUserData = GlobalClass.userData;
        if(globalUserData.isGuest())
        {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);
            finish();
        }
        else{
            GuestUserDetail();
        }

        // Displaying the user details on the screen
        //txName.setText(userData.getUserName());
       //txEmail.setText(userData.getUserName());

        // Logout button click event
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {

        GlobalClass.userData = null;
        sharedPreferencesEditor.putString(Config.RegisteredPreference,"");
        sharedPreferencesEditor.commit();

/*
        MainActivity main = new MainActivity();
        main.RegisterGuestUser();
*/

        // Launching the login activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void GuestUserDetail(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_Customer_Detail, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "User Detail Response: " + response.toString());

                        try {
                            Gson gson = new Gson();
                            CustomerDetailResponse model = gson.fromJson(response.toString(), new TypeToken<CustomerDetailResponse>(){}.getType());
                            if(model.getCustomerDetail() != null){
                                GlobalClass.userData.setUserName(model.getCustomerDetail().getUserName());
                                GlobalClass.userData.setEmail(model.getCustomerDetail().getEmail());
                            }

                            String json = gson.toJson(GlobalClass.userData);
                            sharedPreferencesEditor.putString(Config.RegisteredPreference,json);
                            sharedPreferencesEditor.commit();

                            txName.setText(GlobalClass.userData.getUserName());

                        } catch (Exception e) {
                            // JSON error
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "User Detail Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_LONG).show();
            }
        });
        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(objectRequest);
    }

}
