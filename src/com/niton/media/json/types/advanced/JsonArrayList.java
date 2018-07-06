package com.niton.media.json.types.advanced;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.niton.media.json.JsonSerializer;
import com.niton.media.json.basic.JsonArray;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;
import com.niton.media.json.types.JsonSerialArray;
import com.niton.media.json.types.JsonSerialObject;

/**
 * This is the JsonArrayList Class
 * @author Nils
 * @version 2018-07-01
 */
public class JsonArrayList<T> extends JsonValue<ArrayList<T>> {
	public JsonArrayList() {

	}
	public JsonArrayList(ArrayList<T> value) {
		super(value);
	}
	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		JsonSerialArray<? extends T> array = new JsonSerialArray<>();
		array.setValue(getValue().toArray());
		return array.getJson();
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		JsonSerialArray<? extends T> array = new JsonSerialArray<>();
		array.setToRead(Object.class);
		array.readNext(sis);
		int size = Array.getLength(array.getValue());
		ArrayList<T> toSet = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			toSet.add((T) Array.get(array.getValue(), i));
		}
		setValue(toSet);
	}
}

