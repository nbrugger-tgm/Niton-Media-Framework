package com.niton.media.json;

import java.util.ArrayList;

/**
 * This is the JsonObjectArray Class
 * @author Nils
 * @version 2018-06-09
 */
public class JsonObjectArray extends JsonArray<JsonValue<?>> {
	public JsonObjectArray() {
		super();
	}
	public JsonObjectArray(Object[] array) throws IllegalArgumentException, IllegalAccessException {
		ArrayList<JsonValue<?>> list = new  ArrayList<>();
		for(Object o : array)
			if(o == null) {
				list.add(new JsonString("null"));
			}else if (o instanceof Number) {
				double nr = ((Number) o).doubleValue();
				list.add(new JsonNumber(nr));
			}
			else if( o instanceof Character)
				list.add(new JsonNumber(((Character) o).charValue()));
			else if (o instanceof String)
				list.add(new JsonString((String) o));
			else if (o.getClass().isArray())
				list.add(new JsonObjectArray(getArray(o)));
			else if (o.getClass().isEnum())
				list.add(new JsonString(((Enum<?>) o).name()));
			else
				list.add(new JsonObject(o));
		setValue(list);
	}
}

