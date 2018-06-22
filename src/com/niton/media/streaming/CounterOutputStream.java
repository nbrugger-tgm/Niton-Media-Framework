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
}
