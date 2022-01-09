package com.lightlance.app.wedding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lightlance.app.R;
import com.lightlance.app.api.model.CategoriesItem;
import com.lightlance.app.api.model.PackagesItem;
import com.lightlance.app.utils.Helper;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA_ORDER_ID = "extra_order_id";
    public static String EXTRA_CATEGORY = "extra_category";
    public static String EXTRA_PACKAGE = "extra_package";

    private CategoriesItem categoriesItem;
    private PackagesItem packagesItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        categoriesItem = getIntent().getParcelableExtra(EXTRA_CATEGORY);
        packagesItem = getIntent().getParcelableExtra(EXTRA_PACKAGE);

        if (categoriesItem != null && packagesItem != null) {
            ImageView imgCategory = findViewById(R.id.imgCategory);
            TextView tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
            TextView tvPackageDesc = findViewById(R.id.tvPackageDesc);
            TextView tvPackagePrice = findViewById(R.id.tvPackagePrice);

            Glide.with(this)
                    .load("https://ws-tif.com/lightlance/lightlance/img/" + categoriesItem.getImageKategori())
                    .centerCrop()
                    .into(imgCategory);

            tvCategoryTitle.setText(categoriesItem.getNamaKategori());
            tvPackageDesc.setText(packagesItem.getDeskripsi());

            String price = "IDR " + Helper.addThousandSeparator(Integer.parseInt(packagesItem.getHargaPaket()));
            tvPackagePrice.setText(price);
        }

        Button btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPayment) {
            Intent intent = new Intent(this, ConfirmPayActivity.class);
            intent.putExtra(ConfirmPayActivity.EXTRA_ORDER_ID, getIntent().getIntExtra(ConfirmPayActivity.EXTRA_ORDER_ID, -1));
            intent.putExtra(ConfirmPayActivity.EXTRA_CATEGORY, categoriesItem);
            intent.putExtra(ConfirmPayActivity.EXTRA_PACKAGE, packagesItem);
            startActivity(intent);
        }
    }
}