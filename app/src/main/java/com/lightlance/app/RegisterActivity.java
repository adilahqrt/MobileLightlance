package com.lightlance.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lightlance.app.api.ApiConfig;
import com.lightlance.app.api.ApiService;
import com.lightlance.app.api.model.RegisterUser;
import com.lightlance.app.api.responses.RegisterResponse;
import com.lightlance.app.utils.Validator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    //deklarasi property
    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;
    private TextInputEditText edtConfirmPass;
    private TextInputEditText edtFullname;
    private Spinner spinnerGender;
    private TextInputEditText edtAddress;
    private TextInputEditText edtPhone;
    private Button btnRegister;
    private Button btnHaveAccount;
    private RelativeLayout progressLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //inisialisasi property berdasarkan ID
        edtEmail = findViewById(R.id.edt_emailReg);
        edtPassword = findViewById(R.id.edt_passReg);
        edtConfirmPass = findViewById(R.id.edt_confirmPassReg);
        edtFullname = findViewById(R.id.edt_fullname);
        edtAddress = findViewById(R.id.edt_address);
        edtPhone = findViewById(R.id.edt_phone);
        progressLayer = findViewById(R.id.progress_layer);
        progressLayer.setVisibility(View.INVISIBLE);

        //inisialisasi dan mengatur callback ketika tombol di klik
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        //inisialisasi dan mengatur callback ketika tombol di klik
        btnHaveAccount = findViewById(R.id.btnHaveAccount);
        btnHaveAccount.setOnClickListener(this);

        /**
         * inisialisasi property berdasarkan ID
         * membuat dropdown untuk opsi gender
         */
        spinnerGender = findViewById(R.id.spinner_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
    }


    //method onClick
    @Override
    public void onClick(View v) {
        if (progressLayer.getVisibility() == View.VISIBLE) return;

        /**
         * memeriksa btn mana yg sedang di klik berdasarkan ID nya
         * kondisi ketika btn di klik lalu berpindah activity */
        if (v.getId() == R.id.btnHaveAccount) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnRegister) {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String confirmPass = edtConfirmPass.getText().toString().trim();
            String fullname = edtFullname.getText().toString().trim();
            String gender = spinnerGender.getSelectedItem().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();

            //memanggil method validate dengan parameter value dalam register activity
            if (!validateError(email, password, confirmPass, fullname, address, phone)) {
                progressLayer.setVisibility(View.VISIBLE);
                RegisterUser registerUser = new RegisterUser(email, password, fullname, gender, address, phone, 0);

                ApiService apiService = ApiConfig.getApiService();
                apiService.register(registerUser).enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.body() != null) {
                            progressLayer.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterActivity.this, "Status : " + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                    }
                });

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    //method validate untuk memvalidasi value pada register
    private boolean validateError(String email, String password, String confirmPass, String fullname, String address, String phone) {
        boolean hasError = false;

        /**
         * jika email kosong, maka trdapat error This field can't be empty!
         * jika value hasError sblm nya adalah FALSE maka akan di set mjd TRUE karena terjadi error pada validasi
         * jika tdk empty namun email tdk valid, maka terdapat error email is invalid
         */
        if (email.isEmpty()) {
            edtEmail.setError("This field can't be empty!");
            if (!hasError) {
                hasError = true;
            }
        } else if (!Validator.validateEmail(email)) {
            edtEmail.setError("Email is invalid!");
            if (!hasError) {
                hasError = true;
            }
        }

        /**
         * jika password kosong, maka trdapat error This field can't be empty!
         * jika value hasError sblm nya adalah FALSE maka akan di set mjd TRUE karena terjadi error pada validasi
         * jika tdk empty namun  jml karakter < 6, maka terdapat error password at least 6 karakter
         */
        if (password.isEmpty()) {
            edtPassword.setError("This field can't be empty!");
            if (!hasError) {
                hasError = true;
            }
        } else if (!Validator.validateCharLength(password, 6)) {
            edtPassword.setError("Password at least 6 characters!");
            if (!hasError) {
                hasError = true;
            }
        }

        /**
         * jika confirmPass kosong, maka trdapat error This field can't be empty!
         * jika value hasError sblm nya adalah FALSE maka akan di set mjd TRUE karena terjadi error pada validasi
         * jika tdk empty namun confirmPass tdk sama dgn password, maka terdapat error confirmPass is not same
         */
        if (confirmPass.isEmpty()) {
            edtConfirmPass.setError("This field can't be empty!");
            if (!hasError) {
                hasError = true;
            }
        } else if (!Validator.validateCharLength(confirmPass, 6)) {
            if (!hasError) {
                hasError = true;
            }
            edtConfirmPass.setError("Confirm Password at least 6 characters!");
        } else if (edtPassword.getError() == null && !password.equals(confirmPass)) {
            edtConfirmPass.setError("Confirm Password is not same!");
            if (!hasError) {
                hasError = true;
            }
        }

        /**
         * jika fullname kosong, maka trdapat error This field can't be empty!
         * jika value hasError sblm nya adalah FALSE maka akan di set mjd TRUE karena terjadi error pada validasi
         */
        if (fullname.isEmpty()) {
            edtFullname.setError("This field can't be empty!");
            if (!hasError) {
                hasError = true;
            }
        }

        /**
         * jika address kosong, maka trdapat error This field can't be empty!
         * jika value hasError sblm nya adalah FALSE maka akan di set mjd TRUE karena terjadi error pada validasi
         */
        if (address.isEmpty()) {
            edtAddress.setError("This field can't be empty!");
            if (!hasError) {
                hasError = true;
            }
        }

        /**
         * jika phone kosong, maka trdapat error This field can't be empty!
         * jika value hasError sblm nya adalah FALSE maka akan di set mjd TRUE karena terjadi error pada validasi
         */
        if (phone.isEmpty()) {
            edtPhone.setError("This field can't be empty!");
            if (!hasError) {
                hasError = true;
            }
        }
        //mengembalikan nilai hasError
        return hasError;
    }
}