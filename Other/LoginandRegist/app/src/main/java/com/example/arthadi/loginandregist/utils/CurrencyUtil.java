package com.example.arthadi.loginandregist.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by blastocode on 6/10/17.
 */

public class CurrencyUtil {
    private static DecimalFormat decimalFormat;// = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    private static DecimalFormatSymbols decimalFormatSymbols;// = new DecimalFormatSymbols();

    public static DecimalFormat getFormatter() {
        if(decimalFormat == null) {
            decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.US);
            decimalFormatSymbols = new DecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("Rp. ");
            decimalFormatSymbols.setMonetaryDecimalSeparator(',');
            decimalFormatSymbols.setGroupingSeparator('.');

            decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        }

        return decimalFormat;
    }
    public static String formatIdr(long amount) {
        DecimalFormat decimalFormat = getFormatter();
        return decimalFormat.format(amount);
    }
}
