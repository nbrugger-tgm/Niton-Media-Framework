package com.niton.media.streaming;

import com.niton.media.testing.UnmodifyingInputStreamTest;

import java.io.FilterInputStream;
import java.io.InputStream;

public class FilterStreamTest extends UnmodifyingInputStreamTest<FilterInputStream> {
	@Override
	public FilterInputStream create(InputStream source) {
		return new FilterInputStream(source){};
	}

	@Override
	public int getSampleCount() {
		return 5;
	}

	@Override
	public int getSampleSize() {
		return 1024;
	}
}
