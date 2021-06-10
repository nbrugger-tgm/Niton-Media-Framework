package com.niton.media.streaming;

import com.niton.media.annotations.Coverage;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This stream counts the bytes passing thru
 */
@Coverage(lines = 1,methods = 1)
public class CounterInputStream extends FilterInputStream {
	private long mass;

	public CounterInputStream(InputStream out) {
		super(out);
	}

	/**
	 * <i>Note:</i> when used with a BufferedInputStream the byte count will increase in bursts
	 * @return the amount of bytes that passed this stream (were read from it)
	 */
	public long getReceivedBytes() {
		return mass;
	}

	@Override
	public int read() throws IOException {
		mass++;
		return super.read();
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int i = super.read(b,off,len);
		mass += i;
		return i;
	}
}
