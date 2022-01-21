package com.lightlance.app.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.lightlance.app.R;
import com.lightlance.app.api.model.CategoriesItem;
import com.lightlance.app.api.model.PackagesItem;

public class WeddingSatuActivity extends AppCompatActivity {
    public static String EXTRA_CATEGORY = "extra_category";
    public static String EXTRA_PACKAGE = "extra_package";

    private ImageView imgCategory;
    private TextView tvCategoryTitle;
    private TextView tvPackageDesc;
    private Button btnBookingNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_satu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        imgCategory = findViewById(R.id.imgCategory);
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvPackageDesc = findViewById(R.id.tvPackageDesc);

        CategoriesItem categoriesItem = getIntent().getParcelableExtra(EXTRA_CATEGORY);
        PackagesItem packagesItem = getIntent().getParcelableExtra(EXTRA_PACKAGE);

        if (categoriesItem != null && packagesItem != null) {
            Glide.with(this)
                    .load("https://ws-tif.com/lightlance/img/" + categoriesItem.getImageKategori())
                    .centerCrop()
                    .into(imgCategory);

            tvCategoryTitle.setText(categoriesItem.getNamaKategori());
            tvPackageDesc.setText(packagesItem.getDeskripsi());
        }

        btnBookingNow = findViewById(R.id.btnBookingNow);
        btnBookingNow.setOnClickListener(v -> {
            Intent intent = new Intent(WeddingSatuActivity.this, BookingWedding1Activity.class);
            intent.putExtra(BookingWedding1Activity.EXTRA_CATEGORY, categoriesItem);
            intent.putExtra(BookingWedding1Activity.EXTRA_PACKAGE, packagesItem);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}