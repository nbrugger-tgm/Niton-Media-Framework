package com.niton.media.json.types;

import java.io.IOException;

import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonByte Class
 * @author Nils
 * @version 2018-06-30
 */
public class JsonDouble extends JsonValue<Double> {
	public JsonDouble() {
	}

	/**
	 * Creates an Instance of JsonDouble.java
	 * @author Nils
	 * @version 2018-06-30
	 * @param d
	 */
	public JsonDouble(double d) {
		super(Double.valueOf(d));
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		JsonLong l = new JsonLong();
		l.setValue(Double.doubleToLongBits(getValue().doubleValue()));
		return l.getJson();
	}

	/**
	 * @throws IOException 
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		JsonLong s = new JsonLong();
		s.readNext(sis);
		setValue(Double.longBitsToDouble(s.getValue().longValue()));
	}
}

