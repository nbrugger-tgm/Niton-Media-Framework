package com.niton.media.json.exceptions;

import com.niton.media.json.basic.JsonValue;

/**
 * This is the GenerateJsonException Class
 * @author Nils
 * @version 2018-07-06
 */
public class GenerateJsonException extends JsonException{
	private static final long serialVersionUID = -8447537650593809869L;

	public GenerateJsonException(Class<? extends JsonValue<?>> parserClass) {
		super("Json Error while generating  an "+parserClass.getSimpleName());
		setParserClass(parserClass);
	}

	public GenerateJsonException(String arg0) {
		super(arg0);
	}

	public GenerateJsonException(Throwable arg0) {
		super(arg0);
	}

	
}

