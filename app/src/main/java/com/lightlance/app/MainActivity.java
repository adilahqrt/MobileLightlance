package com.lightlance.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lightlance.app.api.ApiConfig;
import com.lightlance.app.api.ApiService;
import com.lightlance.app.api.model.LoginUser;
import com.lightlance.app.api.responses.LoginResponse;
import com.lightlance.app.utils.PreferencesHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
//    ProgressBar pb;
//    int count = 0;

    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencesHelper = PreferencesHelper.getInstance();
        String email = preferencesHelper.getItem(this, "email", "").toString();
        String password = preferencesHelper.getItem(this, "password", "").toString();

        if (email.isEmpty() || password.isEmpty()) {
            Handler handler = new Handler();
            Runnable runnable = () -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            };
            handler.postDelayed(runnable, 3000);
        } else {
            login(email, password);
        }
//
//        prog();
    }

//    public void prog() {
//        pb = (ProgressBar) findViewById(R.id.progressBar);
//        final Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                count++;
//                pb.setProgress(count);
//
//                if (count == 100) {
//                    timer.cancel();
//                }
//            }
//        };
//        timer.schedule(timerTask, 0, 30);
//    }

    private void login(String email, String password) {
        LoginUser loginUser = new LoginUser(email, password);

        ApiService service = ApiConfig.getApiService();
        service.login(loginUser).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    LoginResponse body = response.body();
                    if (!body.isSuccess()) {
                        Toast.makeText(MainActivity.this, "Login Failed: " + body.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    preferencesHelper.saveItem(MainActivity.this, "LoggedUser", body.getUser());

                    Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                    startActivity(intent);

                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LOGIN_RESULT", "onFailure: ", t);
            }
        });
    }
}