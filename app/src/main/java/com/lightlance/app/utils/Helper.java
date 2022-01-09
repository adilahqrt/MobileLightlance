package com.lightlance.app.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Helper {
    public static String addThousandSeparator(int value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        System.out.println(formatter.format(value));

        return formatter.format(value);
    }
}
