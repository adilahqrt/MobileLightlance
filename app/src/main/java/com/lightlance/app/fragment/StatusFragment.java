package com.lightlance.app.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lightlance.app.R;
import com.lightlance.app.adapter.OrderAdapter;
import com.lightlance.app.api.ApiConfig;
import com.lightlance.app.api.ApiService;
import com.lightlance.app.api.model.User;
import com.lightlance.app.api.responses.OrderListResponse;
import com.lightlance.app.utils.PreferencesHelper;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatusFragment extends Fragment {

    private RecyclerView rvOrders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvOrders = view.findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchOrderList();
    }

    private void fetchOrderList() {
        PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
        User user = (User) preferencesHelper.getItem(getContext(), "LoggedUser", new User());
        ApiService service = ApiConfig.getApiService();
        service.getOrders(Integer.parseInt(user.getId())).enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                if (response.body() != null && response.body().isSuccess()) {
                    OrderAdapter orderAdapter = new OrderAdapter();
                    orderAdapter.setOrdersItemList(response.body().getOrders());

                    rvOrders.setAdapter(orderAdapter);
                }
            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {

            }
        });
    }
}