package com.cresset.asimjofaofficial.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.*;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.cresset.asimjofaofficial.R;

import com.cresset.asimjofaofficial.models.ProductDetailList;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class ProductImagePagerAdapter extends android.support.v4.view.PagerAdapter {
    //private ArrayList<Integer> imageLink;
    private ArrayList<String> imagesList;
    private ArrayList<ProductDetailList> productDetailLists;
    private LayoutInflater inflater;
    private Context mContext;


    public ProductImagePagerAdapter(Context context, ArrayList<String> imagesList){
        this.mContext = context;
       this.imagesList = imagesList;
        //this.productDetailLists = detailLists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        final String image = imagesList.get(position).toString();

        View imageLayout = inflater.inflate(R.layout.product_image_viewer, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.product_image_viewer);
        final ProgressBar progressBar = (ProgressBar) imageLayout.findViewById(R.id.progressBar);

        //imageView.setOnTouchListener(new ImageMatrixTouchHandler(view.getContext()));

        Picasso.with(mContext).load(image)
                .placeholder(R.drawable.placeholder_loading)
                .fit().centerInside()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);

                    }
                });

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}