package com.niton.media.json.exceptions;

/**
 * This is the JsonEscapeException Class
 * @author Nils
 * @version 2018-07-06
 */
public class JsonEscapeException extends JsonParsingException {
	private static final long serialVersionUID = 7393175236057127397L;

	public JsonEscapeException(String sequence,boolean isEscaped) {
		super(isEscaped?"The sequence '"+sequence+"' was escaped as it is no escape Sequence":"The String contained a unescaped escape needed Sequence");
	}
}

