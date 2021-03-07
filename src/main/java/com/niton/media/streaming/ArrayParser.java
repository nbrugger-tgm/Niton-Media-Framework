package com.niton.media.streaming;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is the ArrayParser Class
 * @author Nils
 * @version 2017-12-26
 */
public class ArrayParser {

	// OBJECT
	
	// OBJ-ARRAY
	public static byte[] toBytes(Object[] array) throws IllegalArgumentException, IllegalAccessException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(ByteParser.toBytes(array.length));
			for (Object t : array) {
				byte[] tAsA = ByteParser.toBytes(t);
				bos.write(ByteParser.toBytes(tAsA.length));
				bos.write(tAsA);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public static Object[] arrayFromBytes(byte[] data)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		try {
			return readNextArray(bin);
		} catch (IOException e) {
			return null;
		}
	}

	public static Object[] readNextArray(InputStream bin)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		int entrys = ByteParser.readNextInt(bin);
		Object[] array = new Object[entrys];
		for (int i = 0; i < entrys; i++) {
			array[i] = ByteParser.readNextObject(bin);
		}
		return array;
	}

	// OBJ-ARRAY
	
	// BASIC-ARRAYS
	
	// BOOLEAN
	public static byte[] toBytes(boolean[] array) throws IllegalArgumentException, IllegalAccessException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(ByteParser.toBytes(array.length));
			for (boolean t : array) {
				byte[] tAsArray = ByteParser.toBytes(t);
				bos.write(ByteParser.toBytes(tAsArray.length));
				bos.write(tAsArray);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public static boolean[] booleanArrayFromBytes(byte[] data)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		try {
			return readNextBooleanArray(bin);
		} catch (IOException e) {
			return null;
		}
	}

	public static boolean[] readNextBooleanArray(InputStream bin)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		int entrys = ByteParser.readNextInt(bin);
		boolean[] array = new boolean[entrys];
		for (int i = 0; i < entrys; i++) {
			array[i] = ByteParser.readNextBoolean(bin);
		}
		return array;
	}

	// BYTE
	public static byte[] toBytes(byte[] array) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(ByteParser.toBytes(array.length));
			for (byte t : array) {
				byte[] tAsA = ByteParser.toBytes(t);
				bos.write(ByteParser.toBytes(tAsA.length));
				bos.write(tAsA);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public static byte[] byteArrayFromBytes(byte[] data)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		try {
			return readNextByteArray(bin);
		} catch (IOException e) {
			return null;
		}
	}

	public static byte[] readNextByteArray(InputStream bin)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		int entrys = ByteParser.readNextInt(bin);
		byte[] array = new byte[entrys];
		for (int i = 0; i < entrys; i++) {
			array[i] = ByteParser.readNextByte(bin);
		}
		return array;
	}

	// SHORT
	public static byte[] toBytes(short[] array) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(ByteParser.toBytes(array.length));
			for (short t : array) {
				byte[] tAsA = ByteParser.toBytes(t);
				bos.write(ByteParser.toBytes(tAsA.length));
				bos.write(tAsA);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public static short[] shortArrayFromBytes(byte[] data)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		try {
			return readNextShortArray(bin);
		} catch (IOException e) {
			return null;
		}
	}

	public static short[] readNextShortArray(InputStream bin)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		int entrys = ByteParser.readNextInt(bin);
		short[] array = new short[entrys];
		for (int i = 0; i < entrys; i++) {
			array[i] = ByteParser.readNextShort(bin);
		}
		return array;
	}

	// SHORT
	
	// INT
	public static byte[] toBytes(int[] array) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(ByteParser.toBytes(array.length));
			for (int t : array) {
				byte[] tAsA = ByteParser.toBytes(t);
				bos.write(ByteParser.toBytes(tAsA.length));
				bos.write(tAsA);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public static int[] intArrayFromBytes(byte[] data)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		try {
			return readNextIntArray(bin);
		} catch (IOException e) {
			return null;
		}
	}

	public static int[] readNextIntArray(InputStream bin)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		int entrys = ByteParser.readNextInt(bin);
		int[] array = new int[entrys];
		for (int i = 0; i < entrys; i++) {
			array[i] = ByteParser.readNextInt(bin);
		}
		return array;
	}

	// INT
	
	// LONG
	public static byte[] toBytes(long[] array) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(ByteParser.toBytes(array.length));
			for (long t : array) {
				byte[] tAsA = ByteParser.toBytes(t);
				bos.write(ByteParser.toBytes(tAsA.length));
				bos.write(tAsA);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public static long[] longArrayFromBytes(byte[] data)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		try {
			return readNextLongArray(bin);
		} catch (IOException e) {
			return null;
		}
	}

	public static long[] readNextLongArray(InputStream bin)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		int entrys = ByteParser.readNextInt(bin);
		long[] array = new long[entrys];
		for (int i = 0; i < entrys; i++) {
			array[i] = ByteParser.readNextLong(bin);
		}
		return array;
	}

	// LONG
	
	// FLOAT
	public static byte[] toBytes(float[] array) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(ByteParser.toBytes(array.length));
			for (float t : array) {
				byte[] tAsA = ByteParser.toBytes(t);
				bos.write(ByteParser.toBytes(tAsA.length));
				bos.write(tAsA);
	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public static float[] floatArrayFromBytes(byte[] data)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		try {
			return readNextFloatArray(bin);
		} catch (IOException e) {
			return null;
		}
	}

	public static float[] readNextFloatArray(InputStream bin)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		int entrys = ByteParser.readNextInt(bin);
		float[] array = new float[entrys];
		for (int i = 0; i < entrys; i++) {
			array[i] = ByteParser.readNextFloat(bin);
		}
		return array;
	}

	// FLOAT
	
	// DOUBLE
	public static byte[] toBytes(double[] array) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(ByteParser.toBytes(array.length));
			for (double t : array) {
				byte[] tAsA = ByteParser.toBytes(t);
				bos.write(ByteParser.toBytes(tAsA.length));
				bos.write(tAsA);
	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	public static double[] doubleArrayFromBytes(byte[] data)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		try {
			return readNextDoubleArray(bin);
		} catch (IOException e) {
			return null;
		}
	}

	public static double[] readNextDoubleArray(InputStream bin)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		int entrys = ByteParser.readNextInt(bin);
		double[] array = new double[entrys];
		for (int i = 0; i < entrys; i++) {
			array[i] = ByteParser.readNextDouble(bin);
		}
		return array;
	}
}

