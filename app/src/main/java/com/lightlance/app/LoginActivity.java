package com.lightlance.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lightlance.app.api.ApiConfig;
import com.lightlance.app.api.ApiService;
import com.lightlance.app.api.model.LoginUser;
import com.lightlance.app.api.responses.LoginResponse;
import com.lightlance.app.utils.PreferencesHelper;
import com.lightlance.app.utils.Validator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //deklarasi property
    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;
    private Button btnLogin;
    private Button btnSignUp;
    private RelativeLayout progressLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inisialisasi property berdasarkan ID
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);

        //inisialisasi dan mengatur callback ketika tombol di klik
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        //inisialisasi dan mengatur callback ketika tombol di klik
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);

        //inisialisasi dan mengatur callback ketika tombol di klik
        progressLayer = findViewById(R.id.progress_layer);
        progressLayer.setVisibility(View.INVISIBLE);

    }

    //method onClick
    @Override
    public void onClick(View v) {
        //cek apakah progress loading tampil dilayar, jika iya maka panggil return shg statemen dibawahnya tidak akan dieksekusi
        if (progressLayer.getVisibility() == View.VISIBLE) return;

        /**
         * memeriksa btn mana yg sedang di klik berdasarkan ID nya
         * memastikan edtEmail & edtPassword dalam kondisi tidak null */
        if (v.getId() == R.id.btn_login) {
            assert edtEmail.getText() != null && edtPassword.getText() != null;
            //mendapatkan value dari edtEmail & edtPassword ke var email dan merubah nilainya menjadi string
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            /*
             * memvalidasi value yg terdapat pada login yaitu email dan password
             * jika hasil validasi mengembalikan nilai true, berarti tidak ada error pd email&pass. kemudian statement proses login akan dieksekusi * */
            if (validate(email, password)) {
                progressLayer.setVisibility(View.VISIBLE);

                //membuat objek dari Class LoginUser dengan value email & password
                LoginUser loginUser = new LoginUser(email, password);

                /**
                 * utk memnanggil method getApiService pada class ApiService
                 * melakukan proses request api Login ke server
                 */
                ApiService service = ApiConfig.getApiService();
                service.login(loginUser).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.body() != null) {
                            progressLayer.setVisibility(View.INVISIBLE);

                            LoginResponse body = response.body();
                            if (!body.isSuccess()) {
                                Toast.makeText(LoginActivity.this, "Login Failed: " + body.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
                            preferencesHelper.saveItem(LoginActivity.this, "LoggedUser", body.getUser());
                            preferencesHelper.saveItem(LoginActivity.this, "email", email);
                            preferencesHelper.saveItem(LoginActivity.this, "password", password);

                            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
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
        } else if (v.getId() == R.id.btnSignUp) { //memeriksa btn mana yg sedang di klik berdasarkan ID nya
            Intent intent = new Intent(this, RegisterActivity.class); //kondisi ketika btn di klik lalu berpindah activity
            startActivity(intent);
        }
    }

    //method validate untu memvalidasi email & pass
    private boolean validate(String email, String password) {
        /*
         * jika email kosong, maka trdapat error This field can't be empty!
         * jika tdk empty namun nilainya tdk valid, maka terdapat error Email is invalid */
        if (email.isEmpty()) {
            edtEmail.setError("This field can't be empty!");
        } else if (!Validator.validateEmail(email)) {
            edtEmail.setError("Email is invalid!");
        }

        /** jika password kosong, maka trdapat error This field can't be empty!
         * jika tdk empty namun jml karakter < 6, maka terdapat error password at least 6 karakter*/
        if (password.isEmpty()) {
            edtPassword.setError("This field can't be empty!");
        } else if (!Validator.validateCharLength(password, 6)) {
            edtPassword.setError("Password at least 6 character!");
        }
        //mengembalikan nilai dari kondisi apakah ada error pada edtEmail&edtPass
        return edtEmail.getError() == null && edtPassword.getError() == null;
    }
}