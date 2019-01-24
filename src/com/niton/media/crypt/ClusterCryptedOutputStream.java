package com.niton.media.crypt;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
/**
 * @author Nils Brugger
 * @version 2019-01-24
 * @deprecated not tested
 */
public class ClusterCryptedOutputStream extends OutputStream {
	private byte[] key;
	private int[] buffer;
	private int bufferMark = 0;
	private OutputStream os;
	public ClusterCryptedOutputStream(byte[] key, OutputStream stream,int buffersize) {
		this.key = key;
		os = new BufferedOutputStream(stream);
		buffer = new int[buffersize];
		Arrays.fill(buffer,(byte) -1);
	}

	@Override
	public void write(int paramInt) throws IOException {
		buffer[bufferMark] = paramInt;
		bufferMark ++;
		if(buffer.length == bufferMark)
			flush();
	}

	@Override
	public void flush() throws IOException {
		int end = buffer.length;
		for(int i = 0;i<buffer.length;i++)
			if(buffer[i] == -1) {
				end = i;
				break;
			}
		byte[] byted = new byte[end];
		for (int i = 0; i < end; i++) 
			byted[i] = (byte) (buffer[i]+Byte.MIN_VALUE);
		byte[] cryptedData = SimpleCluster.encrypt(key, byted);
		os.write(cryptedData);
		os.flush();
		Arrays.fill(buffer,(byte) -1);
		bufferMark = 0;
	}

}
