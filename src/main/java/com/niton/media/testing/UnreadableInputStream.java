package com.niton.media.testing;

import java.io.IOException;
import java.io.InputStream;

public class UnreadableInputStream extends InputStream {
	@Override
	public int read() throws IOException {
		throw new IOException("This stream is unreadable for testing purposes");
	}
}
