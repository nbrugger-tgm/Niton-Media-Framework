package com.niton.media.crypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.*;
import java.util.Random;

/**
 * A utility class for en/decrypting byte[] and Objects using the AES algorythm
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
	
	/**
	 * <b>Description : Encrypts your bytes</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2019-01-24
	 * @param key the key to encrypt with
	 * @param dataToEncrypt the data to encrypt
	 * @return the encrypted data
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] encrypt(SecretKey key, byte[] dataToEncrypt) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		int padding = pad-(dataToEncrypt.length%pad);
		byte[] padded = new byte[dataToEncrypt.length + padding];
		Random r = new SecureRandom();
		for (int i = 0; i < padded.length; i++) {
			padded[i] = (byte) (dataToEncrypt.length > i ? dataToEncrypt[i] : r.nextInt());
		}
		padded[padded.length-1] = (byte) padding;
		return doCrypto(Cipher.ENCRYPT_MODE, key, padded);
	}

	/**
	 * <b>Description : decrypts the bytes</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2019-01-24
	 * @param key the key the data was encrypted with
	 * @param encryptedData the data to decrypt
	 * @return the decrypted data or null if the key wasn't right
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException 
	 */
	public static byte[] decrypt(SecretKey key, byte[] encryptedData) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
			byte[] dec;
			try {
				dec = doCrypto(Cipher.DECRYPT_MODE, key, encryptedData);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				return null;
			}
			byte[] ret = new byte[dec.length - (dec[dec.length - 1])];
			System.arraycopy(dec, 0, ret, 0, ret.length);
			return ret;
	}

	private static byte[] doCrypto(int cipherMode, SecretKey key, byte[] data) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec ivspec = new IvParameterSpec(IV);
		cipher.init(cipherMode, key, ivspec);
		byte[] outputBytes = cipher.doFinal(data);
		return outputBytes;
	}

	/**
	 * Generates a AES key of the given bitsize
	 * @param size the number of bits of the AES key
	 * @return the private key
	 */
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

	/**
	 * Creates a AES key from a byte array. The size of the array doesn't matter BUT if it is smaller than 256 BIT -> 32 BYTE security is lower as the remaining bits are filled with '0'
	 *
	 * Also making the array bigger than 32 bytes has no effect on the returned key
	 * @param bytes the array to generate the key from
	 * @return the AES key
	 */
	public static SecretKey getKey(byte[] bytes) {
		if(bytes.length != 128/8 && bytes.length != 2*128/8){
			byte[] newArr = new byte[2*128/8];
			for (int i = 0; i < Math.min(bytes.length,newArr.length); i++) {
				newArr[i] = bytes[i];
			}
			bytes = newArr;
		}
		SecretKey key2 = new SecretKeySpec(bytes, 0, bytes.length, ALGORITHM);
		return key2;
	}

	/**
	 * same as {@link #getKey(byte[])} the difference being that you can decide between a 128 and 256 bit key
	 * @param bytes the bytes to generate the AES key from
	 * @param size the bitsize of the AES key
	 * @return the AES key
	 */
	public static SecretKey getKey(byte[] bytes,int size) {
		if(bytes.length != size/8){
			byte[] newArr = new byte[size/8];
			for (int i = 0; i < Math.min(bytes.length,newArr.length); i++) {
				newArr[i] = bytes[i];
			}
			bytes = newArr;
		}
		return new SecretKeySpec(bytes, 0, bytes.length, ALGORITHM);
	}

	/**
	 * Generates a secure random array of the given size
	 * @param size the amount of bytes in the array
	 * @return the random array
	 */
	public static byte[] generateRandom(int size) {
		Random r = new SecureRandom();
		byte[] b = new byte[size];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) r.nextInt(255);
		}
		return b;
	}

	/**
	 * Creates an output stream that encrypts its content
	 * @param key the key to encrypt with
	 * @param os the output stream to redirect to
	 * @return the encrypting stream
	 * @throws InvalidKeyException thrown by {@link Cipher#init(int, Key, AlgorithmParameters)}
	 * @throws InvalidAlgorithmParameterException thrown by {@link Cipher#init(int, Key, AlgorithmParameters)}
	 * @throws NoSuchAlgorithmException thrown by {@link Cipher#init(int, Key, AlgorithmParameters)}
	 * @throws NoSuchPaddingException thrown by {@link Cipher#init(int, Key, AlgorithmParameters)}
	 */
	public static CipherOutputStream cryptOutputStream(SecretKey key,OutputStream os) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec ivspec = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
		CipherOutputStream out = new CipherOutputStream(os, cipher);
		return out;
	}

	/**
	 * Creates a stream that decrypts its content with AES
	 * @param key the key to decrypt with
	 * @param os the InputStream to use as source to decrypt
	 * @return the input stream
	 * @throws InvalidKeyException thrown by {@link Cipher#init(int, Key, AlgorithmParameters)}
	 * @throws InvalidAlgorithmParameterException thrown by {@link Cipher#init(int, Key, AlgorithmParameters)}
	 * @throws NoSuchAlgorithmException thrown by {@link Cipher#init(int, Key, AlgorithmParameters)}
	 * @throws NoSuchPaddingException thrown by {@link Cipher#init(int, Key, AlgorithmParameters)}
	 */
	public static CipherInputStream decryptInputStream(SecretKey key,InputStream os) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec ivspec = new IvParameterSpec(IV);
		cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
		CipherInputStream out = new CipherInputStream(os, cipher);
		return out;
	}

	/**
	 * Encrypts an object
	 * @param serial the object to encrypt
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws IOException
	 */
	public static SealedObject encryptObject(Serializable serial, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, IOException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec ivspec = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
		return new SealedObject(serial, cipher);
	}
	public static Serializable decryptObject(SealedObject enc, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, IOException, ClassNotFoundException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		IvParameterSpec ivspec = new IvParameterSpec(IV);
		cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
		return (Serializable) enc.getObject(cipher);
	}
}
