package com.niton.media.json.basic;

import java.io.IOException;

import com.niton.media.json.io.JsonInputStream;
import com.niton.media.json.io.StringInputStream;

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
		return builder.toString();
	}
	/**
	 * @throws IOException 
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		JsonString s = new JsonString();
		s.readNext(sis);
		setName(s.getValue());
		while(sis.hasNext()) {
			if(sis.readChar() == ':')
				break;
		}
		JsonInputStream jsin = new JsonInputStream(sis);
		T t = (T) jsin.readNextJson();
		setValue(t);
	}
}

