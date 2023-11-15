package com.sauriotech.eventquizzer.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.sauriotech.eventquizzer.utils.DummyLogger;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class Encrypter {
	private static final String ALGORITHM = "AES";

	private static SecretKeySpec prepareSecreteKey(String myKey) {
		MessageDigest sha = null;
		try {
			byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			return new SecretKeySpec(key, ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encrypt(String strToEncrypt, String secret) {
		try {
			SecretKeySpec secretKey = prepareSecreteKey(secret);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			String info = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
			
			return info.replace("/", "__.__");
		} catch (Exception e) {
			DummyLogger.sysout("Encrypter", "encrypt", "Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt, String secret) {
		try {
			SecretKeySpec secretKey = prepareSecreteKey(secret);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt.replace("__.__", "/"))));
		} catch (Exception e) {
			DummyLogger.sysout("Encrypter", "encrypt", "Error while decrypting: " + e.toString());
		}
		return null;
	}
}
