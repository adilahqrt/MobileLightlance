package com.lightlance.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lightlance.app.api.ApiConfig;
import com.lightlance.app.api.ApiService;
import com.lightlance.app.api.model.BalanceTopUp;
import com.lightlance.app.api.model.User;
import com.lightlance.app.api.responses.TopUpResponse;
import com.lightlance.app.utils.PreferencesHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnTopUp;
    private EditText editTopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnTopUp = findViewById(R.id.btnTopUp);
        btnTopUp.setOnClickListener(this);

        editTopUp = findViewById(R.id.myBalance);
        editTopUp.setOnClickListener(this);
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
        if (v.getId() == R.id.btnTopUp) {
            String nominal = editTopUp.getText().toString().trim();
            int nNominal = Integer.parseInt(nominal);
            if (nNominal < 10000) {
                editTopUp.setError("Minimum Top Up IDR 10000");
                Toast.makeText(BalanceActivity.this, "Minimum Top Up IDR 10000", Toast.LENGTH_SHORT).show();
            } else {
                PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
                User user = (User) preferencesHelper.getItem(this, "LoggedUser", new User());
                ApiService service = ApiConfig.getApiService();
                BalanceTopUp balanceTopUp = new BalanceTopUp(Integer.parseInt(user.getId()), nNominal);
                service.topUp(balanceTopUp).enqueue(new Callback<TopUpResponse>() {
                    @Override
                    public void onResponse(Call<TopUpResponse> call, Response<TopUpResponse> response) {
                        Toast.makeText(BalanceActivity.this, "Top Up Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<TopUpResponse> call, Throwable t) {

                    }
                });
            }
        }
    }
}