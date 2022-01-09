package com.lightlance.app.wedding;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.lightlance.app.R;
import com.lightlance.app.api.ApiConfig;
import com.lightlance.app.api.ApiService;
import com.lightlance.app.api.model.CategoriesItem;
import com.lightlance.app.api.model.Order;
import com.lightlance.app.api.model.PackagesItem;
import com.lightlance.app.api.model.User;
import com.lightlance.app.api.responses.OrderResponse;
import com.lightlance.app.datepicker.DatePicker;
import com.lightlance.app.fragment.BookingSuccessFragment;
import com.lightlance.app.utils.PreferencesHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingWedding1Activity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, BookingSuccessFragment.ClickListener {
    public static String EXTRA_CATEGORY = "extra_category";
    public static String EXTRA_PACKAGE = "extra_package";

    private TextView tvCategoryTitle;
    private TextView tvPackageDesc;
    private TextInputEditText edtDate;
    private TextInputEditText edtAddress;
    private CheckBox cbSetuju;
    private Button btnContinue;

    private CategoriesItem categoriesItem;
    private PackagesItem packagesItem;

    private BookingSuccessFragment bookingSuccessFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_wedding1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvPackageDesc = findViewById(R.id.tvPackageDesc);

        categoriesItem = getIntent().getParcelableExtra(EXTRA_CATEGORY);
        packagesItem = getIntent().getParcelableExtra(EXTRA_PACKAGE);

        if (categoriesItem != null && packagesItem != null) {
            tvCategoryTitle.setText(categoriesItem.getNamaKategori());
            tvPackageDesc.setText(packagesItem.getDeskripsi());
        }
        edtDate = findViewById(R.id.edt_date);
        edtDate.setOnClickListener(this);

        edtAddress = findViewById(R.id.edt_address);
        edtAddress.setOnClickListener(this);

        cbSetuju = findViewById(R.id.cbSetuju);

        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
    }

    /**
     * menampilkan btn back
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * menampilkan date picker smp line 84
     */
    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        final Calendar choosenDate = Calendar.getInstance();
        choosenDate.set(year, month, dayOfMonth);

        @SuppressLint("SimpleDateFormat") final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        final Date date = choosenDate.getTime();
        final String strDate = simpleDateFormat.format(date);

        edtDate.setText(strDate);
    }

    @Override
    public void onClick(View v) {
        /** click date picker */
        if (v.getId() == R.id.edt_date) {
            DatePicker datePicker = new DatePicker();

            datePicker.show(getSupportFragmentManager(), datePicker.getClass().getSimpleName());

        } else if (v.getId() == R.id.btnContinue) {
            String date = Objects.requireNonNull(edtDate.getText()).toString().trim();
            String address = Objects.requireNonNull(edtAddress.getText()).toString().trim();

            if (!validate(date, address)) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date1 = format.parse(date);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    String tanggalNormal = format1.format(date1);

                    PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
                    User user = (User) preferencesHelper.getItem(this, "LoggedUser", new User());

                    ApiService service = ApiConfig.getApiService();
                    Order order = new Order(tanggalNormal, address, Integer.parseInt(user.getId()), Integer.parseInt(packagesItem.getIdPaket()));
                    service.sendOrder(order).enqueue(new Callback<OrderResponse>() {
                        @Override
                        public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                            if (response.body() != null) {
                                OrderResponse orderResponse = response.body();

                                if (orderResponse.isSuccess()) {
                                    Intent intent = new Intent(BookingWedding1Activity.this, PaymentActivity.class);
                                    intent.putExtra(PaymentActivity.EXTRA_ORDER_ID, orderResponse.getIdTransaction());
                                    intent.putExtra(PaymentActivity.EXTRA_CATEGORY, categoriesItem);
                                    intent.putExtra(PaymentActivity.EXTRA_PACKAGE, packagesItem);
                                    startActivity(intent);
                                } else {
                                    bookingSuccessFragment = new BookingSuccessFragment();
                                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                    bookingSuccessFragment.title = "Transaksi gagal!";
                                    bookingSuccessFragment.message = orderResponse.getMessage();
                                    bookingSuccessFragment.show(fragmentTransaction, "success dialog");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderResponse> call, Throwable t) {
                            Log.e("ORDER_ERROR", "onFailure: " + t.getMessage(), t);
                        }
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean validate(String date, String address) {
        boolean hasError = false;

        if (date.isEmpty()) {
            edtDate.setError("Date can't be empty!");
            hasError = true;
        }

        if (address.isEmpty()) {
            edtAddress.setError("Address can't be empty!");

            if (!hasError) {
                hasError = true;
            }
        }

        if (!cbSetuju.isChecked()) {
            cbSetuju.setError("Checkbox must be checked!");

            if (!hasError) {
                hasError = true;
            }
        }
        return hasError;
    }

    @Override
    public void okClickListener() {
        if (bookingSuccessFragment != null) {
            bookingSuccessFragment.dismiss();
        }
    }
}