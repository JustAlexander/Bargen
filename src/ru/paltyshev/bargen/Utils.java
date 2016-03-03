package ru.paltyshev.bargen;

import ru.paltyshev.bargen.exeption.InvalidBarcodeNumberException;
import ru.paltyshev.bargen.exeption.RangeBarcodeNumbersException;

import java.util.prefs.Preferences;


public class Utils extends Thread {
    private static Preferences preferences = Preferences.userRoot().node("BarcodeCounts");

    public static int getCountOfBarcode() {
        return preferences.getInt("countOfBarcode", 1);
    }

    public static void updateCount(int count) {
        preferences.putInt("countOfBarcode", count);
    }

    public static int inputValidation(String in) throws InvalidBarcodeNumberException {

        int out = 0;

        try {
            out = Integer.parseInt(in);
            if (out < 1) throw new InvalidBarcodeNumberException();
        } catch (NumberFormatException e) {
            throw new InvalidBarcodeNumberException();
        }
        return out;
    }

    public static void twoNumbersValidation(int first, int last) throws RangeBarcodeNumbersException {
        if (first > last)
            throw new RangeBarcodeNumbersException();
    }

}
