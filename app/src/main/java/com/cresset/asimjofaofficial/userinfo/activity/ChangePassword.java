package com.cresset.asimjofaofficial.userinfo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.Profile11;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.ChnagePasswordModel;
import com.cresset.asimjofaofficial.models.GuestOrLoginResponseModel;
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.volley.MyAccount1;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    private static final String TAG = ChangePassword.class.getSimpleName();

    private EditText etEmail,etPassword,etNewPassword;
    private Button saveChangePassword;
    private ProgressDialog pDialog;
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        //userData = GlobalClass.userData;
        //etEmail = (EditText) findViewById(R.id.email_change_password);
        etPassword = (EditText) findViewById(R.id.password);
        etNewPassword = (EditText) findViewById(R.id.new_password);
        saveChangePassword = (Button) findViewById(R.id.password_save);
        back = (ImageView) findViewById(R.id.img_cross);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        saveChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cahngePassword();
            }
        });
    }

    public void cahngePassword() {
       // String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        // Check for empty data in the form
        if (!password.isEmpty() && !newPassword.isEmpty()) {
            // login user
            checkChangePassword(password, newPassword);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the required field!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void checkChangePassword(final String password,final String newPassword){
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("Email", GlobalClass.userData.getEmail());
        params.put("OldPassword", password);
        params.put("NewPassword", newPassword);

        pDialog.setMessage("please wait ...");
        showDialog();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_UPDATE_PASSWORD, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "Login Response: " + response.toString());
                        hideDialog();

                        try {
                            Gson gson = new Gson();
                            ChnagePasswordModel model = gson.fromJson(response.toString(), new TypeToken<ChnagePasswordModel>(){}.getType());

                            //model.setEmail(email);
                            model.setOldPassword(password);
                            model.setNewPassword(newPassword);
                            Toast.makeText(getApplicationContext(), "your password change successfully", Toast.LENGTH_LONG)
                                    .show();
                            Intent intent = new Intent(getApplicationContext(), MyAccount1.class);
                            startActivity(intent);
                            finish();
//                            if (model.getCustomerId() != null && model.getCustomerId() != "") {
//
//                                UserModel userData = new UserModel();
//                                userData.setUserID(model.getCustomerId());
//                                userData.setGuest(false);
//                                userData.setEmail(email);
//                            }else {
//                                Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
//                            }

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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();

    }

}
