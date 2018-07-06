package com.niton.media.json.exceptions;

import com.niton.media.json.basic.JsonValue;

/**
 * This is the JsonNotClosedException Class
 * @author Nils
 * @version 2018-07-06
 */
public class JsonNotClosedException extends JsonParsingException {
	private static final long serialVersionUID = 1704416108204939789L;

	public JsonNotClosedException(Class<? extends JsonValue<?>> parserClass) {
		super("The "+parserClass.getSimpleName()+" was not closed correctly");
	}
	
}

