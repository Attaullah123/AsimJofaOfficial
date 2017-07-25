package com.cresset.asimjofaofficial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.api.AppController;
import com.cresset.asimjofaofficial.database.UserDataStore;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class IndiviualAccount extends Fragment {
//    private static final String TAG = IndiviualAccount.class.getSimpleName();
//    private View view;
//    private EditText firstName,lastName,emailAddress,etpassword,confirmPassword;
//    private AppCompatButton saveButton;
//    private ProgressDialog pDialog;
//    private UserSessionManager userSession;
//    private UserDataStore userDB;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.indiviual_account, container, false);
//       // getActivity().getActionBar().hide();
//
//        firstName = (EditText) view.findViewById(R.id.reg_first_name);
//        lastName = (EditText) view.findViewById(R.id.reg_last_name);
//        emailAddress = (EditText) view.findViewById(R.id.reg_email);
//        etpassword = (EditText) view.findViewById(R.id.reg_password);
//        confirmPassword = (EditText) view.findViewById(R.id.reg_confirm_password);
//        saveButton = (AppCompatButton) view.findViewById(R.id.btn_signup);
//
//        // Progress dialog
//        pDialog = new ProgressDialog(getContext());
//        pDialog.setCancelable(false);
//
//        // Session manager
//        userSession = new UserSessionManager(getContext());
//
//        // SQLite database handler
//        userDB = new UserDataStore(getContext());
//        //check if user logged already or not
//        if (userSession.isLoggedIn()){
//            Intent intent = new Intent(getContext(), Profile.class);
//            startActivity(intent);
//            getActivity().finish();
//        }
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String fname = firstName.getText().toString().trim();
//                String lname = lastName.getText().toString().trim();
//                String email = emailAddress.getText().toString().trim();
//                String password = etpassword.getText().toString().trim();
//                String confirmPas = confirmPassword.getText().toString().trim();
//
//                if (!fname.isEmpty() && !lname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPas.isEmpty()){
//                    registerUser(fname, lname, email, password, confirmPas);
//
//                }else {
//                    Toast.makeText(getContext(),
//                            "Please enter your details!", Toast.LENGTH_LONG)
//                            .show();
//                }
//
//            }
//        });
//
//        return view;
//    }
//
//    public void registerUser(final String fname, final String lname, final String email, final String password,final String confirmPassword){
//        // Tag used to cancel the request
//        String tag_string_req = "req_register";
//
//        pDialog.setMessage("Registering ...");
//        showDialog();
//
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                Config.URL_USER_REGISTER, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Register Response: " + response.toString());
//                hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//                    if (!error) {
//                        // User successfully stored in MySQL
//                        // Now store the user in sqlite
//                        String uid = jObj.getString("uid");
//
//                        JSONObject user = jObj.getJSONObject("user");
//                        String fname = user.getString("name");
//                        String lname = user.getString("name");
//                        String email = user.getString("email");
//                        String created_at = user
//                                .getString("created_at");
//
//                        // Inserting row in users table
//                        userDB.addUser(fname,lname, email, uid, created_at);
//
//                        Toast.makeText(getContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
//
//                        // Launch login activity
//                        Intent intent = new Intent(getContext(), Profile.class);
//                        startActivity(intent);
//                        getActivity().finish();
//                    } else {
//
//                        // Error occurred in registration. Get the error
//                        // message
//                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Registration Error: " + error.getMessage());
//                Toast.makeText(getContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to register url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("fname", fname);
//                params.put("lname", lname);
//                params.put("email", email);
//                params.put("password", password);
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(strReq);
//    }
//
//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }


}
