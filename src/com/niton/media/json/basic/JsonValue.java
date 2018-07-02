package com.niton.media.json.basic;

import java.io.IOException;

import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonValue Class
 * @author Nils
 * @version 2018-06-05
 */
public abstract class JsonValue<T>{
	private Object o;
	public JsonValue() {}
	public JsonValue(T value) {
		this.o = value;
	}
	public abstract String getJson();
	public T getValue() {
		return (T) o;
	}
	public abstract boolean readNext(StringInputStream sis) throws IOException;
	public void setValue(T t) {
		this.o = t;
	}
	@Override
	public String toString() {
		return getJson();
	}
}

