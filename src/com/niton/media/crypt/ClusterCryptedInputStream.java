package com.niton.media.crypt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
/**
 * @author Nils Brugger
 * @version 2019-01-24
 * @deprecated not tested
 */
@Deprecated
public class ClusterCryptedInputStream extends InputStream {
	private byte[] key;
	private byte[] buffer;
	private byte[] in;
	private int bufferMark = 0;
	private InputStream os;

	public ClusterCryptedInputStream(byte[] key, InputStream stream, int buffersize) throws IOException {
		this.key = key;
		os = new BufferedInputStream(stream);
		bufferMark = buffersize;
		in = SimpleCluster.encrypt(key, new byte[buffersize]);
		readBuffer();
	}

	@Override
	public int read() throws IOException {
		if (bufferMark == buffer.length)
			readBuffer();
		int ret = buffer[bufferMark] - Byte.MIN_VALUE;
		bufferMark++;
		return ret;
	}

	private void readBuffer() throws IOException {
		Arrays.fill(in, (byte)-1);
		int i = 0;
		for (; i < in.length; i++) {
			int readen = os.read();
			if (readen != -1)
				in[i] = (byte) (readen);
			else
				break;
		}
		byte[] cutted = new byte[i];
		for (int j = 0; j < cutted.length; j++)
			cutted[j] = in[j];
		buffer = SimpleCluster.decrypt(key, cutted);
		bufferMark = 0;
	}

}
