package com.cresset.asimjofaofficial;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.models.ForgetPasswordModel;
import com.cresset.asimjofaofficial.models.GuestOrLoginResponseModel;
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.userinfo.activity.ChangePassword;
import com.cresset.asimjofaofficial.utilities.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {
    private static final String TAG = ChangePassword.class.getSimpleName();
    private Toolbar toolbar;
    private ImageView back;
    private EditText etForgetEmail;
    private Button retrievePassword;
    private ProgressDialog pDialog;
    private TextView passCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);


        toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        //toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        etForgetEmail = (EditText) findViewById(R.id.forget_email);
        passCode = (TextView) findViewById(R.id.code_reset);
        retrievePassword = (Button) findViewById(R.id.retrieve_password);

        back = (ImageView) findViewById(R.id.img_cross);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        retrievePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPasswordCode();
            }
        });
    }
    public void getPasswordCode() {
        String email = etForgetEmail.getText().toString().trim();

        // Check for empty data in the form
        if (!email.isEmpty()) {
            // login user
            getPassword(email);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the required field!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void getPassword(final String email){
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("Email", "test1@gmail.com");
        String tag_string_req = "req_login";

        pDialog.setMessage("Please wait ...");
        showDialog();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_FORGET_PASSWORD, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "Login Response: " + response.toString());
                        hideDialog();

                        try {
                            Gson gson = new Gson();
                            ForgetPasswordModel model = gson.fromJson(response.toString(), new TypeToken<ForgetPasswordModel>(){}.getType());

                            //Toast.makeText(getApplicationContext(), "Customer id:" + model.getCustomerId() , Toast.LENGTH_SHORT).show();
                            model.setEmail(email);
                            String userName =model.getPassword();
                            passCode.setText(userName);



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
