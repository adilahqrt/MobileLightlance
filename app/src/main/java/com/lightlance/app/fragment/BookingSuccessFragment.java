package com.lightlance.app.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lightlance.app.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BookingSuccessFragment extends DialogFragment {
    public String title;
    public String message;

    private ClickListener clickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_booking_success, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        TextView tvMessage = view.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Button btnOK = view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener = (ClickListener) getActivity();
                assert clickListener != null;
                clickListener.okClickListener();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getDialog()).getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.dialogWidth), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public interface ClickListener {
        void okClickListener();
    }
}