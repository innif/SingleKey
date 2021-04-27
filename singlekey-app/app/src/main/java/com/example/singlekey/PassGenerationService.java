package com.example.singlekey;

import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PassGenerationService {
    String masterPass;
    String salt;
    String chars = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ123456789!$%&?+#<>-@_()[]{}";

    public PassGenerationService(String salt){
        this.salt = salt;
    }

    public String generatePassword(CharSequence input){
        String hashingString = combineStrings(input.toString(), masterPass);
        hashingString = combineStrings(hashingString, salt);
        byte[] hash = hash(hashingString);
        int[] unsignedHash = new int[0];
        if (hash != null) {
            unsignedHash = signedToUnsigned(hash);
            return hashToPass(unsignedHash, 10, chars); //TODO
        }
        else {
            return null;
        }
    }

    public void setMasterPass(String masterPass) {
        this.masterPass = masterPass;
    }

    private String combineStrings(String a, String b){
        return a + b; //TODO
    }

    private byte[] hash(@NonNull String input){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null; //TODO
    }

    private int[] signedToUnsigned(@NonNull byte[] in){
        int[] out = new int[in.length];
        for (int i = 0; i < in.length; i++) {
            int temp = in[i];
            while (temp < 0)
                temp += 256;
            out[i] = temp;
        }
        return out;
    }

    private String hashToPass(int[] hash, int len, String characterbase){
        StringBuilder out = new StringBuilder();
        int carry = 0;
        int numchars = characterbase.length();
        for (int i = 0; i < len; i++) {
            int charIndex = hash[i%hash.length] + carry;
            carry = charIndex / numchars;
            charIndex = charIndex % numchars;
            out.append(characterbase.charAt(charIndex));
        }
        return out.toString();
    }
}
