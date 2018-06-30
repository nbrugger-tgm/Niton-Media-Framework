package com.niton.media.json;

import java.util.HashMap;

import com.niton.media.json.basic.*;
import com.niton.media.json.types.*;

/**
 * This is the JsonSerializer Class
 * @author Nils
 * @version 2018-06-29
 */
public class JsonSerializer {
	private static 	HashMap<Class<?>, Class<? extends JsonValue<?>>> typeTable = new HashMap<>();
	static {
		registerJsonType(String.class, JsonString.class);
		registerJsonType(Character.class, JsonChar.class);
		registerJsonType(Byte.class, JsonByte.class);
		registerJsonType(Short.class, JsonShort.class);
		registerJsonType(Integer.class, JsonInt.class);
		registerJsonType(Long.class, JsonLong.class);
		registerJsonType(Boolean.class, JsonBoolean.class);
		registerJsonType(Float.class, JsonFloat.class);
		registerJsonType(Double.class, JsonDouble.class);
	}
	@SuppressWarnings("unchecked")
	public static <T> Class<? extends JsonValue<T>> getJsonFor(Class<T> c) {
		return (Class<? extends JsonValue<T>>) typeTable.get(c);
	}
	public static <T> void registerJsonType(Class<T> baseClass,Class<? extends JsonValue<T>> jsonType) {
		typeTable.put(baseClass, jsonType);
	}
}

