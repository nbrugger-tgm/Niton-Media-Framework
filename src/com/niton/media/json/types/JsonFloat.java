package com.niton.media.json.types;

import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonFloat Class
 * @author Nils
 * @version 2018-06-30
 */
public class JsonFloat extends JsonValue<Float>{
	public JsonFloat() {}
	@Override
	public String getJson() {
		return new JsonString(getValue().toString()).getJson();
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public boolean readNext(StringInputStream sis) {
		JsonString s = new JsonString();
		boolean c = s.readNext(sis);
		setValue(Float.parseFloat(s.getValue()));
		return c;
	}
}

