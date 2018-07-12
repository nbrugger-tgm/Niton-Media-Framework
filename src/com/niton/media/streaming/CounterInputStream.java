package com.niton.media.streaming;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;

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
	
	/**
	 * @see java.io.InputStream#close()
	 */
	@Override
	public void close() throws IOException {
		out.close();
		super.close();
	}

}
