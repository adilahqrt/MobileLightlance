package com.lightlance.app.utils;

import android.util.Patterns;

public abstract class Validator {
    /**
     * memvalidasi apakah email valid atau tidak
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * memvalidasi panjang dari string / karakter
     * @param value
     * @param requireLength
     * @return
     */
    public static boolean validateCharLength(String value, int requireLength) {
        return value.length() >= requireLength;
    }
}
