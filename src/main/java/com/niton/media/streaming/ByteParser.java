package com.niton.media.streaming;

import com.niton.media.annotations.InDevelopment;
import com.niton.media.annotations.Untested;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Untested
@InDevelopment
public class ByteParser {
	/*
	 * BASIC TYPES
	 */

	// INT
	public static byte[] toBytes(int i) {
		return BitParser.binaryToBytes(BitParser.getBinary(i));
	}

	public static int intFromBytes(byte[] data) {
		return BitParser.decodeInt(BitParser.bytesToBinary(data));
	}

	public static int readNextInt(InputStream bin) throws IOException {
		byte[] b = new byte[4];
		bin.read(b);
		return intFromBytes(b);
	}
	// INT

	// BYTE
	public static byte[] toBytes(byte i) {
		return new byte[] { i };
	}

	public static byte byteFromBytes(byte[] data) {
		return data[0];
	}

	public static byte readNextByte(InputStream bin) throws IOException {
		return (byte) bin.read();
	}
	// BYTE

	// SHORT
	public static byte[] toBytes(short i) {
		return BitParser.binaryToBytes(BitParser.getBinary(i));
	}

	public static short shortFromBytes(byte[] data) {
		return BitParser.decodeShort(BitParser.bytesToBinary(data));
	}

	public static short readNextShort(InputStream bin) throws IOException {
		byte[] b = new byte[2];
		bin.read(b);
		return shortFromBytes(b);
	}
	// SHORT

	// LONG
	public static byte[] toBytes(long i) {
		return BitParser.binaryToBytes(BitParser.getBinary(i));
	}

	public static long longFromBytes(byte[] data) {
		return BitParser.decodeLong(BitParser.bytesToBinary(data));
	}

	public static long readNextLong(InputStream bin) throws IOException {
		byte[] b = new byte[8];
		bin.read(b);
		return longFromBytes(b);
	}
	// LONG

	// DOUBLE    //LONG

	// DOUBLE
	public static byte[] toBytes(double d) {
		return toBytes(Double.doubleToLongBits(d));
	}

	public static double doubleFromBytes(byte[] data) {
		return Double.longBitsToDouble(longFromBytes(data));
	}

	public static double readNextDouble(InputStream in) throws IOException {
		return Double.longBitsToDouble(readNextLong(in));
	}
	// DOUBLE

	// FLOAT    //DOUBLE

	// FLOAT
	public static byte[] toBytes(float d) {
		return toBytes(Float.floatToIntBits(d));
	}

	public static float floatFromBytes(byte[] data) {
		return Float.intBitsToFloat(intFromBytes(data));
	}

	public static float readNextFloat(InputStream in) throws IOException {
		return Float.intBitsToFloat(readNextInt(in));
	}
	// FLOAT

	// CHAR    //FLOAT

	// CHAR
	public static byte[] toBytes(char d) {
		return toBytes((short) d);
	}

	public static char charFromBytes(byte[] data) {
		return (char) shortFromBytes(data);
	}

	public static char readNextChar(InputStream in) throws IOException {
		return (char) readNextShort(in);
	}
	// CHAR

	// BOOLEAN    //CHAR

	// BOOLEAN
	public static byte[] toBytes(boolean d) {
		return new byte[] { (byte) (d ? 1 : 0) };
	}

	public static boolean booleanFromBytes(byte[] data) {
		return data[0] > 0;
	}

	public static boolean readNextBoolean(InputStream in) throws IOException {
		return in.read() > 0;
	}
	// BOOLEAN    //BOOLEAN

	/*
	 * BASIC-TYPES
	 */

	/*
	 * ADVANCED-TYPES
	 */

