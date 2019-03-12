package web.com.store.service;

import java.util.Base64;

// https://www.baeldung.com/java-base64-encode-and-decode

public class Base64Security {
    public static String urlEncoder(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes());
    }

    public static String urlDecoder(String value) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(value);
        return new String(decodedBytes);
    }

    public static String valueEncoder(String value) {
        return Base64.getEncoder().withoutPadding().encodeToString(value.getBytes());
    }

    public static String valueDecoder(String value) {
        byte[] decodedBytes = Base64.getDecoder().decode(value);
        return new String(decodedBytes);
    }
}
