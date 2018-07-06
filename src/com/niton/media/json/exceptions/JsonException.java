package com.niton.media.json.exceptions;

import com.niton.media.json.basic.JsonValue;

/**
 * This is the JsonException Class
 * @author Nils
 * @version 2018-07-06
 */
public class JsonException extends RuntimeException {
	private Class<? extends JsonValue<?>> parserClass;
	private static final long serialVersionUID = -772291868986706681L;
	public JsonException() {
		super();
	}
	public JsonException(String arg0) {
		super(arg0);
	}
	public JsonException(Throwable arg0) {
		super(arg0);
	}
	public JsonException(Class<? extends JsonValue<?>> parserClass) {
		super("JsonException ocured on "+parserClass.getSimpleName());
		this.parserClass = parserClass;
	}
	/**
	 * @param parserClass the parserClass to set
	 */
	public void setParserClass(Class<? extends JsonValue<?>> parserClass) {
		this.parserClass = parserClass;
	}
}

