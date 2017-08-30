package com.cresset.asimjofaofficial;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cresset on 22/08/2017.
 */

public class ShowFullImage extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_image_show);

        imageView = (ImageView) findViewById(R.id.imageView4);
        imageView.buildDrawingCache();
        ///Bitmap image= imageView.getDrawingCache();


        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("picture");

        imageView.setImageBitmap(bmp);
    }
}