	// STRING
public static byte[] toBytes(String text) {
		byte[] data;
		try {
			data = text.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			data = text.getBytes();
		}
		ByteArrayOutputStream boStream = new ByteArrayOutputStream();
		try {
			boStream.write(toBytes(data.length));
			boStream.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return boStream.toByteArray();
	}

	public static String stringFromBytes(byte[] data) {
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		try {
			return readNextString(bin);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String readNextString(InputStream in) throws IOException {
		int size = readNextInt(in);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		for (int i = 0; i < size; i++) {
			bos.write(in.read());
		}
		return new String(bos.toByteArray(), "UTF-8");
	}
	// STRING

	// OBJECT
	static byte[] toBytes(Object o) throws IOException, IllegalArgumentException, IllegalAccessException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (o == null) {
			bos.write(toBytes("n"));
			return bos.toByteArray();
		}
		Class<?> clazz = o.getClass();
		if (clazz.isArray()) {
			bos.write(toBytes("[]"));
			Object[] array = null;
			Class<?> arrayType = clazz.getComponentType();
			if (!arrayType.isPrimitive()) {
				array = (Object[]) o;
				bos.write(toBytes(array));
			} else {
				switch (arrayType.getName()) {
				case "double":
					double[] dprimArray = (double[]) o;
					return toBytes(dprimArray);
				case "float":
					float[] fprimArray = (float[]) o;
					return toBytes(fprimArray);
				case "int":
					int[] iprimArray = (int[]) o;
					return toBytes(iprimArray);
				case "long":
					long[] lprimArray = (long[]) o;
					return toBytes(lprimArray);
				case "short":
					short[] sprimArray = (short[]) o;
					return toBytes(sprimArray);
				case "byte":
					byte[] bprimArray = (byte[]) o;
					return toBytes(bprimArray);
				case "boolean":
					boolean[] boprimArray = (boolean[]) o;
					return toBytes(boprimArray);
				case "char":
					char[] cprimArray = (char[]) o;
					return toBytes(cprimArray);
				}
			}
		} else if (clazz.isPrimitive()) {
			bos.write(toBytes("prim"));
			bos.write(toBytes(clazz.getSimpleName()));
			switch (clazz.getSimpleName()) {
			case "double":
				bos.write(toBytes((double) o));
				break;
			case "float":
				bos.write(toBytes((float) o));
				break;
			case "int":
				bos.write(toBytes((int) o));
				break;
			case "long":
				bos.write(toBytes((long) o));
				break;
			case "short":
				bos.write(toBytes((short) o));
				break;
			case "byte":
				bos.write(toBytes((byte) o));
				break;
			case "boolean":
				bos.write(toBytes((boolean) o));
				break;
			case "char":
				bos.write(toBytes((char) o));
				break;
			}
		} else if (clazz.isEnum()) {
			bos.write(toBytes("enum"));
			bos.write(toBytes(clazz.getName()));
			bos.write(toBytes(o.toString()));
		} else {
			bos.write(toBytes("class"));
			bos.write(toBytes(clazz.getName()));
			Field[] vars = clazz.getDeclaredFields();
			bos.write(toBytes(vars.length));
			for (int i = 0; i < vars.length; i++) {
				int mods = vars[i].getModifiers();
				if (Modifier.isFinal(mods) || Modifier.isStatic(mods))
					continue;

				vars[i].setAccessible(true);
				bos.write(toBytes(vars[i].get(o)));
			}
		}

		return bos.toByteArray();
	}
	static Object objectFromBytes(byte[] val) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		return readNextObject(new ByteArrayInputStream(val));
	}

	static Object readNextObject(InputStream in)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String type = readNextString(in);
		if (type.equals("n")) {
			return null;
		}
		if (type.equals("[]")) {
			return ArrayParser.readNextArray(in);
		} else if (type.equals("prim")) {
			switch (readNextString(in)) {
			case "double":
				return readNextDouble(in);
			case "float":
				return readNextFloat(in);
			case "int":
				return readNextInt(in);
			case "long":
				return readNextInt(in);
			case "short":
				return readNextShort(in);
			case "byte":
				return readNextByte(in);
			case "boolean":
				return readNextBoolean(in);
			case "char":
				return readNextChar(in);
			default:
				return readNextInt(in);
			}
		} else if (type.equals("enum")) {
			String name = readNextString(in); // Name des Pfades : com.lb.logic.Day
			String value = readNextString(in); // Beispiel : FRIDAY
			Class<?> clazz = Class.forName(name);
			Object[] vals = clazz.getEnumConstants();
			for (Object object : vals)
				if (object.toString().equals(value))
					return object;
			return vals[0];
		} else if (type.equals("class")) {
			String name = readNextString(in);
			Class<?> clazz = Class.forName(name);
			Field[] vars = clazz.getDeclaredFields();
			Object temp = clazz.newInstance();
			for (int i = 0; i < vars.length; i++) {
				int mods = vars[i].getModifiers();
				if (Modifier.isFinal(mods) || Modifier.isStatic(mods))
					continue;
				Object value = readNextObject(in);
				vars[i].setAccessible(true);
				vars[i].set(temp, value);
			}
			return temp;
		} else {
			return null;
		}
	}
	// OBJECT
}
