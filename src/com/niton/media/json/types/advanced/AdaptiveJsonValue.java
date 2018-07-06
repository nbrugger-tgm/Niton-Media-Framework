package com.niton.media.json.types.advanced;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.niton.media.json.JsonSerializer;
import com.niton.media.json.JsonType;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.exceptions.GenerateJsonException;
import com.niton.media.json.exceptions.JsonEscapeException;
import com.niton.media.json.exceptions.JsonParsingException;
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
			JsonObject complete = new JsonObject();
			complete.add("type", type.getName());
			JsonValue obj;
			Class<?> parserfieldType = JsonSerializer.getWrapper(type);
			if (type.isEnum())
				obj = new JsonEnum((Enum<?>) val);
			else if (type.isArray()) {
				if (type.getComponentType().isPrimitive()) {
					Object[] warpped = (Object[]) Array.newInstance(JsonSerializer.getWrapper(type.getComponentType()),
							Array.getLength(val));
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
			complete.add("value", obj);
			return complete.getJson();
		} catch (Exception e) {
			throw new GenerateJsonException(e);
		}
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		JsonString string = new JsonString();
		try {
			string.readNext(sis);
		} catch (JsonEscapeException e) {
//			e.printStackTrace();
		}
		if (string.getValue() != null)
			if (string.getValue().equals("null")) {
				setValue(null);
				return;
			} else {
				StringInputStream sis2 = new StringInputStream(string.getJson());
				sis2.readChar();
				sis.setPreStream(sis2);
			}
		JsonObject jos = new JsonObject();
		jos.readNext(sis);
		try {
			toParse = Class.forName(jos.get("type").getValue().toString());
		} catch (ClassNotFoundException e) {
			throw new JsonParsingException(e);
		}
		jos.remove("type");
		sis = new StringInputStream(jos.get("value").getJson());
		wh: while (sis.hasNext()) {
			char c = sis.readChar();
			for (JsonType t : JsonType.values()) {
				if (t.getOpenToken() == c)
					break wh;
			}
		}
		if (toParse.isEnum()) {
			JsonEnum jenum = new JsonEnum();
			jenum.readNext(sis);
			setValue(jenum.getValue());
		} else if (toParse.isArray()) {
			JsonSerialArray<?> jarray = new JsonSerialArray<>();
			jarray.setToRead(toParse.getComponentType());
			jarray.readNext(sis);
			setValue(jarray.getValue());
		} else {
			try {
				Class<? extends JsonValue<?>> parser = JsonSerializer.getJsonFor(toParse);
				if (parser == null)
					parser = JsonSerialObject.class;
				JsonValue<?> someValue = parser.newInstance();
				if (parser == JsonSerialObject.class)
					((JsonSerialObject) someValue).setTypeToRead(toParse);
				someValue.readNext(sis);
				setValue(someValue.getValue());
			} catch (InstantiationException | IllegalAccessException e) {
				throw new JsonParsingException(e);
			}
		}
	}

	private Class<?> toParse;
}
