package com.cresset.asimjofaofficial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.api.AppController;
import com.cresset.asimjofaofficial.database.UserDataStore;
import com.cresset.asimjofaofficial.models.GuestOrLoginResponseModel;
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.utilities.UserSessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Profile extends AppCompatActivity {
    private static final String TAG = Profile.class.getSimpleName();
    private Toolbar toolbar;
    private Button btn_login,btn_create_account;
    private EditText et_email,et_password;
    private ProgressDialog pDialog;
    //private ImageView back;
    //private ImageView img_cross;
    private UserModel userData;
    private TextView forgetPassword;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setBackgroundColor(Color.parseColor("#ffffff"));

        gson = new Gson();
        sharedPreferencesEditor = getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE).edit();
        sharedPreferences = getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_create_account = (Button) findViewById(R.id.create_account);
        //back = (ImageView) findViewById(R.id.img_cross);
        et_email = (EditText) findViewById(R.id.login_email);
        et_password = (EditText) findViewById(R.id.login_password);
        forgetPassword = (TextView) findViewById(R.id.forget_password);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        userData = GlobalClass.userData;

        //go to forget activity
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(intent);
            }
        });

        if (userData!= null){

            if(!userData.isGuest() && userData.getUserName() != null && userData.getUserName() != "")
            {
                Intent intent = new Intent(getApplicationContext(), MyAccount.class);
                startActivity(intent);
                finish();
            }
        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                vibrator.vibrate(80);
                startActivity(intent);

            }
        });


//       back.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               onBackPressed();
//           }
//       });
    }

    public void userLogin(){
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        // Check for empty data in the form
        if (!email.isEmpty() && !password.isEmpty()) {
            // login user
                checkLogin(email, password);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void checkLogin(final String email, final String password){
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("Email", email);
        params.put("Password", password);
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_LOGIN_USER, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "Login Response: " + response.toString());
                        hideDialog();

                try {
                    Gson gson = new Gson();
                    GuestOrLoginResponseModel model = gson.fromJson(response.toString(), new TypeToken<GuestOrLoginResponseModel>(){}.getType());

                    //Toast.makeText(getApplicationContext(), "Customer id:" + model.getCustomerId() , Toast.LENGTH_SHORT).show();

                    if (model.getCustomerId() != null && model.getCustomerId() != "") {

                        UserModel userData = new UserModel();
                        userData.setUserID(model.getCustomerId());
                        userData.setGuest(false);
                        userData.setEmail(email);

                        GuestUserchangeToLogin(GlobalClass.userData,userData);
                    }else {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    }



                } catch (Exception e) {
                    // JSON error
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "please check connection", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(objectRequest);
    }

    public void GuestUserchangeToLogin(UserModel guest, final UserModel registered){
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("GuestUserId", guest.getUserID());
        params.put("LoginUserId", registered.getUserID());
        String tag_string_req = "req_login";

        pDialog.setMessage("Updating user data in ...");
        showDialog();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_Guest_User_ChangeToLogin, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "Login Response: " + response.toString());
                        hideDialog();

                        try {
                            GlobalClass.userData = registered;
                            String json = gson.toJson(registered);
                            sharedPreferencesEditor.putString(Config.RegisteredPreference,json);
                            sharedPreferencesEditor.commit();

                            Toast.makeText(getApplicationContext(), "You are login Successfully!", Toast.LENGTH_LONG).show();


                            Intent intent = new Intent(Profile.this,MyAccount.class);
                            startActivity(intent);
                            finish();
                            //Gson gson = new Gson();
                            //GuestOrLoginResponseModel model = gson.fromJson(response.toString(), new TypeToken<GuestOrLoginResponseModel>(){}.getType());
                        } catch (Exception e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(objectRequest);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();

    }

    }



