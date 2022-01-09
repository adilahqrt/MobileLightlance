package com.lightlance.app.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lightlance.app.R;
import com.lightlance.app.adapter.CategoryAdapter;
import com.lightlance.app.api.ApiConfig;
import com.lightlance.app.api.ApiService;
import com.lightlance.app.api.model.CategoriesItem;
import com.lightlance.app.api.responses.CategoriesResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private RecyclerView rvCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        rvCategory = view.findViewById(R.id.rvCategory);
        rvCategory.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchCategory();
    }

    private void fetchCategory() {
        ApiService service = ApiConfig.getApiService();
        service.getCategories().enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.body() != null) {
                    CategoryAdapter adapter = new CategoryAdapter();
                    adapter.setCategoriesItemList((ArrayList<CategoriesItem>) response.body().getCategories());

                    rvCategory.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                Log.e("RESPONSE_ERROR", "onFailure: " + t.getMessage(), t);
            }
        });
    }
}