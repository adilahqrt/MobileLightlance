package com.lightlance.app.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lightlance.app.R;
import com.lightlance.app.api.model.CategoriesItem;
import com.lightlance.app.api.model.PackagesItem;
import com.lightlance.app.wedding.WeddingSatuActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {
    private CategoriesItem categoriesItem;
    private ArrayList<PackagesItem> packagesItemList = new ArrayList<>();

    public void setCategoriesItem(CategoriesItem categoriesItem) {
        this.categoriesItem = categoriesItem;
    }

    public void setPackagesItemList(ArrayList<PackagesItem> packagesItemList) {
        this.packagesItemList.clear();
        this.packagesItemList.addAll(packagesItemList);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_item, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PackageViewHolder holder, int position) {
        holder.bind(packagesItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return packagesItemList.size();
    }

    public class PackageViewHolder extends RecyclerView.ViewHolder {
        public PackageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void bind(PackagesItem packagesItem) {
            TextView tvPackageDesc = itemView.findViewById(R.id.tvPackageDesc);
            tvPackageDesc.setText(packagesItem.getDeskripsi());

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), WeddingSatuActivity.class);
                intent.putExtra(WeddingSatuActivity.EXTRA_CATEGORY, categoriesItem);
                intent.putExtra(WeddingSatuActivity.EXTRA_PACKAGE, packagesItem);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
