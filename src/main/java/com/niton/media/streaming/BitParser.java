package com.niton.media.streaming;

import com.niton.media.annotations.InDevelopment;
import com.niton.media.annotations.Untested;

/**
 * This is the BitParser Class
 * @author Nils
 * @version 2017-12-26
 */
@Untested
@InDevelopment
public class BitParser {
	public static boolean[] getBinary(long l) {
		boolean[] bits = new boolean[64];
		bits[0] = l>=0;
		if(l<0) {
			l++;
			l *= -1;
		}
		for (int i = 1; i < bits.length ; i++) {
			boolean mod = l%2>0;
			if(mod)
				l--;
			l = l/2;
			bits[i] = mod;
		}
		return bits;
	}

	public static boolean[] getBinary(int j) {
		boolean[] bits = new boolean[32];
		bits[0] = j>=0;
		if(j<0) {
			j++;
			j *= -1;
		}
		for (int i = 1; i < bits.length ; i++) {
			boolean mod = j%2>0;
			if(mod)
				j--;
			j = j/2;
			bits[i] = mod;
		}
		return bits;
	}

	public static boolean[] getBinary(short s) {
		boolean[] bits = new boolean[16];
		bits[0] = s>=0;
		if(s<0) {
			s++;
			s *= -1;
		}
		for (int i = 1; i < bits.length ; i++) {
			boolean mod = s%2!=0;
			if(mod)
				s--;
			s = (short) (s/2);
			bits[i] = mod;
		}
		return bits;
	}

	public static boolean[] getBinary(byte s) {
		boolean[] bits = new boolean[8];
		bits[0] = s>=0;
		if(s<0) {
			s++;
			s *= -1;
		}
		for (int i = 1; i < bits.length ; i++) {
			boolean mod = s%2>0;
			if(mod)
				s--;
			s = (byte) (s/2);
			bits[i] = mod;
		}
		return bits;
	}

	public static byte[] binaryToBytes(boolean[] bits) {
		byte[] bytes = new byte[bits.length/8];
		for (int i = 0; i < bytes.length; i++) {
			boolean[] thisByteBits = getSubArray(bits,i*8,(i+1)*8);
			bytes[i] = decodeByte(thisByteBits);
		}
		return bytes;
	}

	public static boolean[] bytesToBinary(byte[] bytes) {
		boolean[] bits = new boolean[bytes.length*8];
		int j = 0;
		for (int i = 0; i < bytes.length; i++) {
			boolean[] oneByteBits = getBinary(bytes[i]);
			for(int z = 0;z<oneByteBits.length;z++) {
				bits[j] = oneByteBits[z];
				j++;
			}
		}
		return bits;
	}

	public static long decodeLong(boolean[] bits) {
		boolean positive = bits[0];
		long val = 0;
		for (int i = 1; i < bits.length; i++) {
			if(bits[i])
				val += Math.pow(2, i-1);
		}
		if(!positive) {
			val *= -1;
			val--;
		}
		return val;
	}

	public static int decodeInt(boolean[] bits) {
		boolean positive = bits[0];
		int val = 0;
		for (int i = 1; i < bits.length; i++) {
			if(bits[i])
				val += Math.pow(2, i-1);
		}
		if(!positive) {
			val *= -1;
			val --;
		}
		return val;
	}

	public static short decodeShort(boolean[] bits) {
		boolean positive = bits[0];
		short val = 0;
		for (int i = 1; i < bits.length; i++) {
			if(bits[i])
				val += Math.pow(2, i-1);
		}
		if(!positive) {
			val *= -1;
			val--;
		}
		return val;
	}

	public static byte decodeByte(boolean[] bits) {
		boolean positive = bits[0];
		byte val = 0;
		for (int i = 1; i < bits.length; i++) {
			if(bits[i])
				val += Math.pow(2, i-1);
		}
		if(!positive) {
			val *= -1;
			val--;
		}
		return val;
	}

	private static boolean[] getSubArray(boolean[] bits, int fromInclusive, int toExclusive) {
		boolean[] sub = new boolean[toExclusive-fromInclusive];
		int sc = 0;
		for (int i = fromInclusive; i < toExclusive; i++) {
			sub[sc] = bits[i];
			sc++;
		}
		return sub;
	}
}

