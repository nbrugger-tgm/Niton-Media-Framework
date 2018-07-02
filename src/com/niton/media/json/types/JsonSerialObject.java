package com.niton.media.json.types;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.niton.media.json.JsonSerializer;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonPair;
import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;
import com.niton.media.json.types.advanced.AdaptiveJsonValue;

/**
 * This is the JsonSerialObject Class
 * 
 * @author Nils
 * @version 2018-06-30
 */
public class JsonSerialObject extends JsonValue<Object> {
	public JsonSerialObject() {
	}

	/**
	 * Creates an Instance of JsonSerialObject.java
	 * 
	 * @author Nils
	 * @version 2018-06-30
	 * @param value
	 */
	public JsonSerialObject(Object value) {
		setValue(value);
	}

	/**
	 * @throws InstantiationException 
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		try {
			Object val = getValue();
			Class<?> type = val.getClass();
			JsonObject obj = new JsonObject();
			obj.add("class", new JsonString(type.getName()));
			for (Field field : JsonSerializer.getFields(type)) {
				if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) ||Modifier.isTransient(field.getModifiers()))
					continue;
				field.setAccessible(true);
				obj.add(field.getName(), new AdaptiveJsonValue(field.get(val)));
			}
			return obj.getJson();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Generating JSON of object failed");
		}
	}

	/**
	 * @throws IOException
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public boolean readNext(StringInputStream sis) throws IOException {
		boolean success = true;
		JsonObject obj = new JsonObject();
		success = success && obj.readNext(sis);
		Object value;
		try {
			Class<?> outClass = Class.forName((String) obj.get("class").getValue());
			Objenesis objenesis = new ObjenesisStd(); // or ObjenesisSerializer
			value = objenesis.newInstance(outClass);
			for (Field f : outClass.getDeclaredFields()) {
				f.setAccessible(true);
				if (Modifier.isTransient(f.getModifiers()) || Modifier.isStatic(f.getModifiers())
						|| Modifier.isFinal(f.getModifiers()))
					continue;
				Class<?> fieldClass = f.getType();
				JsonPair<JsonValue<?>> orig = new JsonPair<JsonValue<?>>(obj.get(f.getName()), f.getName());
				JsonSerialPair serialPair = new JsonSerialPair();
				serialPair.setJsonToParse(JsonSerializer.getJsonFor(fieldClass));
				serialPair.readNext(new StringInputStream(orig.getJson()));
				f.set(value, serialPair.getValue());
			}
			setValue(value);
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
		return success;
	}
}
