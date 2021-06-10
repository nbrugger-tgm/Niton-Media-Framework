package com.niton.media.streaming;

import com.niton.media.testing.UnmodifyingInputStreamTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CounterInputStreamTest extends UnmodifyingInputStreamTest<CounterInputStream> {

	@Test
	@DisplayName("Sent byte counting")
	void getSendBytes() throws IOException {
		CounterInputStream cis = create(new ByteArrayInputStream(new byte[8*1024]));
		assertEquals(0,cis.getReceivedBytes());
		cis.read();
		assertEquals(1,cis.getReceivedBytes());
		cis.read(new byte[511]);
		assertEquals(512,cis.getReceivedBytes());
		cis.close();
	}

	@Override
	public CounterInputStream create(InputStream source) {
		return new CounterInputStream(source);
	}

	@Override
	public int getSampleCount() {
		return 5;
	}

	@Override
	public int getSampleSize() {
		return 1000;
	}
}