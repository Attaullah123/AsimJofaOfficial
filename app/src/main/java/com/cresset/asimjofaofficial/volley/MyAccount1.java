package com.cresset.asimjofaofficial.volley;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cresset.asimjofaofficial.ContactUs;
import com.cresset.asimjofaofficial.MainActivity;
import com.cresset.asimjofaofficial.MyAccount;
import com.cresset.asimjofaofficial.Profile11;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.CustomerDetailResponse;
import com.cresset.asimjofaofficial.models.UserModel;
import com.cresset.asimjofaofficial.userinfo.activity.AddressBookActivity;
import com.cresset.asimjofaofficial.userinfo.activity.ChangePassword;
import com.cresset.asimjofaofficial.userinfo.activity.MultipleAdressBook;
import com.cresset.asimjofaofficial.userinfo.activity.OrderHistory;
import com.cresset.asimjofaofficial.userinfo.activity.UserInfoActivity;
import com.cresset.asimjofaofficial.utilities.Config;
import com.cresset.asimjofaofficial.utilities.GlobalClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class MyAccount1 extends Fragment {
    private TextView txName,txEmail,userOrderInfo,userAccInfo,contactUs,logout;
    private LinearLayout lyOrderHistory,lyUserInfo,lyAddressBook,lyContactUs,lyChnagePassword;

    private UserModel globalUserData;
    //private ImageView back;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private static final String TAG = MyAccount1.class.getSimpleName();
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_account, container, false);

        //txName = (TextView) findViewById(R.id.name);
        txName = (TextView) view.findViewById(R.id.acc_name);

        lyOrderHistory = (LinearLayout) view.findViewById(R.id.ly_order_history);
        lyUserInfo = (LinearLayout) view.findViewById(R.id.ly_personal_details);
        lyAddressBook = (LinearLayout) view.findViewById(R.id.ly_address_book);
        lyContactUs = (LinearLayout) view.findViewById(R.id.ly_contact_us);
        lyChnagePassword = (LinearLayout) view.findViewById(R.id.ly_change_password);

        logout = (TextView) view.findViewById(R.id.log_out);
        //back = (ImageView) view.findViewById(R.id.img_back);

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });

        sharedPreferencesEditor = getActivity().getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE).edit();

        lyUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                startActivity(intent);
            }
        });

        lyOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderHistory.class);
                startActivity(intent);
            }
        });

        lyAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MultipleAdressBook.class);
                startActivity(intent);
            }
        });

        lyContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContactUs.class);
                startActivity(intent);
            }
        });

        lyChnagePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
        globalUserData = GlobalClass.userData;
        if (globalUserData.isGuest()) {
            Intent intent = new Intent(getContext(), Profile11.class);
            startActivity(intent);
            getActivity().finish();
        } else {
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


        return view;
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
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        Toast.makeText(getContext(),"You Logout", Toast.LENGTH_LONG).show();
        getActivity().finish();
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
                Toast.makeText(getContext(), "Couldn't feed refresh, check connection", Toast.LENGTH_LONG).show();
            }
        });
        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(objectRequest);
    }

}

