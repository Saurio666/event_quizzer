package com.sauriotech.eventquizzer.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthorizer {
    private final String secret;

    public BasicAuthorizer(@Value("${EVENT_QUIZZER_TOKEN_SECRET}") String secret) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("EVENT_QUIZZER_TOKEN_SECRET must be at least 32 characters");
        }
        this.secret = secret;
    }

    public Credentials parseToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        String data = Encrypter.decrypt(token, secret);
        if (data == null) {
            return null;
        }
        String[] info = data.split(";");
        try {
            if (info.length == 2) {
                return new Credentials(Long.parseLong(info[0]), info[1]);
            }
            if (info.length == 1) {
                return new Credentials(Long.parseLong(info[0]), null);
            }
        } catch (NumberFormatException ignored) {
            // Invalid token payload. Never log the token or decrypted data.
        }
        return null;
    }

    public String encryptData(String token) {
        return Encrypter.encrypt(token, secret);
    }
}
