package examples.json;

import java.io.IOException;

import com.niton.media.json.basic.JsonArray;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonPair;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.JsonInputStream;
import com.niton.media.json.io.StringInputStream;

public class ReadJson {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		String someJson = "{\r\n" +
				"    \"success\": \"true\",\r\n" +
				"    \"data\": [\r\n" +
				"        {\r\n" +
				"            \"username\": \"dss\",\r\n" +
				"            \"password\": \"12545\",\r\n" +
				"            \"email\": \"ss@sd.com\",\r\n" +
				"            \"name\": \"s\",\r\n" +
				"            \"gender\": \"w\",\r\n" +
				"            \"mobile\": \"123456789\"\r\n" +
				"        }\r\n" +
				"    ],\r\n" +
				"    \"msg\": \"Thanks for register\"\r\n" +
				"}";
		JsonInputStream jis = new JsonInputStream(new  StringInputStream(someJson));
		JsonObject obj = jis.readNextJsonObject();
		System.out.println("MSG : "+obj.get("msg").getValue());
		JsonObject inner = ((JsonArray<JsonObject>)obj.get("data")).get(0);
		for(JsonPair<JsonValue<?>> s : inner.getAsArray()) {
			System.out.println(s.getName()+" = "+s.getValue());
		}
	}

}
