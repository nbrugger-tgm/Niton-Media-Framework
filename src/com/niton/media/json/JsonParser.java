package com.niton.media.json;

import java.util.ArrayList;

/**
 * This is the JsonParser Class
 * 
 * @author Nils
 * @version 2018-06-06
 */
public class JsonParser {
	public static JsonNumber parseNextInt(StringInputStream text) {
		JsonNumber i = new JsonNumber();
		i.setValue(Double.valueOf(Double.parseDouble(parseNextString(text).getValue())));
		return i;
	}

	public static JsonString parseNextString(StringInputStream text) {
		JsonString string = new JsonString();
		StringBuilder builder = new StringBuilder();
		boolean escape = false;
		while(text.hasNext()) {
			char c = text.read();
			if(escape) {
				if(c == '\\' || c == '\"' || c == '/')
					builder.append(c);
				if(c == 'b')
					builder.append('\b');
				if(c == 'f')
					builder.append('\f');
				if(c == 'n')
					builder.append('\n');
				if(c == 'r')
					builder.append('\r');
				if(c == 't')
					builder.append('\t');
				escape = false;
			}else {
				escape = c == '\\';
				if(!escape)
					if(c == JsonType.STRING.getCloseToken()){
						string.setValue(builder.toString());
						return string;
					}
					else
						builder.append(c);
			}
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	public static <T extends JsonValue<?>> JsonArray<T> parseNextArray(StringInputStream text) {
		JsonArray<T> array = new JsonArray<>();
		JsonValue<?> lastOBJ = null;
		while(text.hasNext()) {
			char c = text.read();
			if(c == ',') {
				array.add((T) lastOBJ);
				lastOBJ = null;
				continue;
			}
			if(c == JsonType.ARRAY.getCloseToken()) {
				if(lastOBJ != null)
					array.add((T) lastOBJ);
				return array;
			}else
				if(lastOBJ == null) {
					for(JsonType type : JsonType.values()) {
						if(type.getOpenToken() == c)
							switch (type) {
							case ARRAY:
								lastOBJ = parseNextArray(text);
								break;
							case OBJECT:
								lastOBJ = parseNextObject(text);
								break;
							case PAIR:
								lastOBJ = parseNextPair(text);
								break;
							case STRING:
								lastOBJ = parseNextString(text);
								break;
							}
					}
				}
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	public static <T extends JsonValue<?>> JsonPair<T> parseNextPair(StringInputStream text) {
		JsonPair<T> pair = new JsonPair<>();
		while(text.hasNext()) {
			char c = text.read();
			if(c == JsonType.PAIR.getCloseToken())
				return pair;
			else
				pair.setValue((T) parseNextJson(text));
		}
		return pair;
	}

	public static JsonObject parseNextObject(StringInputStream text) {
		JsonObject object = new JsonObject();
		JsonString lastName = null;
		while(text.hasNext()) {
			char c = text.read();
			if(lastName == null) {
				if(c == JsonType.STRING.getOpenToken())
					lastName = parseNextString(text);
			}else if (c == JsonType.PAIR.getOpenToken()) {
				JsonPair<?> pair = parseNextPair(text);
				pair.setName(lastName.getValue());
				object.add(pair);
				lastName = null;
			}
			if(c == JsonType.OBJECT.getCloseToken()) {
				return object;
			}
		}
		return object;
	}

	public static JsonValue<?> parseNextJson(StringInputStream text) {
		JsonValue<?> val = null;
		wh: while(text.hasNext()) {
			char c = text.read();
			for(JsonType type : JsonType.values()) {
				if(type.getOpenToken() == c)
					switch (type) {
					case ARRAY:
						val = parseNextArray(text);
						break wh;
					case OBJECT:
						val = parseNextObject(text);
						break wh;
					case PAIR:
						val = parseNextPair(text);
						break wh;
					case STRING:
						val = parseNextString(text);
						break wh;
					}
			}
		}
		return val;
	}
	
	public static JsonType getLast(ArrayList<JsonType> array) {
		return array.get(array.size()-1);
	}
	
	public static void main(String[] args) {
//		JsonObject obj = new JsonObject();
//		JsonPair<JsonString> name = new JsonPair<JsonString>(new JsonString("mein name"), "name");
//		JsonPair<JsonString> name1 = new JsonPair<JsonString>(new JsonString("asd"), "name2");
//		JsonPair<JsonString> name2 = new JsonPair<JsonString>(new JsonString("sdf"), "name3");
//		JsonPair<JsonString> name3 = new JsonPair<JsonString>(new JsonString("dfg"), "name4");
//		JsonPair<JsonArray<JsonString>> members = new JsonPair<>();
//		JsonArray<JsonString> array = new JsonArray<>();
//		array.add(new JsonString("nils \n \t \""));
//		array.add(new JsonString("alex"));
//		array.add(new JsonString("max"));
//		array.add(new JsonString("tobi"));
//		members.setName("members");
//		members.setValue(array);
//		obj.add(name);
//		obj.add(name1);
//		obj.add(name2);
//		obj.add(name3);
//		obj.add(members);
		String out = "[\n" + 
				"	{\n" + 
				"		\"day\" : \"MO\",\n" + 
				"		\"lessons\" : [\n" + 
				"			\"SIX\",\n" + 
				"			\"SEVEN\",\n" + 
				"			\"EIGHT\"\n" + 
				"		],\r\n" + 
				"	},\n" + 
				"	{\n" + 
				"		\"day\" : \"SA\",\n" + 
				"		\"lessons\" : [\n" + 
				"		],\r\n" + 
				"	},\n" + 
				"	{\n" + 
				"		\"day\" : \"SO\",\n" + 
				"		\"lessons\" : [\n" + 
				"		],\n" + 
				"	},\n" + 
				"	{\n" + 
				"		\"day\" : \"DI\",\n" + 
				"		\"lessons\" : [\n" + 
				"		],\n" + 
				"	},\n" + 
				"	{\n" + 
				"		\"day\" : \"DO\",\n" + 
				"		\"lessons\" : [\n" + 
				"		],\n" + 
				"	},\n" + 
				"	{\n" + 
				"		\"day\" : \"FR\",\n" + 
				"		\"lessons\" : [\n" + 
				"			\"SIX\",\n" + 
				"			\"SEVEN\",\n" + 
				"			\"EIGHT\"\n" + 
				"		],\n" + 
				"	},\n" + 
				"	{\n" + 
				"		\"day\" : \"MI\",\n" + 
				"		\"lessons\" : [\n" + 
				"			\"SIX\",\n" + 
				"			\"SEVEN\",\n" + 
				"			\"EIGHT\"\n" + 
				"		],\n" + 
				"	},\n" + 
				"]";
		System.out.println(out);
		StringInputStream sis = new StringInputStream(out);
		@SuppressWarnings("unchecked")
		JsonArray<JsonObject> data = (JsonArray<JsonObject>) parseNextJson(sis);
		System.out.println(data.getJson());
	}
}
