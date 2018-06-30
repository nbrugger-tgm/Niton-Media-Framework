package com.niton.media.json.types;

import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonNumber Class
 * 
 * @author Nils
 * @version 2018-06-30
 */
public class JsonLong extends JsonValue<Long> {
	public JsonLong() {
	}

	@Override
	public String getJson() {
		return "\"" + getValue().toString() + "\"";
	}

	@Override
	public boolean readNext(StringInputStream sis) {
		boolean worked;
		JsonString s = new JsonString();
		worked = s.readNext(sis);
		try {
			setValue(Long.parseLong(s.getValue()));
		} catch (Exception e) {
			worked = false;
		}
		return worked;
	}
}
