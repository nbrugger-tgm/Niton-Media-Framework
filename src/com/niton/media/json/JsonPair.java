package com.niton.media.json;

/**
 * This is the JsonPair Class
 * @author Nils
 * @version 2018-06-06
 */
public class JsonPair<T extends JsonValue<?>> extends JsonValue<T> {
	private String name;
	public JsonPair(T value, String name) {
		super(value);
		this.name = name;
	}
	/**
	 * Creates an Instance of JsonPair.java
	 * @author Nils
	 * @version 2018-06-06
	 */
	public JsonPair() {
		super(null);
		name = new String();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String getJson() {
		StringBuilder builder = new StringBuilder();
		JsonString string = new JsonString(name);
		builder.append(string.getJson());
		builder.append(" : ");
		builder.append(getValue().getJson());
		builder.append(',');
		return builder.toString();
	}
}

