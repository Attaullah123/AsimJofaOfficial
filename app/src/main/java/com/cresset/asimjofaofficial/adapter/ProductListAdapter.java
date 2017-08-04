package com.cresset.asimjofaofficial.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.cresset.asimjofaofficial.R;
import com.cresset.asimjofaofficial.models.ProductListModel;
import com.cresset.asimjofaofficial.models.ProductModel;
import com.cresset.asimjofaofficial.productdetail.ProductDetail;
import com.cresset.asimjofaofficial.utilities.CustomVolleyRequest;
import com.cresset.asimjofaofficial.utilities.ResizableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<ProductListModel> productModelList;
    private ArrayList<ProductListModel> mFilterList;
//    public String productName;
//    private ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title, price,proId,outOfStock;
        public ImageView thumbnail;
        public CardView cardView;
        private Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.product_title);
            outOfStock = (TextView) itemView.findViewById(R.id.txt_outOfStock);


            price = (TextView) itemView.findViewById(R.id.product_list_price);
            thumbnail = (ImageView) itemView.findViewById(R.id.product_thumbnail);
            proId = (TextView) itemView.findViewById(R.id.product_list_id);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            //overflow = (ImageView) view.findViewById(R.id.overflow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Context context = v.getContext();
//                    Bitmap bitmap = thumbnail.getDrawingCache();
                    Intent intent = new Intent(v.getContext(), ProductDetail.class);
//                    //intent.putExtra("product_image", bitmap,  productModelList.get(position).getThumbnail());
                    System.out.println(productModelList.get(position).getId());
                    intent.putExtra("ProductId", productModelList.get(position).getId());
//                   intent.putExtra("product_name", productModelList.get(position).getName());
                    context.startActivity(intent);

                }
            });
        }

    }
    public ProductListAdapter(Context mContext, ArrayList<ProductListModel> productList) {
        this.mContext = mContext;
        this.productModelList = productList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cart_design, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ProductListModel productListModel = productModelList.get(position);

        holder.proId.setText(productListModel.getId());
        holder.title.setText(productListModel.getName());
        holder.price.setText(productListModel.getPrice());

        if(productListModel.isOutOfStock()){
            holder.outOfStock.setBackgroundColor(Color.BLACK);
            holder.outOfStock.setText("SOLD OUT");
        }

//        imageLoader = CustomVolleyRequest.getInstance(mContext).getImageLoader();
//        imageLoader.get(productModelList.get(), ImageLoader.getImageListener(holder.thumbnail, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
//
//        holder.imageView.setImageUrl(superHero.getImageUrl(), imageLoader);

        // loading album cover using Glide library
        Glide.with(mContext).load(productListModel.getImageLink()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public void clearCart() {
        productModelList.clear();
        productModelList.clear();
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilterList = productModelList;
                } else {

                    ArrayList<ProductListModel> filteredList = new ArrayList<>();

                    for (ProductListModel mFilteredLists : productModelList) {

                        if (mFilteredLists.getName().toLowerCase(Locale.getDefault()).contains(charSequence)) {

                            filteredList.add(mFilteredLists);
                        }
                    }

                    mFilterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (ArrayList<ProductListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
