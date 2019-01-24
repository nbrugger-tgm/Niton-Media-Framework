package com.niton.media.crypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.*;

/**
 * A utility class for en/decrypting byte[] and Objects
 * 
 * @author www.codejava.net, Nils, Stack Overflow
 *
 */
public class SimpleAES {
	private static final String ALGORITHM = "AES";
	private final static byte[] IV = new byte[16];
	private static final String TRANSFORMATION = "AES/CBC/pkcs5padding";
	private static final int pad = 16;
	public static SecretKey lastKey;
	
	public static byte[] encrypt(SecretKey key, byte[] dataToEncrypt) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		int padding = pad-(dataToEncrypt.length%pad);
		byte[] padded = new byte[dataToEncrypt.length + padding];
		Random r = new Random();
		for (int i = 0; i < padded.length; i++) {
			padded[i] = (byte) (dataToEncrypt.length > i ? dataToEncrypt[i] : r.nextInt());
		}
		padded[padded.length-1] = (byte) padding;
		return doCrypto(Cipher.ENCRYPT_MODE, key, padded);
	}

	public static byte[] decrypt(SecretKey key, byte[] encryptedData) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException {
		byte[] dec;
		try {
			dec = doCrypto(Cipher.DECRYPT_MODE, key, encryptedData);
		} catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			return null;
		}
		byte[] ret = new byte[dec.length - (dec[dec.length - 1])];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = dec[i];
		}
		return ret;
	}

	private static byte[] doCrypto(int cipherMode, SecretKey key, byte[] data) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec ivspec = new IvParameterSpec(IV);
		cipher.init(cipherMode, key, ivspec);
		byte[] outputBytes = cipher.doFinal(data);
		return outputBytes;
	}

	public static SecretKey generateKey(int size) {
		KeyGenerator gen = null;
		try {
			gen = KeyGenerator.getInstance(ALGORITHM);
			gen.init(size);
			SecretKey key = gen.generateKey();
			lastKey = key;
			return key;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SecretKey getKey(byte[] bytes) {
		SecretKey key2 = new SecretKeySpec(bytes, 0, bytes.length, ALGORITHM);
		return key2;
	}

	public static byte[] generateRandom(int size) {
		Random r = new Random();
		byte[] b = new byte[size];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) r.nextInt(255);
		}
		return b;
	}
	public static CipherOutputStream cryptOutputStream(SecretKey key,OutputStream os) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec ivspec = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
		CipherOutputStream out = new CipherOutputStream(os, cipher);
		return out;
	}
	public static CipherInputStream decryptInputStream(SecretKey key,InputStream os) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec ivspec = new IvParameterSpec(IV);
		cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
		CipherInputStream out = new CipherInputStream(os, cipher);
		return out;
	}
	public static SealedObject encryptObject(Serializable serial, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, IOException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec ivspec = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
		SealedObject so = new SealedObject(serial, cipher);
		return so;
	}
	public static Serializable decryptObject(SealedObject enc, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, IOException, ClassNotFoundException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec ivspec = new IvParameterSpec(IV);
		cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
		return (Serializable) enc.getObject(cipher);
	}
}
