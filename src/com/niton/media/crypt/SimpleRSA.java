package com.niton.media.crypt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author JavaDigest, Nils Brugger
 * 
 */
public class SimpleRSA {

	/**
	 * String to hold name of the encryption algorithm.
	 */
	public static final String ALGORITHM = "RSA";

	/**
	 * Generate key which contains a pair of private and public key using 1024
	 * bytes. Store the set of keys in Prvate.key and Public.key files.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static KeyPair generateKeys() throws NoSuchAlgorithmException {
		final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
		keyGen.initialize(2136);
		final KeyPair key = keyGen.generateKeyPair();
		return key;
	}

	/**
	 * Encrypt the plain text using public key.
	 * 
	 * @param text : original plain text
	 * @param key  :The public key
	 * @return Encrypted text
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws                           java.lang.Exception
	 */
	public static byte[] encrypt(byte[] data, PublicKey key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] cipherText = null;
		// get an RSA cipher object and print the provider
		final Cipher cipher = Cipher.getInstance(ALGORITHM);
		// encrypt the plain text using the public key
		cipher.init(Cipher.ENCRYPT_MODE, key);
		cipherText = cipher.doFinal(data);
		return cipherText;
	}

	/**
	 * Decrypt text using private key.
	 * 
	 * @param text :encrypted text
	 * @param key  :The private key
	 * @return plain text or null for wrong key
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws                          java.lang.Exception
	 */
	public static byte[] decrypt(byte[] text, PrivateKey key)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] dectyptedText = null;
		// get an RSA cipher object and print the provider
		final Cipher cipher = Cipher.getInstance(ALGORITHM);
		// decrypt the text using the private key
		cipher.init(Cipher.DECRYPT_MODE, key);
		try {
			dectyptedText = cipher.doFinal(text);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			return null;
		}
		return dectyptedText;
	}
}