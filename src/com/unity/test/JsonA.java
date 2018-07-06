package com.unity.test;

import java.io.IOException;

import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonA Class
 * @author Nils
 * @version 2018-07-05
 */
public class JsonA extends JsonValue<A> {
	public JsonA() {

	}

	/**
	 * Creates an Instance of JsonA.java
	 * @author Nils
	 * @version 2018-07-05
	 * @param a
	 */
	public JsonA(A a) {
		setValue(a);
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		JsonString s = new JsonString();
		s.setValue(getValue().a+"-"+getValue().b);
		return s.getJson();
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		JsonString s = new JsonString();
		s.readNext(sis);
		A a = new A();
		a.a = s.getValue().split("-")[0].charAt(0);
		a.b = s.getValue().split("-")[1].charAt(0); 
		setValue(a);
	}
}

