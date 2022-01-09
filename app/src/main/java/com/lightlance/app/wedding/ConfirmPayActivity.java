package com.lightlance.app.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.lightlance.app.MainMenuActivity;
import com.lightlance.app.R;
import com.lightlance.app.api.ApiConfig;
import com.lightlance.app.api.ApiService;
import com.lightlance.app.api.model.CategoriesItem;
import com.lightlance.app.api.model.PackagesItem;
import com.lightlance.app.api.model.PaymentModel;
import com.lightlance.app.api.model.User;
import com.lightlance.app.api.responses.PaymentResponse;
import com.lightlance.app.fragment.BookingSuccessFragment;
import com.lightlance.app.utils.Helper;
import com.lightlance.app.utils.PreferencesHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPayActivity extends AppCompatActivity implements BookingSuccessFragment.ClickListener {
    public static String EXTRA_ORDER_ID = "extra_order_id";
    public static String EXTRA_CATEGORY = "extra_category";
    public static String EXTRA_PACKAGE = "extra_package";

    private BookingSuccessFragment bookingSuccessFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pay);

        int orderId = getIntent().getIntExtra(EXTRA_ORDER_ID, -1);
        CategoriesItem categoriesItem = getIntent().getParcelableExtra(EXTRA_CATEGORY);
        PackagesItem packagesItem = getIntent().getParcelableExtra(EXTRA_PACKAGE);

        if (orderId != -1 && categoriesItem != null && packagesItem != null) {
            TextView tvPackageName = findViewById(R.id.tvPackageName);
            TextView tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
            TextView tvPackageDesc = findViewById(R.id.tvPackageDesc);
            TextView tvBalance = findViewById(R.id.tvBalance);
            TextView tvPackagePrice = findViewById(R.id.tvPackagePrice);

            tvPackageName.setText(packagesItem.getNamaPaket());
            tvCategoryTitle.setText(categoriesItem.getNamaKategori());
            tvPackageDesc.setText(packagesItem.getDeskripsi());

            PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
            User user = (User) preferencesHelper.getItem(this, "LoggedUser", new User());

            String balance = "Rp. " + Helper.addThousandSeparator(Integer.parseInt(user.getBalance()));
            String price = "Rp. " + Helper.addThousandSeparator(Integer.parseInt(packagesItem.getHargaPaket()));

            tvBalance.setText(balance);
            tvPackagePrice.setText(price);

            Button btnPayment = findViewById(R.id.btnPayment);
            btnPayment.setOnClickListener(v -> {
                PaymentModel paymentModel = new PaymentModel(orderId, Integer.parseInt(user.getId()));
                ApiService service = ApiConfig.getApiService();
                service.payOrder(paymentModel).enqueue(new Callback<PaymentResponse>() {
                    @Override
                    public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            bookingSuccessFragment = new BookingSuccessFragment();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            bookingSuccessFragment.title = "Pembayaran Berhasil!";
                            bookingSuccessFragment.message = "Pesanan anda telah diproses oleh admin.";
                            bookingSuccessFragment.show(fragmentTransaction, "success dialog");
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentResponse> call, Throwable t) {

                    }
                });
            });
        }
    }

    @Override
    public void okClickListener() {
        if (bookingSuccessFragment != null) {
            Intent intent = new Intent(ConfirmPayActivity.this, MainMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}