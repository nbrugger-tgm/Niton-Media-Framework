package com.niton.media.json.types;

import java.io.IOException;

import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.exceptions.JsonParsingException;
import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonEnum Class
 * @author Nils
 * @version 2018-07-02
 */
public class JsonEnum extends JsonValue<Enum<?>> {
	public JsonEnum() {
	}

	public JsonEnum(Enum<?> value) {
		super(value);
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		JsonObject obj = new JsonObject();
		obj.add("class", getValue().getClass().getName());
		obj.add("name", getValue().name());
		return obj.getJson();
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		JsonObject obj = new JsonObject();
		obj.readNext(sis);
		try {
			@SuppressWarnings("unchecked")
			Class<? extends Enum<?>> enume = (Class<? extends Enum<?>>) Class.forName((String) obj.get("class").getValue());
			Enum<?>[] consts = enume.getEnumConstants();
			for (Enum<?> enum1 : consts) {
				if(enum1.name().equals(obj.get("name").getValue().toString())){
					setValue(enum1);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new JsonParsingException(e);
		}
	}
}

