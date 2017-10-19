package com.cresset.asimjofaofficial;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.database.UserDataStore;
import com.cresset.asimjofaofficial.models.CartModel;
import com.cresset.asimjofaofficial.models.GuestOrLoginResponseModel;
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.utilities.UserSessionManager;
import com.cresset.asimjofaofficial.volley.MyAccount1;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private EditText firstName,lastName,emailAddress,etpassword,confirmPassword,etDay,etMonth,etYear;
    private AppCompatButton saveButton;
    private ProgressDialog progressDialog;
    private UserSessionManager userSession;
    private UserDataStore userDB;
    private UserModel globalUserData;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        firstName = (EditText) findViewById(R.id.reg_first_name);
        lastName = (EditText) findViewById(R.id.reg_last_name);
        emailAddress = (EditText) findViewById(R.id.reg_email);
        etpassword = (EditText) findViewById(R.id.reg_password);
        confirmPassword = (EditText) findViewById(R.id.reg_confirm_password);
        saveButton = (AppCompatButton) findViewById(R.id.btn_signup);

        etDay = (EditText) findViewById(R.id.birthday_day);
        etMonth = (EditText) findViewById(R.id.birthday_month);
        etYear = (EditText) findViewById(R.id.birthday_year);

        gson = new Gson();
        sharedPreferencesEditor = getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE).edit();

        globalUserData = GlobalClass.userData;
        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("please wait...");

/*
        // Session manager
        userSession = new UserSessionManager(getApplicationContext());

        // SQLite database handler
        userDB = new UserDataStore(getApplicationContext());
        //check if user logged already or not
        if (userSession.isLoggedIn()){
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);
            finish();
        }
*/
        if(!globalUserData.isGuest() && globalUserData.getUserName() != null && globalUserData.getUserName() != "")
        {
            Intent intent = new Intent(getApplicationContext(), MyAccount1.class);
            startActivity(intent);
            finish();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = firstName.getText().toString().trim();
                String lname = lastName.getText().toString().trim();
                String email = emailAddress.getText().toString().trim();
                String password = etpassword.getText().toString().trim();
                String confirmPas = confirmPassword.getText().toString().trim();
                String day = etDay.getText().toString().trim();
                String month = etMonth.getText().toString().trim();
                String year = etYear.getText().toString().trim();

                if (!fname.isEmpty() && !lname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPas.isEmpty()
                        && !day.isEmpty() && !month.isEmpty() && !year.isEmpty()){
                    registerUser(fname, lname, email, password, confirmPas ,day, month, year);

                }else {
                    Toast.makeText(getApplicationContext(), "Please enter the required fields!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void registerUser(final String fname, final String lname, final String email, final String password,final String confirmPassword,
                             final String day, final String month,final String year){
        progressDialog.show();
        HashMap<String,String> params = new HashMap<>();
        params.put("ProjectId",Config.PROJECTID);
        params.put("FirstName", fname);
        params.put("LastName", lname);
        params.put("Email", email);
        params.put("Pasword", password);
        params.put("Day", day);
        params.put("month", month);
       params.put("year", year);

        @SuppressLint("WifiManagerLeak") WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        params.put("IpAddress", ip);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_USER_REGISTER, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            JSONObject jObj = new JSONObject(response.toString());
                            String result = jObj.getString("Status");
                            progressDialog.dismiss();

                            Gson gson = new Gson();
                            GuestOrLoginResponseModel model = gson.fromJson(response.toString(), new TypeToken<GuestOrLoginResponseModel>(){}.getType());

                            //show("Customer id:" + model.getCustomerId());

                            if (model.getCustomerId() != null && model.getCustomerId() != "") {

                                UserModel userData = new UserModel();
                                userData.setUserID(model.getCustomerId());
                                userData.setUserName(fname);
                                userData.setEmail(email);

                                userData.setBirthdayDay(day);
                                userData.setBirthdayMonth(month);
                                userData.setBirthdayYear(year);

                                userData.setGuest(false);

                                GlobalClass.userData = userData;
                                String json = gson.toJson(userData);
                                sharedPreferencesEditor.putString(Config.RegisteredPreference,json);
                                sharedPreferencesEditor.commit();

                                show("Registered successfully!");
                            }

                            // Launch login activity
                            Intent intent = new Intent(getApplicationContext(), MyAccount1.class);
                            startActivity(intent);
                            finish();

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't register  , please check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(objectRequest);
    }

    public void show(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }


}
