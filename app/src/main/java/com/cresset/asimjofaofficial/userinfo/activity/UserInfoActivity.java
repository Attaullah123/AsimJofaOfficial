package com.cresset.asimjofaofficial.userinfo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
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
import com.cresset.asimjofaofficial.MyAccount;
import com.cresset.asimjofaofficial.Profile;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.adapter.ProductListAdapter;
import com.cresset.asimjofaofficial.database.UserDataStore;
import com.cresset.asimjofaofficial.models.ChnagePasswordModel;
import com.cresset.asimjofaofficial.models.CustomerDetailModel;
import com.cresset.asimjofaofficial.models.GuestOrLoginResponseModel;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.ProductModel;
import com.cresset.asimjofaofficial.models.UserDetailModel;
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.cresset.asimjofaofficial.utilities.UserSessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.cresset.asimjofaofficial.R.id.progressBar;


public class UserInfoActivity extends AppCompatActivity {
    private static final String TAG = ChangePassword.class.getSimpleName();
    private EditText etuserName,etuserEmail;
    private ProgressDialog progressDialog;
    ImageView back;
    //private Button saveUserInfo;
    private UserSessionManager userSession;
    private UserDataStore userDB;
    private UserModel globalUserData;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Gson gson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        etuserName = (EditText) findViewById(R.id.user_name);
        etuserEmail = (EditText) findViewById(R.id.user_email);
       // saveUserInfo = (Button) findViewById(R.id.change_user_info);

        back = (ImageView) findViewById(R.id.img_cross);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("please wait");
        gson = new Gson();
        sharedPreferencesEditor = getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE).edit();

        globalUserData = GlobalClass.userData;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        saveUserInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String fname = etuserName.getText().toString().trim();
//                String email = etuserEmail.getText().toString().trim();
//
//                if (!fname.isEmpty() && !email.isEmpty()){
//                    saveChnageInfo(fname, email);
//
//                }else {
//                    Toast.makeText(getApplicationContext(),
//                            "Please enter the required fields!", Toast.LENGTH_LONG)
//                            .show();
//                }
//
//            }
//        });

//        userName.setText("Attaullah");
//        userEmail.setText("atta.789@gmail.com");

        getUserInfo();
    }
    public void getUserInfo() {
        //progressBar.setVisibility(View.VISIBLE);

        //creating json array list
        Map<String, String> params = new HashMap<String, String>();
        params.put("ProjectId", Config.PROJECTID);
        params.put("CustomerId", GlobalClass.userData.getUserID());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_USER_DETAIL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //CategoryModel categoryModel = new CategoryModel();

                        Log.d("Response", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        Gson gson = new Gson();
                        UserDetailModel userDetailModel = gson.fromJson(response.toString(), new TypeToken<UserDetailModel>(){}.getType());

                        final CustomerDetailModel customerDetailModel = userDetailModel.getCustomerDetail();
                        String userName =customerDetailModel.getUserName();
                        String userEmail =customerDetailModel.getEmail();
                        etuserName.setText(userName);
                        etuserEmail.setText(userEmail);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error.toString());
                //progressBar.setVisibility(View.GONE);
                //progressDialog.dismiss();
            }
        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(objectRequest);
        CustomVolleyRequest.getInstance(getApplicationContext()).getRequestQueue().add(objectRequest);
    }

//    public void saveChnageInfo(final String fname, final String email){
//        progressDialog.show();
//        HashMap<String,String> params = new HashMap<>();
//        params.put("ProjectId",Config.PROJECTID);
//        params.put("FirstName", fname);
//        params.put("Email", email);
////        params.put("Day", day);
////        params.put("month", month);
////        params.put("year", year);
//
//        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
//        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
//
//        params.put("IpAddress", ip);
//
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_USER_REGISTER, new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try{
//
//                            JSONObject jObj = new JSONObject(response.toString());
//                            String result = jObj.getString("Status");
//                            progressDialog.dismiss();
//
//                            Gson gson = new Gson();
//                            GuestOrLoginResponseModel model = gson.fromJson(response.toString(), new TypeToken<GuestOrLoginResponseModel>(){}.getType());
//
//                            //show("Customer id:" + model.getCustomerId());
//
//                            if (model.getCustomerId() != null && model.getCustomerId() != "") {
//
//                                UserModel userData = new UserModel();
//                                userData.setUserID(model.getCustomerId());
//                                userData.setUserName(fname);
//                                userData.setEmail(email);
//                                userData.setGuest(false);
//
//                                GlobalClass.userData = userData;
//                                String json = gson.toJson(userData);
//                                sharedPreferencesEditor.putString(Config.RegisteredPreference,json);
//                                sharedPreferencesEditor.commit();
//
//                                Toast.makeText(getApplicationContext(), "svae info successfully!", Toast.LENGTH_SHORT).show();
//                            }
//
//                            // Launch login activity
//                            Intent intent = new Intent(getApplicationContext(), MyAccount.class);
//                            startActivity(intent);
//                            finish();
//
//                        }catch (JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Couldn't register user, please check connection", Toast.LENGTH_SHORT).show();
//                Log.d("Error", error.toString());
//                progressDialog.dismiss();
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(objectRequest);
//    }

}

