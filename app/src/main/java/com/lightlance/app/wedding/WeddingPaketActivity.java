package com.lightlance.app.wedding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lightlance.app.R;
import com.lightlance.app.adapter.PackageAdapter;
import com.lightlance.app.api.ApiConfig;
import com.lightlance.app.api.ApiService;
import com.lightlance.app.api.model.CategoriesItem;
import com.lightlance.app.api.model.PackagesItem;
import com.lightlance.app.api.responses.PackagesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeddingPaketActivity extends AppCompatActivity {
    public static String EXTRA_CATEGORY = "extra_category";

    private CategoriesItem categoriesItem;

    private ImageView imgCategory;
    private TextView tvCategoryTitle;
    private RecyclerView rvPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_paket);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        imgCategory = findViewById(R.id.imgCategory);
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);

        rvPackage = findViewById(R.id.rvPackage);
        rvPackage.setLayoutManager(new LinearLayoutManager(this));

        categoriesItem = getIntent().getParcelableExtra(EXTRA_CATEGORY);

        if (categoriesItem != null) {
            Glide.with(this)
                    .load("https://ws-tif.com/lightlance/img/" + categoriesItem.getImageKategori())
                    .centerCrop()
                    .into(imgCategory);

            tvCategoryTitle.setText(categoriesItem.getNamaKategori().toUpperCase());

            fetchPackages(Integer.parseInt(categoriesItem.getIdKategori()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchPackages(int categoryId) {
        ApiService service = ApiConfig.getApiService();
        service.getPackages(categoryId).enqueue(new Callback<PackagesResponse>() {
            @Override
            public void onResponse(Call<PackagesResponse> call, Response<PackagesResponse> response) {
                if (response.body() != null) {
                    PackageAdapter packageAdapter = new PackageAdapter();
                    packageAdapter.setCategoriesItem(categoriesItem);
                    packageAdapter.setPackagesItemList((ArrayList<PackagesItem>) response.body().getPackages());

                    rvPackage.setAdapter(packageAdapter);
                }
            }

            @Override
            public void onFailure(Call<PackagesResponse> call, Throwable t) {
                Log.e("ERROR", "onFailure: " + t.getMessage(), t);
            }
        });
    }
}