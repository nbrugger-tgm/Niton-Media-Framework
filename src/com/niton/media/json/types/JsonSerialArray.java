package com.niton.media.json.types;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.niton.media.json.JsonSerializer;
import com.niton.media.json.JsonType;
import com.niton.media.json.basic.JsonArray;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;
import com.niton.media.json.types.advanced.AdaptiveJsonValue;

/**
 * This is the JsonSerialArray Class
 * 
 * @author Nils
 * @version 2018-06-30
 */
public class JsonSerialArray<T> extends JsonValue<Object> {
	public JsonSerialArray() {
	}

	public JsonSerialArray(T[] array) {
		setValue(array);
	}

	@SuppressWarnings("unchecked")
	public JsonSerialArray(ArrayList<T> array) {
		setValue(array.toArray());
	}

	private enum PARSE {
		ENUM, ARRAY, OBJECT;
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getJson() {
		JsonArray<JsonValue<?>> jsArray = new JsonArray<>();
		T[] array = (T[]) getValue();
		for (T t : array) {
			jsArray.add(new AdaptiveJsonValue(t));
		}
		return jsArray.getJson();

	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		ArrayList<Object> collector = new ArrayList<>();
		AdaptiveJsonValue last = null;
		while (sis.hasNext()) {
			char c = sis.readChar();
			if (c == JsonType.ARRAY.getCloseToken()) {
				if (last != null) {
					collector.add(last.getValue());
				}
				Object array = Array.newInstance(toRead, collector.size());
				for (int i = 0; i < collector.size(); i++) {
					Array.set(array, i, collector.get(i));
				}
				if (toRead.isPrimitive())
					setValue(JsonSerializer.toPrimitiveArray(array, toRead));
				else
					setValue(array);
			} else if (c == ',') {
				collector.add(last.getValue());
			} else {
				for (JsonType t : JsonType.values()) {
					if (t.getOpenToken() == c) {
						last = new AdaptiveJsonValue();
						last.readNext(sis);
						break;
					}
				}

			}
		}
	}

	public final static Object[] getArray(Object val) {
		if (val instanceof Object[])
			return (Object[]) val;
		int arrlength = Array.getLength(val);
		Object[] outputArray = new Object[arrlength];
		for (int i = 0; i < arrlength; ++i) {
			outputArray[i] = Array.get(val, i);
		}
		return outputArray;
	}

	private Class<?> toRead;

	/**
	 * @param toRead the toRead to set
	 */
	public void setToRead(Class<?> toRead) {
		this.toRead = toRead;
	}
}
