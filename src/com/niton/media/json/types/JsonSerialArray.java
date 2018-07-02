package com.niton.media.json.types;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.niton.media.json.JsonSerializer;
import com.niton.media.json.JsonType;
import com.niton.media.json.basic.JsonArray;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;
import com.niton.media.json.types.advanced.AdaptiveJsonValue;

/**
 * This is the JsonSerialArray Class
 * 
 * @author Nils
 * @version 2018-06-30
 */
public class JsonSerialArray<T> extends JsonValue<Object[]> {
	public JsonSerialArray() {
	}

	public JsonSerialArray(T[] array) {
		setValue(array);
	}

	@SuppressWarnings("unchecked")
	public JsonSerialArray(ArrayList<T> array) {
		setValue((T[]) array.toArray());
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
	public boolean readNext(StringInputStream sis) throws IOException {
		try {
			ArrayList<T> collector = new ArrayList<>();
			@SuppressWarnings("unchecked")
			T[] array = (T[]) Array.newInstance(Object.class, 1);
			@SuppressWarnings("unchecked")
			Class<T> component = (Class<T>) array.getClass().getComponentType();
			Class<? extends JsonValue<T>> jsonComponent = JsonSerializer.getJsonFor(component);

			boolean suc = true;
			JsonValue<T> last = jsonComponent.newInstance();
			while (sis.hasNext()) {
				char c = sis.readChar();
				if (c == JsonType.ARRAY.getCloseToken()) {
					if (last != null)
						collector.add(last.getValue());
					break;
				} else if (c == ',') {
					collector.add(last.getValue());
				} else {
					last = jsonComponent.newInstance();

					suc = suc && last.readNext(sis);
				}
			}
			return suc;
		} catch (InstantiationException | IllegalAccessException e) {
			return false;
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
}
