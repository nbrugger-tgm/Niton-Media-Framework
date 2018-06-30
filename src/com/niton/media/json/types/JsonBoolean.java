package com.niton.media.json.types;

import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonBoolean Class
 * @author Nils
 * @version 2018-06-30
 */
public class JsonBoolean extends JsonValue<Boolean> {
	public JsonBoolean() {
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		return "\""+getValue()+"\"";
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public boolean readNext(StringInputStream sis) {
		JsonString s = new JsonString();
		boolean c = s.readNext(sis);
		setValue(Boolean.parseBoolean(s.getValue()));
		return c;
	}
}

