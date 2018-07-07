package com.niton.media.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
import com.niton.media.json.types.advanced.JsonHashMap;


/**
 * This is the JsonSerializer Class
 * 
 * @author Nils
 * @version 2018-06-29
 */
@SuppressWarnings("unchecked")
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
		registerJsonType(HashMap.class, (Class<? extends JsonValue<?>>) JsonHashMap.class);
	}

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
//		type = type.getSuperclass();
		while(type != null) {
			list.add(type);
			type = type.getSuperclass();
		}
		return list;
	}
	
	public static Class<?> getHigtestSuperType(Class<?>... types){
		ArrayList<ArrayList<Class<?>>> supertypes = new ArrayList<>();
		for (int i = 0; i < types.length; i++) {
			supertypes.add(getSuperTypes(types[i]));
		}
		Class<?> higestType = null;
		for (int i = 0;i<supertypes.get(0).size();i++) {
			ArrayList<Class<?>> ebene = new ArrayList<>();
			for (ArrayList<Class<?>> arrayList : supertypes) {
				if(arrayList.size() == i)
					return higestType;
				ebene.add(arrayList.get(arrayList.size()-1-i));
			}
			boolean allEqual = true;
			for (int j = 1;j<supertypes.size();j++) {
				allEqual = allEqual && ebene.get(j).equals(ebene.get(j-1));
			}
			if(allEqual)
				higestType = ebene.get(0);
			else
				break;
		}
		return higestType;
	}

	/**
	 * Description : 
	 * @author Nils
	 * @version 2018-07-04
	 * @param value
	 * @return
	 */
	public static Object toPrimitiveArray(Object value,Class<?> primitive) {
		Class<?> wrapper = getWrapper(primitive);
		Object simpleArray = Array.newInstance(primitive, Array.getLength(value));
		for(int i = 0;i<Array.getLength(value);i++) {
			try {
				Array.set(simpleArray, i, wrapper.getMethod(primitive.getName()+"Value").invoke(Array.get(value, i)));
			} catch (ArrayIndexOutOfBoundsException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return simpleArray;
	}
}
