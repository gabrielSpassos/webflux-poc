package com.gabrielspassos.poc.crypt;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;

public class CryptUtils {

    public static byte[] hexStringToByteArray(String hexString) {
        return DatatypeConverter.parseHexBinary(hexString);
    }

    public static String byteArrayToHex(byte[] byteArray) {
        return DatatypeConverter.printHexBinary(byteArray);
    }

    public static byte[] pureTextToByteArray(String pureText) {
        String base64 = Base64.getEncoder().encodeToString(pureText.getBytes());
        return base64ToByteArray(base64);
    }

    public static String byteArrayToPureText(byte[] byteArray) {
        String base64 = DatatypeConverter.printBase64Binary(byteArray);
        byte[] bytes = base64ToByteArray(base64);
        return new String(bytes);
    }

    private static byte[] base64ToByteArray(String base64) {
        return DatatypeConverter.parseBase64Binary(base64);
    }

}
