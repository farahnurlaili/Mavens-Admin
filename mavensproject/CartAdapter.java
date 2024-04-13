package com.example.mavensproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    private Context context;
    private List<CartModel> productModelList;

    public CartAdapter(Context context) {
        this.context = context;
        productModelList = new ArrayList<>();
    }
    public void addProduct(CartModel productModel) {
        productModelList.add(productModel);
        notifyDataSetChanged();
    }
    public List<CartModel> getSelectedItems() {
        List<CartModel> cartItems=new ArrayList<>();
        for (int i=0;i<productModelList.size();i++) {
            if (productModelList.get(i).isSelected) {
                cartItems.add(productModelList.get(i));
            }
        }
        return cartItems;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartModel productModel=productModelList.get(position);
        holder.title.setText(productModel.getProductName());
        holder.price.setText(productModel.getProductPrice());
        holder.qty.setText(productModel.getProductQty());

        Glide.with(context).load(productModel.getProductImage())
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productModel.setIsSelected(!productModel.isSelected);
                    if (productModel.isSelected) {
                        holder.itemView.setBackgroundColor(Color.parseColor("#B6B5B5"));
                    }
                    else {
                        holder.itemView.setBackgroundColor(Color.WHITE);
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title, qty, price;
        private ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            qty=itemView.findViewById(R.id.qty);
            price=itemView.findViewById(R.id.price);
            img=itemView.findViewById(R.id.image);

        }
    }
}
