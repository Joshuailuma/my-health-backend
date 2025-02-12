package com.myhealth.library.utils;

import java.util.Random;

public class AppUtils {
    public static String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
