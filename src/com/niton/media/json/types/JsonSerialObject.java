package com.niton.media.json.types;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.niton.media.json.JsonSerializer;
import com.niton.media.json.JsonType;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonPair;
import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.exceptions.JsonParsingException;
import com.niton.media.json.io.JsonInputStream;
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
//			obj.add("class", new JsonString(type.getName()));
			for (Field field : JsonSerializer.getFields(type)) {
				if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())
						|| Modifier.isTransient(field.getModifiers()))
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
	public void readNext(StringInputStream sis) throws IOException {
		JsonObject obj = new JsonObject();
		obj.readNext(sis);
		Object value;
		try {
			Class<?> outClass = typeToRead;
			if(outClass.isEnum()) {
				JsonEnum jenum = new JsonEnum();
				StringInputStream sis2 = new StringInputStream(obj.getJson());
				sis2.readChar();
				jenum.readNext(sis2);
				setValue(jenum.getValue());
			}
			Objenesis objenesis = new ObjenesisStd(); // or ObjenesisSerializer
			value = objenesis.newInstance(outClass);
			StringInputStream jis;
			for (Field f : JsonSerializer.getFields(outClass)) {
				f.setAccessible(true);
				if (Modifier.isTransient(f.getModifiers()) || Modifier.isStatic(f.getModifiers())
						|| Modifier.isFinal(f.getModifiers()))
					continue;
				JsonValue<?> valueAsPlainJson = obj.get(f.getName());
				jis = new StringInputStream(valueAsPlainJson.getJson());
				jis.readChar();
				AdaptiveJsonValue serialPair = new AdaptiveJsonValue();
				serialPair.readNext(jis);
				Object fieldValue = serialPair.getValue();
				f.set(value, fieldValue);
			}
			setValue(value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new JsonParsingException(e);
		}
	}
	private Class<?> typeToRead ;
	/**
	 * @param typeToRead the typeToRead to set
	 */
	public void setTypeToRead(Class<?> typeToRead) {
		this.typeToRead = typeToRead;
	}
}
