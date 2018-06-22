package com.niton.media.crypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.crypto.SecretKey;

public class CryptedInputStream extends InputStream {
	private byte[] reciedData = new byte[0];
	private InputStream instream;
	private SecretKey key;
	private int mark = 0;

//	public CryptedInputStream(InputStream inputStream, SecretKey key) {
//		instream = inputStream;
//		this.key = key;
//	}

	@Override
	public int read() throws IOException {
		if (mark == 0)
			readBuffer();
		if (mark == reciedData.length) {
			readBuffer();
			mark = 0;
		}
		if (reciedData.length == mark)
			return -1;
		int i = reciedData[mark];
		mark++;
		return i;
	}

	public byte[] readAll() throws IOException {
		readBuffer();
		return reciedData;
	}

	private void readBuffer() throws IOException {
		try {
			if (instream.available() == 0) {
				reciedData = new byte[0];
				return;
			}
			ObjectInputStream oin = new ObjectInputStream(instream);
			reciedData = (byte[]) oin.readObject();
			reciedData = SimpleAES.decrypt(key, reciedData);
		} catch (ClassNotFoundException e) {
		}
	}

	@Override
	public int available() throws IOException {
		return reciedData.length - mark;
	}
}
