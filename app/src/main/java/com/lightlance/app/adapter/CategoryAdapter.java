package com.lightlance.app.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lightlance.app.R;
import com.lightlance.app.api.model.CategoriesItem;
import com.lightlance.app.wedding.WeddingPaketActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ArrayList<CategoriesItem> categoriesItemList = new ArrayList<>();

    public void setCategoriesItemList(ArrayList<CategoriesItem> categoriesItemList) {
        this.categoriesItemList.clear();
        this.categoriesItemList.addAll(categoriesItemList);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryViewHolder holder, int position) {
        holder.bind(categoriesItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoriesItemList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public CategoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void bind(CategoriesItem categoriesItem) {
            ImageView imgCategory = itemView.findViewById(R.id.imgCategory);

            Glide.with(itemView.getContext())
                    .load("https://ws-tif.com/lightlance/img/" + categoriesItem.getImageKategori())
                    .centerCrop()
                    .into(imgCategory);

            TextView tvTitle = itemView.findViewById(R.id.tvCategoryTitle);
            tvTitle.setText(categoriesItem.getNamaKategori());

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), WeddingPaketActivity.class);
                intent.putExtra(WeddingPaketActivity.EXTRA_CATEGORY, categoriesItem);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
