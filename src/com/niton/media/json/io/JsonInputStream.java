package com.niton.media.json.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.niton.media.filesystem.NFile;
import com.niton.media.json.JsonType;
import com.niton.media.json.basic.JsonArray;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonString;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.types.advanced.AdaptiveJsonValue;

/**
 * This is the JsonInputStream Class
 * @author Nils
 * @version 2018-06-30
 */
public class JsonInputStream {
	private StringInputStream sis;
	
	public JsonInputStream(StringInputStream sis) {
		super();
		this.sis = sis;
	}
	public JsonInputStream(InputStream source) throws UnsupportedEncodingException {
		this(new StringInputStream(source));
	}
	
	public JsonInputStream(NFile source) throws UnsupportedEncodingException, FileNotFoundException {
		this(new StringInputStream(source));
	}
	public JsonString readNextString() throws IOException {
		JsonString object = new JsonString();
		while(sis.hasNext()) 
			if(sis.readChar() == JsonType.STRING.getOpenToken()) {
				object.readNext(sis);
				break;
			}
		return object;
	}
	
	public <T extends JsonValue<?>> JsonArray<T> readNextArray() throws IOException {
		JsonArray<T> array = new JsonArray<>();
		while(sis.hasNext()) {
			char c = sis.readChar();
			if(c == JsonType.ARRAY.getOpenToken()) {
				array.readNext(sis);
				return array;
			}
		}
		return array;
	}

	public JsonObject readNextJsonObject() throws IOException {
		JsonObject object = new JsonObject();
		while(sis.hasNext()) 
			if(sis.readChar() == JsonType.OBJECT.getOpenToken()) {
				object.readNext(sis);
				break;
			}
		return object;
	}
	
	public JsonValue<?> readNextJson() throws IOException {
		JsonValue<?> val = null;
		wh: while(sis.hasNext()) {
			char c = sis.readChar();
			for(JsonType type : JsonType.values()) {
				if(type.getOpenToken() == c) {
					switch (type) {
					case ARRAY:
						val = new JsonArray<>();
						break;
					case OBJECT:
						val = new JsonObject();
						break;
					case STRING:
						val = new JsonString();
						break;
					}
					val.readNext(sis);	
					break wh;
				}
			}
		}
		return val;
	}
	
	public Object readNextObject() throws IOException{
		wk:while (sis.hasNext()) {
			char c = sis.readChar();
			for(JsonType type : JsonType.values())
				if(type.getOpenToken() == c) {
					break wk;
				}
		}
		AdaptiveJsonValue ajv = new AdaptiveJsonValue();
		ajv.readNext(sis);
		return ajv.getValue();
		
	}
	public void close() throws IOException {
		sis.close();
	}
	
	/**
	 * @return the sis
	 */
	public StringInputStream getSource() {
		return sis;
	}
	public boolean hasNext() throws IOException {
		return sis.hasNext();
	}
}

