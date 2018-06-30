package com.niton.media.json.types;

import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonByte Class
 * @author Nils
 * @version 2018-06-30
 */
public class JsonInt extends JsonValue<Integer> {
	public JsonInt() {

	}

	/**
	 * Creates an Instance of JsonInt.java
	 * @author Nils
	 * @version 2018-06-30
	 * @param value
	 */
	public JsonInt(int value) {
		super(Integer.valueOf(value));
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		return "\""+getValue().toString()+"\"";
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public boolean readNext(StringInputStream sis) {
		JsonString s = new JsonString();
		boolean c = s.readNext(sis);
		setValue(Integer.parseInt(s.getValue()));
		return c;
	}
}

