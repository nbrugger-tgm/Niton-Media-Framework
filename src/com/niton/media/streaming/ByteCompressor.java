package com.niton.media.streaming;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * This is the ByteCompressor Class
 * @author Nils
 * @version 2017-12-26
 */
class ByteCompressor {
	public static byte[] simpleCompress(byte[] bytes) {
		int times = 1;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ByteArrayOutputStream pattern = new ByteArrayOutputStream();
		for (int i = 1; i < bytes.length; i++) {
			byte cur = bytes[i];
			byte last = bytes[i - 1];
			if (cur == last) {
				times++;
				continue;
			}
			if (times > 2) {
				if (pattern.size() > 0)
					writePattern(bos, pattern);
				while (times > Byte.MAX_VALUE) {
					bos.write(0);
					bos.write(Byte.MAX_VALUE);
					bos.write(last);
					times -= Byte.MAX_VALUE;
				}
				bos.write(0);
				bos.write(times);
				bos.write(last);
				times = 1;
			} else {
				for (int j = 0; j < times; j++)
					pattern.write(last);
				times = 1;
			}
		}
		if (times > 2) {
			if (pattern.size() > 0)
				writePattern(bos, pattern);
			while (times > Byte.MAX_VALUE) {
				bos.write(0);
				bos.write(Byte.MAX_VALUE);
				bos.write(bytes[bytes.length - 1]);
				times -= Byte.MAX_VALUE;
			}
			bos.write(0);
			bos.write(times);
			bos.write(bytes[bytes.length - 1]);
		} else {
			for (int j = 0; j < times; j++)
				pattern.write(bytes[bytes.length - 1]);
			writePattern(bos, pattern);
		}
		return bos.toByteArray();
	}

	private static void writePattern(ByteArrayOutputStream bos, ByteArrayOutputStream pattern) {
		byte[] complPattern = pattern.toByteArray();
		pattern.reset();
		int size = complPattern.length;
		int pos = 0;
		while (size > Byte.MAX_VALUE) {
			bos.write(1);
			bos.write(Byte.MAX_VALUE);
			for (byte b = 0; b < Byte.MAX_VALUE; b++) {
				bos.write(complPattern[pos]);
				pos++;
			}
			size -= Byte.MAX_VALUE;
		}
		bos.write(1);
		bos.write(size);
		for (byte b = 0; b < size; b++) {
			bos.write(complPattern[pos]);
			pos++;
		}
	}

	public static byte[] simpleDecompress(byte[] compressed) {
		ByteArrayInputStream bin = new ByteArrayInputStream(compressed);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int b = bin.read();
		while (b != -1) {
			boolean mode = b == 1;
			int count = bin.read();
			if (mode) {
				for (int i = 0; i < count; i++) {
					int v = bin.read();
					bos.write(v);
				}
			} else {
				int v = bin.read();
				for (int i = 0; i < count; i++) {
					bos.write(v);
				}
			}
			b = bin.read();
		}
		return bos.toByteArray();
	}

}

