package ru.sstu.shopik.utils;

import java.util.Random;

public class RandomStringUtil {

    private static String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz0123456789";
    private static Random RANDOM = new Random();


    public static String generateString(int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length()));
        }
        return new String(text);
    }
}
