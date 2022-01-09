package com.lightlance.app.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lightlance.app.R;
import com.lightlance.app.api.responses.OrdersItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private final ArrayList<OrdersItem> ordersItemList = new ArrayList<>();

    public void setOrdersItemList(List<OrdersItem> ordersItemList) {
        this.ordersItemList.clear();
        this.ordersItemList.addAll(ordersItemList);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderViewHolder holder, int position) {
        holder.bind(ordersItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return ordersItemList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public OrderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void bind(OrdersItem ordersItem) {
            TextView tvPackageName = itemView.findViewById(R.id.tvPackageName);
            TextView tvDate = itemView.findViewById(R.id.tvDate);
            TextView tvStatus = itemView.findViewById(R.id.tvStatus);

            tvPackageName.setText(ordersItem.getPackagesItem().getNamaPaket());
            tvDate.setText(ordersItem.getTglPemesanan());
            tvStatus.setText(ordersItem.getStatusPemesanan());

            switch (ordersItem.getStatusPemesanan()) {
                case "Pending":
                    tvStatus.setTextColor(Color.YELLOW);
                    break;

                case "Success":
                    tvStatus.setTextColor(Color.GREEN);
                    break;

                case "Cancel":
                    tvStatus.setTextColor(Color.RED);
                    break;

                default:
                    break;
            }
        }
    }
}
