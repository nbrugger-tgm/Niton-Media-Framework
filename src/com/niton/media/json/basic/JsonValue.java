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
	public abstract void readNext(StringInputStream sis) throws IOException;
	public void setValue(T t) {
		this.o = t;
	}
	@Override
	public String toString() {
		return getJson();
	}
	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		JsonValue<T> newJson;
		try {
			newJson = getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
		newJson.o = this.o;
		return newJson;
	}
}

