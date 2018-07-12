package com.niton.media.streaming;

import java.io.IOException;
import java.io.OutputStream;

public class CounterOutputStream extends OutputStream {
	private OutputStream out;
	private long mass;

	public CounterOutputStream(OutputStream out) {
		super();
		this.out = out;
	}

	@Override
	public void write(int b) throws IOException {
		mass++;
		out.write(b);
	}
	public long getSendBytes() {
		return mass;
	}
	
	/**
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		out.close();
		super.close();
	}
	/**
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		out.flush();
		super.flush();
	}
}
