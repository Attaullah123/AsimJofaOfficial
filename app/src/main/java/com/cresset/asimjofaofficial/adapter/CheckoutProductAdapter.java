package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.CartModel;
import com.cresset.asimjofaofficial.models.CartModelItems;
import com.cresset.asimjofaofficial.models.ChildCategoryList;
import com.cresset.asimjofaofficial.models.IndexImage;
import com.cresset.asimjofaofficial.models.ProductHeader;

import java.util.ArrayList;
import java.util.List;


public class CheckoutProductAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<CartModel> cartModel;
    private TextView proName,proPrice,proQuantity,proSize;
    private ImageView cartImage;

    //ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public CheckoutProductAdapter(Context context, ArrayList<CartModel> groups) {
        this.context = context;
        this.cartModel = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //ArrayList<ChildCategoryList> chList = indexImages.get(groupPosition).getChild();
        ArrayList<CartModelItems> chList = (ArrayList<CartModelItems>) cartModel.get(groupPosition).getCartItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        CartModelItems child = (CartModelItems) getChild(groupPosition, childPosition);
        if (convertView == null) {LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.checkout_child_list, null);
        }


        proName = (TextView) convertView.findViewById(R.id.product_name);
        proPrice = (TextView) convertView.findViewById(R.id.product_cart_price);
        proQuantity = (TextView) convertView.findViewById(R.id.cart_product_quantity);
        proSize = (TextView) convertView.findViewById(R.id.product_cart_size);
        //proQuantity = (TextView) convertView.findViewById(R.id.cart_product_quantity);
        cartImage = (ImageView) convertView.findViewById(R.id.cart_thumbnail);
        //NetworkImageView iv = (NetworkImageView) convertView.findViewById(R.id.flag);

        Glide.with(context).load(child.getImageLink()).into(cartImage);
        proName.setText(child.getProductName());
        proPrice.setText(Float.toString(child.getProductPrice()));
        proQuantity.setText(String.valueOf(child.getQuantity()));
        //proSize.setText(String.valueOf(child.getQuantity()));


        //iv.setImageUrl(child.getImage(), imageLoader);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<CartModelItems> chList = cartModel.get(groupPosition).getCartItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return cartModel.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return cartModel.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //ProductHeader group = (ProductHeader) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.checkout_group, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.list_header);
        //ImageView img=(ImageView) convertView.findViewById(R.id.image_display);
        tv.setText("Products");
        //Glide.with(context).load(group.getPictureURL()).into(img);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
