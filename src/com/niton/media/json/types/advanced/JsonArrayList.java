package com.niton.media.json.types.advanced;

import java.io.IOException;
import java.util.ArrayList;

import com.niton.media.json.JsonSerializer;
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
		JsonObject obj = new JsonObject();
		if(getValue().size() == 0)
			return obj.toString();
		Class<?> highestSuperType = getValue().get(0).getClass();
		for (Object o : getValue()) {
			Class<?> type = o.getClass();
			if(!JsonSerializer.getSuperTypes(type).contains(highestSuperType)) {
				highestSuperType = type;
			}
		}
		obj.add("elementType", highestSuperType.getName());
		JsonSerialArray<?> array = new JsonSerialArray<>();
		array.setValue(getValue().toArray());
		obj.add("elements",array);
		return obj.getJson();
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public boolean readNext(StringInputStream sis) throws IOException {
		return false;
	}
}

