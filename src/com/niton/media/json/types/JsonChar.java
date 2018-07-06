package com.niton.media.json.types;

import java.io.IOException;

import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonChar Class
 * 
 * @author Nils
 * @version 2018-06-30
 */
public class JsonChar extends JsonValue<Character> {
	public JsonChar() {
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		return new JsonString(getValue().toString()).getJson();
	}

	/**
	 * @throws IOException 
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		JsonString s = new JsonString();
		s.readNext(sis);
		setValue(s.getValue().charAt(0));
	}
}
