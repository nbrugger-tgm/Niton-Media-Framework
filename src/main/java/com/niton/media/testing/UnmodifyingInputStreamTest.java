package com.niton.media.testing;

import java.io.FilterInputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class UnmodifyingInputStreamTest<S extends FilterInputStream> extends InputStreamTest<S> {

	/**
	 * @return the number of samples to test with
	 */
	public abstract int getSampleCount();

	/**
	 * @return the size in bytes of a sample
	 */
	public abstract int getSampleSize();

	@Override
	public Map<byte[], byte[]> getDataSamples() {
		Map<byte[],byte[]> map = new HashMap<>();
		for (int i = 0; i < getSampleCount(); i++) {
			byte[] rand = random(getSampleSize());
			map.put(rand,rand);
		}
		return map;
	}

	private byte[] random(int sampleSize){
		byte[] ba = new byte[sampleSize];
		for (int i = 0; i < sampleSize; i++) {
			ba[i] = (byte) i;
		}
		return ba;
	}
}
