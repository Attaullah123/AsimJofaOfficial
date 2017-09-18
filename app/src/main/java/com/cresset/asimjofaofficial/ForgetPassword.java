package com.cresset.asimjofaofficial;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ForgetPassword extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView back;
    private EditText etForgetEmail;
    private Button retrievePassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        //toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        etForgetEmail = (EditText) findViewById(R.id.forget_email);
        retrievePassword = (Button) findViewById(R.id.retrieve_password);

        back = (ImageView) findViewById(R.id.img_cross);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
