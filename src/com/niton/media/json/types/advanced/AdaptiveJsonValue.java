package com.niton.media.json.types.advanced;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.niton.media.json.JsonSerializer;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;
import com.niton.media.json.types.JsonEnum;
import com.niton.media.json.types.JsonSerialArray;
import com.niton.media.json.types.JsonSerialObject;

/**
 * This is the AdaptiveJsonValue Class
 * 
 * @author Nils
 * @version 2018-07-02
 */
public class AdaptiveJsonValue extends JsonValue<Object> {
	public AdaptiveJsonValue() {

	}

	public AdaptiveJsonValue(Object value) {
		super(value);
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		try {
			Object val = getValue();
			Class<?> type;
			if (val != null)
				type = val.getClass();
			else
				return new JsonString("null").getJson();
			JsonValue obj;
			Class<?> parserfieldType = JsonSerializer.getWrapper(type);
			if (type.isEnum())
				obj = new JsonEnum((Enum<?>) val);
			else if (type.isArray()) {
				if (type.getComponentType().isPrimitive()) {
					Object[] warpped = (Object[]) Array.newInstance(
							JsonSerializer.getWrapper(type.getComponentType()), Array.getLength(val));
					for (int i = 0; i < warpped.length; i++) {
						warpped[i] = Array.get(val, i);
					}
					val = warpped;
				}
				obj = new JsonSerialArray<>((Object[]) val);
			} else {
				Class<? extends JsonValue<?>> clazz = JsonSerializer.getJsonFor(type);
				if (clazz == null) {
					JsonSerialObject serialObject = new JsonSerialObject();
					serialObject.setValue(val);
					obj = serialObject;
				} else {
					obj = clazz.newInstance();
					obj.setValue(val);
				}
			}

			return obj.getJson();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Generating JSON of object failed");
		}
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public boolean readNext(StringInputStream sis) throws IOException {
		return false;
	}
}
