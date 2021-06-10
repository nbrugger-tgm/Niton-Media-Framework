package com.niton.media.streaming;

import com.niton.media.annotations.UseCaseTested;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@UseCaseTested
public class CounterOutputStream extends FilterOutputStream {
	private long mass;

	public CounterOutputStream(OutputStream out) {
		super(out);
	}

	@Override
	public void write(int b) throws IOException {
		mass++;
		super.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		super.write(b, off, len);
		mass += len;
	}

	public long getSendBytes() {
		return mass;
	}

}
