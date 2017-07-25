package com.cresset.asimjofaofficial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

/**
 * Created by cresset on 13/05/2017.
 */

public class DropDown extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drop_down);

        final Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), button);
                popupMenu.getMenuInflater().inflate(R.menu.size_menu,popupMenu.getMenu());
                popupMenu.show();
                Toast.makeText(getApplicationContext(),"button clicked",Toast.LENGTH_LONG).show();
            }
        });
    }
}
