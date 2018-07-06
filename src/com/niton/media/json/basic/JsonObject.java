package com.niton.media.json.basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.niton.media.json.JsonType;
import com.niton.media.json.exceptions.GenerateJsonException;
import com.niton.media.json.io.StringInputStream;
import com.niton.media.json.types.JsonSerialObject;

/**
 * This is the JsonObject Class
 * 
 * @author Nils
 * @version 2018-06-05
 */
public class JsonObject extends JsonValue<HashMap<String, JsonValue<?>>> {

	public JsonObject() {
		super(new HashMap<>());
	}

	public JsonObject(HashMap<String, JsonValue<?>> value) {
		super(value);
	}

	public void add(JsonPair<? extends JsonValue<?>> value) {
		getValue().put(value.getName(), value.getValue());
	}

	public void add(String name, JsonValue<?> value) {
		put(name, value);
	}

	public void add(String name, String value) {
		put(name, new JsonString(value));
	}

	public void add(String name, Object value) throws IllegalArgumentException, IllegalAccessException {
		put(name, new JsonSerialObject(value));
	}

	public void add(String name, Enum<?> enumerat) {
		put(name, new JsonString(enumerat.name()));
	}

	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-06-08
	 */
	public void put(String name, JsonValue<?> value) {
		getValue().put(name, value);
	}

	public void put(JsonPair<JsonValue<?>> pair) {
		getValue().put(pair.getName(), pair.getValue());
	}

	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-06-08
	 */
	public void replace(String name, JsonValue<?> value) {
		getValue().replace(name, value);
	}

	public void replace(JsonPair<JsonValue<?>> pair) {
		getValue().replace(pair.getName(), pair.getValue());
	}

	public void remove(String name) {
		getValue().remove(name);
	}

	public JsonValue<?> get(String name) {
		return getValue().get(name);
	}

	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-06-08
	 */
	public boolean contains(String name) {
		return getValue().containsKey(name);
	}

	public ArrayList<JsonPair<JsonValue<?>>> getAsArray() {
		ArrayList<JsonPair<JsonValue<?>>> list = new ArrayList<>();
		for (String key : getValue().keySet()) {
			list.add(new JsonPair<JsonValue<?>>(getValue().get(key), key));
		}
		return list;
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append('{');
			builder.append('\n');
			int i = 0;
			for (String key : getValue().keySet()) {
				i++;
				JsonPair<JsonValue<?>> pair = new JsonPair<JsonValue<?>>(getValue().get(key), key);
				String[] lines = pair.getJson().split("\n");
				for (String string : lines) {
					builder.append('\t');
					builder.append(string);
					if (i != getValue().size() && lines[lines.length - 1].equals(string))
						builder.append(',');
					builder.append('\n');
				}
			}
			builder.append('}');
			return builder.toString();
		} catch (Exception e) {
			throw new GenerateJsonException(e);
		}
	}

	/**
	 * @throws IOException
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		JsonPair<?> lastPair = null;
		while (sis.hasNext()) {
			char c = sis.readChar();
			if (c == JsonType.OBJECT.getCloseToken()) {
				if (lastPair != null)
					add(lastPair);
				return;
			} else if (c == JsonType.STRING.getOpenToken()) {
				lastPair = new JsonPair<>();
				lastPair.readNext(sis);
			} else if (c == ',') {
				add(lastPair);
			}
		}
	}
}
