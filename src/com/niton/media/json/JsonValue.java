package com.niton.media.json;

/**
 * This is the JsonValue Class
 * @author Nils
 * @version 2018-06-05
 */
public abstract class JsonValue<T>{
	private T o;
	public JsonValue() {}
	public JsonValue(T value) {
		this.o = value;
	}
	public abstract String getJson();
	public T getValue() {
		return o;
	}
	public void setValue(T value) {
		this.o = value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getJson();
	}
}

