package com.lightlance.app.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lightlance.app.BalanceActivity;
import com.lightlance.app.EditAccountActivity;
import com.lightlance.app.R;
import com.lightlance.app.api.model.User;
import com.lightlance.app.utils.PreferencesHelper;

import org.jetbrains.annotations.NotNull;

public class AccountFragment extends Fragment {
    private ImageView topUpBalance;
    private Button btnEdit;
    private Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        topUpBalance = view.findViewById(R.id.iconTambah);
        topUpBalance.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BalanceActivity.class);
            startActivity(intent);
        });

        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditAccountActivity.class);
            startActivity(intent);
        });

        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            System.exit(0);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
        User user = (User) preferencesHelper.getItem(getActivity(), "LoggedUser", new User());
        Log.d("LoggedUser", "Nama User : " + user.getFullName());

        TextView tvFullname = view.findViewById(R.id.tvFullname);
        tvFullname.setText(user.getFullName());

        TextView tvBalance = view.findViewById(R.id.tvBalance);
        tvBalance.setText(user.getBalance());

        TextView tvEmail = view.findViewById(R.id.tvEmail);
        tvEmail.setText(user.getEmail());

        TextView tvGender = view.findViewById(R.id.tvGender);
        tvGender.setText(user.getGender());

        TextView tvPhone = view.findViewById(R.id.tvPhone);
        tvPhone.setText(user.getPhone());

        TextView tvAddress = view.findViewById(R.id.tvAddress);
        tvAddress.setText(user.getAddress());

    }


}