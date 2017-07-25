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
import com.cresset.asimjofaofficial.models.CategoryList;
import com.cresset.asimjofaofficial.models.ChildCategoryList;
import com.cresset.asimjofaofficial.models.IndexImage;

import java.util.ArrayList;


public class IndexAdapter extends BaseExpandableListAdapter{

    private Context context;
    private ArrayList<IndexImage> indexImages;

    //ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public IndexAdapter(Context context, ArrayList<IndexImage> groups) {
        this.context = context;
        this.indexImages = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //ArrayList<ChildCategoryList> chList = indexImages.get(groupPosition).getChild();
        ArrayList<ChildCategoryList> chList = indexImages.get(groupPosition).getChild();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ChildCategoryList child = (ChildCategoryList) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_menu_list, null);
        }

//		if (imageLoader == null)
//			imageLoader = MyApplication.getInstance().getImageLoader();

        TextView tv = (TextView) convertView.findViewById(R.id.category_child_name);
        //NetworkImageView iv = (NetworkImageView) convertView.findViewById(R.id.flag);

        tv.setText(child.getName().toString());
        //iv.setImageUrl(child.getImage(), imageLoader);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ChildCategoryList> chList = indexImages.get(groupPosition).getChild();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return indexImages.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return indexImages.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        IndexImage group = (IndexImage) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.main_menu_listt, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.category_name);
        ImageView img=(ImageView) convertView.findViewById(R.id.image_display);
        tv.setText(group.getCategoryName());
        Glide.with(context).load(group.getPictureURL()).into(img);


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