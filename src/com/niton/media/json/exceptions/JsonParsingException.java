package com.niton.media.json.exceptions;

import com.niton.media.json.basic.JsonValue;

/**
 * This is the JsonParsingException Class
 * @author Nils
 * @version 2018-07-06
 */
public class JsonParsingException extends JsonException {
	public JsonParsingException(Throwable arg0) {
		super(arg0);
	}

	private static final long serialVersionUID = 2030790088828753283L;

	public JsonParsingException(Class<? extends JsonValue<?>> parserClass) {
		super("An Json Error while trying to read an "+parserClass.getSimpleName());
		setParserClass(parserClass);
	}

	public JsonParsingException(String arg0) {
		super(arg0);
	}

	public JsonParsingException() {

	}
}

