package com.cresset.asimjofaofficial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class FinalOrderActivity extends AppCompatActivity {

    private TextView oderId;
    private Button backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_place_screen);

        oderId = (TextView) findViewById(R.id.order_id);
        backButton = (Button) findViewById(R.id.back_shop);

        Intent intent = getIntent();

        String id = intent.getStringExtra("orderId");
        oderId.setText(id);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
