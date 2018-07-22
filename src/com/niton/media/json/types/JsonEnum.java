package com.niton.media.json.types;

import java.io.IOException;

import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonString;
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
		return new JsonString(getValue().name()).getJson();
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		JsonString obj = new JsonString();
		obj.readNext(sis);
		Enum<?>[] consts = type.getEnumConstants();
		for (Enum<?> enum1 : consts) {
			if(enum1.name().equals(obj.getValue())){
				setValue(enum1);
				break;
			}
		}
	}
	Class<? extends Enum<?>> type; 
	public void setToRead(Class<? extends Enum<?>> toParse) {
		type = toParse;
	}
}

