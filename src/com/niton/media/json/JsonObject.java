package com.niton.media.json;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.niton.media.audio.nio.basic.PlayState;

/**
 * This is the JsonObject Class
 * 
 * @author Nils
 * @version 2018-06-05
 */
public class JsonObject extends JsonValue<HashMap<String, JsonValue<?>>> {

	public JsonObject() {
		super(new HashMap<>());
	}

	public JsonObject(Object obj) throws IllegalArgumentException, IllegalAccessException {
		HashMap<String, JsonValue<?>> map = new HashMap<>();
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if ((Modifier.isTransient(f.getModifiers()) && (!obj.getClass().equals(ArrayList.class)))
					|| Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers()))
				continue;
			JsonValue<?> val;
			Object value = f.get(obj);
			if (value == null)
				val = new JsonString("null");
			else if (value instanceof Number)
				val = new JsonNumber(((Number) value).doubleValue());
			else if (value.getClass().equals(Character.class))
				val = new JsonNumber(((Character) value).charValue());
			else if (value.getClass().equals(boolean.class) || value.getClass().equals(Boolean.class))
				val = new JsonString(Boolean.toString((boolean) value));
			else if (value.getClass().equals(String.class))
				val = new JsonString((String) value);
			else if (value.getClass().isArray())
				val = new JsonObjectArray(JsonArray.getArray(value));
			else if (value.getClass().equals(ArrayList.class))
				val = new JsonObjectArray(((ArrayList<?>) value).toArray());
			else if (value.getClass().isEnum())
				val = new JsonString(((Enum<?>) value).name());
			else
				val = new JsonObject(value);
			map.put(f.getName(), val);
		}
		setValue(map);
	}

	public JsonObject(HashMap<String, JsonValue<?>> value) {
		super(value);
	}

	public void add(JsonPair<? extends JsonValue<?>> value) {
		getValue().put(value.getName(), value.getValue());
	}

	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-06-08
	 */
	public void add(String name, JsonValue<?> value) {
		put(name, value);
	}

	public void add(String name, String value) {
		put(name, new JsonString(value));
	}

	public void add(String name, int value) {
		put(name, new JsonNumber(value));
	}

	public void add(String name, Object value) throws IllegalArgumentException, IllegalAccessException {
		put(name, new JsonObject(value));
	}
	public void add(String name,Enum<?> enumerat) {
		put(name, new JsonString(enumerat.name()));
	}
	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-06-08
	 */
	public void put(String name, JsonValue<?> value) {
		getValue().put(name, value);
	}

	public void put(JsonPair<JsonValue<?>> pair) {
		getValue().put(pair.getName(), pair.getValue());
	}

	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-06-08
	 */
	public void replace(String name, JsonValue<?> value) {
		getValue().replace(name, value);
	}

	public void replace(JsonPair<JsonValue<?>> pair) {
		getValue().replace(pair.getName(), pair.getValue());
	}

	public void remove(String name) {
		getValue().remove(name);
	}

	public JsonValue<?> get(String name) {
		return getValue().get(name);
	}

	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-06-08
	 */
	public boolean contains(String name) {
		return getValue().containsKey(name);
	}

	public ArrayList<JsonPair<JsonValue<?>>> getAsArray() {
		ArrayList<JsonPair<JsonValue<?>>> list = new ArrayList<>();
		for (String key : getValue().keySet()) {
			list.add(new JsonPair<JsonValue<?>>(getValue().get(key), key));
		}
		return list;
	}

	/**
	 * @see com.niton.media.json.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append('\n');
		for (String key : getValue().keySet()) {
			JsonPair<JsonValue<?>> pair = new JsonPair<JsonValue<?>>(getValue().get(key), key);
			String[] lines = pair.getJson().split("\n");
			for (String string : lines) {
				builder.append('\t');
				builder.append(string);
				builder.append('\n');
			}
		}
		builder.append('}');
		return builder.toString();
	}

	public void read(Object obj) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if ((Modifier.isTransient(f.getModifiers()) && (!obj.getClass().equals(ArrayList.class)))
					|| Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers()))
				continue;
			Object value = null;
			JsonValue<?> val = get(f.getName());
			if (!f.getType().equals(String.class) && val instanceof JsonString && val.getValue().equals("null"))
				value = null;
			else if (f.getType().equals(ArrayList.class)) {
				@SuppressWarnings("unchecked")
				JsonArray<? extends JsonValue<? extends Object>> array = (JsonArray<? extends JsonValue<? extends Object>>) val;
				ArrayList<Object> list = new ArrayList<>();
				for (JsonValue<? extends Object> jsonValue : array) {
					list.add(jsonValue.getValue());
				}
				value = list;
			} 
			else if(f.getType().equals(Object.class)) {
				JsonObject jsonobject = (JsonObject) val;
				Constructor<?> c = f.getType().getConstructor();
				c.setAccessible(true);
				value = c.newInstance();
				jsonobject.read(value);
			}else if (f.getType().equals(Boolean.class) || f.getType().equals(boolean.class))
				value = Boolean.parseBoolean((String) val.getValue());
			else if (f.getType().isPrimitive() || f.getType().getSuperclass().equals(Number.class)
					|| f.getType().equals(Character.class)) {
				JsonNumber nr = new JsonNumber();
				if (val instanceof JsonNumber)
					nr = (JsonNumber) val;
				else
					nr.setValue(Double.parseDouble((String) val.getValue()));
				Class<?> c = f.getType();
				if (c.isPrimitive())
					c = Array.get(Array.newInstance(c, 1), 0).getClass();
				if (!c.getName().contains("Integer"))
					value = Double.class.getMethod(c.getSimpleName().toLowerCase() + "Value").invoke(nr.getValue());
				else
					value = nr.getValue().intValue();
			}
			else if (f.getType().isEnum()){
				for(Object o : f.getType().getEnumConstants()) {
					if(((Enum<?>) o).name().equals(val.getValue())) {
						value = o;
						break;
					}
				}
			}
			else if (val instanceof JsonString)
				value = val.getValue();
			else if (f.getType().isArray()) {
				@SuppressWarnings("unchecked")
				JsonArray<? extends JsonValue<?>> array = (JsonArray<? extends JsonValue<?>>) val;
				value = Array.newInstance(f.getType().getComponentType(), array.size());
				array.read(value);
			}
			
			else {
				JsonObject jsonobject = (JsonObject) val;
				Objenesis objenesis = new ObjenesisStd(); // or ObjenesisSerializer
				value = objenesis.newInstance(f.getType());
				jsonobject.read(value);
			}
			f.set(obj, value);
		}
	}
}
