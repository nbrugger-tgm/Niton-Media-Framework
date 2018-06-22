package com.niton.media.json;

/**
 * This is the JsonInt Class
 * @author Nils
 * @version 2018-06-05
 */
public class JsonNumber extends JsonValue<Double> {
	/**
	 * Creates an Instance of JsonInt.java
	 * @author Nils
	 * @version 2018-06-05
	 */
	public JsonNumber(double value) {
		super(value);
	}
	public JsonNumber() {
		super();
	}
	
	
	/**
	 * @see com.niton.media.json.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		return "\""+Double.toString(getValue())+"\"";
	}
}

