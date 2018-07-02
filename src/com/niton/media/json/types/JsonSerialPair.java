package com.niton.media.json.types;

import java.io.IOException;
import java.lang.reflect.Field;

import com.niton.media.json.JsonSerializer;
import com.niton.media.json.basic.JsonPair;
import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.JsonInputStream;
import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonSerialPair Class
 * 
 * @author Nils
 * @version 2018-06-30
 */
public class JsonSerialPair extends JsonValue<Object> {
	private String name;
	private Class<? extends JsonValue<?>> jsonToParse;

	/**
	 * Creates an Instance of JsonSerialPair.java
	 * 
	 * @author Nils
	 * @version 2018-06-30
	 */
	public JsonSerialPair() {
	}

	public JsonSerialPair(String name, Object value) {
		super(value);
		this.name = name;
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		try {
			Object value = getValue();
			Class<?> type = null;
			JsonValue<?> jsonField;
			if(value != null)
				type = value.getClass();
			if (value == null)
				jsonField = new JsonString("null");
			else if (type.isEnum())
				jsonField = new JsonString(((Enum<?>) value).name());
			else if (type.isArray())
				jsonField = new JsonSerialArray<>((Object[]) value);
			else {
				Class<? extends JsonValue<?>> clazz = JsonSerializer.getJsonFor(type);
				if (clazz == null)
					clazz = JsonSerialObject.class;
				jsonField = clazz.newInstance();
				clazz.getField("o").set(jsonField, value);
			}
			JsonPair<JsonValue<?>> pair = new JsonPair<>(jsonField, name);
			return pair.getJson();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public boolean readNext(StringInputStream sis) throws IOException {
		boolean succ = true;
		JsonString s = new JsonString();
		succ = succ && s.readNext(sis);
		setName(s.getValue());
		while(sis.hasNext()) {
			if(sis.readChar() == ':')
				break;
		}
		JsonValue<?> val;
		try {
			val = getJsonToParse().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return false;
		}
		val.readNext(sis);
		setValue(val.getValue());
		return succ;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the jsonToParse
	 */
	public Class<? extends JsonValue<?>> getJsonToParse() {
		return jsonToParse;
	}

	/**
	 * @param jsonToParse the jsonToParse to set
	 */
	public void setJsonToParse(Class<? extends JsonValue<?>> jsonToParse) {
		this.jsonToParse = jsonToParse;
	}
}
