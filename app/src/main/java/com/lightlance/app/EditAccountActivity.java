package com.lightlance.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.lightlance.app.api.model.User;
import com.lightlance.app.utils.PreferencesHelper;

public class EditAccountActivity extends AppCompatActivity {
    private TextInputEditText editEmail;
    private TextInputEditText editFullname;
    private Spinner editSpinnerGender;
    private TextInputEditText editAddress;
    private TextInputEditText editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
        User user = (User) preferencesHelper.getItem(this, "LoggedUser", new User());

        editEmail = findViewById(R.id.editEmail);
        editEmail.setText(user.getEmail());

        editFullname = findViewById(R.id.editFullname);
        editFullname.setText(user.getFullName());

        editSpinnerGender = findViewById(R.id.editSpinnerGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        editSpinnerGender.setAdapter(adapter);

        for (int i = 0; i < adapter.getCount(); i++) {
            Log.d("LOG GENDER", "gender item = " + adapter.getItem(i));
        }

        editAddress = findViewById(R.id.editAddress);
        editAddress.setText(user.getAddress());

        editPhone = findViewById(R.id.editPhone);
        editPhone.setText(user.getPhone());

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