package com.niton.media.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.types.JsonBoolean;
import com.niton.media.json.types.JsonByte;
import com.niton.media.json.types.JsonChar;
import com.niton.media.json.types.JsonDouble;
import com.niton.media.json.types.JsonFloat;
import com.niton.media.json.types.JsonInt;
import com.niton.media.json.types.JsonLong;
import com.niton.media.json.types.JsonShort;
import com.niton.media.json.types.advanced.JsonArrayList;

/**
 * This is the JsonSerializer Class
 * 
 * @author Nils
 * @version 2018-06-29
 */
public class JsonSerializer {
	private static HashMap<Class<?>, Class<? extends JsonValue<?>>> typeTable = new HashMap<>();
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
		
		
		registerJsonType(ArrayList.class, (Class<? extends JsonValue<?>>) JsonArrayList.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<? extends JsonValue<T>> getJsonFor(Class<T> c) {
		return (Class<? extends JsonValue<T>>) typeTable.get(c);
	}

	public static void registerJsonType(Class<?> baseClass, Class<? extends JsonValue<?>> jsonType) {
		try {
			if (jsonType.getConstructor() != null)
				typeTable.put(baseClass, jsonType);
			else
				throw new NoSuchMethodException("");
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("The JSON type must have an public Constructor without arguments");
		}
	}

	public static Class<?> getWrapper(Class<?> primitive) {
		Class<?> warp = primitive;
		if (primitive.isPrimitive()) {
			if (primitive == char.class) {
				warp = Character.class;
			}
			else if (primitive == int.class) {
				warp = Integer.class;
			} else {
				String name = primitive.getName();
				try {
					warp = Class.forName(
							"java.lang." + name.substring(0, 1).toUpperCase() + name.substring(1, name.length()));
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		}
		return warp;
	}
	public static ArrayList<Field> getFields(Class<?> cls) {
		ArrayList<Field> fls = new ArrayList<>();
		while(!cls.equals(Object.class)) {
			Field[] thisFields = cls.getDeclaredFields();
			for (int i = 0; i < thisFields.length; i++) {
				Field field = thisFields[i];
				fls.add(field);
			}
			cls = cls.getSuperclass();
		}
		return fls;
	}
	public static ArrayList<Class<?>> getSuperTypes(Class<?> type){
		ArrayList<Class<?>> list = new ArrayList<>();
		while(type != null) {
			list.add(type.getSuperclass());
			type = type.getSuperclass();
		}
		return list;
	}
}
