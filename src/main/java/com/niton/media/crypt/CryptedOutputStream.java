package com.niton.media.crypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CryptedOutputStream extends OutputStream {
	private SecretKey key;
	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private OutputStream os;
	private int buffer;
	private int counter = 0;
	/**
	 * 
	 * @param key
	 * @param stream
	 * @param buffering 0 for not
	 */
//	public CryptedOutputStream(SecretKey key, OutputStream stream,int buffering) {
//		this.key = key;
//		os = stream;
//		buffer = buffering;
//	}

	@Override
	public void write(int paramInt) throws IOException {
		bos.write(paramInt);
		counter++;
		if(buffer == counter && buffer != 0) {
			counter = 0;
			flush();
		}
	}

	@Override
	public void flush() throws IOException {
		byte[] cryptedData = new byte[0];
		try {
			cryptedData = SimpleAES.encrypt(key, bos.toByteArray());
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bos.reset();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(cryptedData);
		oos.flush();
		System.gc();
	}

}
