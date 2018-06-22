package com.niton.media.streaming;

import java.io.IOException;
import java.io.InputStream;

public class CounterInputStream extends InputStream {
	private InputStream out;
	private long mass;

	public CounterInputStream(InputStream out) {
		super();
		this.out = out;
	}

	public long getRecivedBytes() {
		return mass;
	}

	@Override
	public int read() throws IOException {
		mass++;
		return out.read();
	}
}
