package com.dev.eivs.testscan.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.eivs.testscan.ProductModel;
import com.dev.eivs.testscan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductModel> list;

    public ProductAdapter(List<ProductModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_item, viewGroup, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int i) {

        ProductModel product = list.get(i);

        productViewHolder.textView.setText(product.getDescription());

        Picasso.get().load(product.getUrl()).into(productViewHolder.imageView);

        {
        }
        productViewHolder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(productViewHolder.getAdapterPosition(), 0, 0, "Удалить");
                contextMenu.add(productViewHolder.getAdapterPosition(), 1, 0, "Изменить текст");
                contextMenu.add(productViewHolder.getAdapterPosition(), 2, 0, "Изменить изображение");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
