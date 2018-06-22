package com.niton.media.json;

/**
 * This is the JsonString Class
 * 
 * @author Nils
 * @version 2018-06-05
 */
public class JsonString extends JsonValue<String> {

	public JsonString(String text) {
		super(text);
	}

	/**
	 * Creates an Instance of JsonString.java
	 * 
	 * @author Nils
	 * @version 2018-06-05
	 */
	public JsonString() {
		super();
	}

	/**
	 * @see com.niton.media.json.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		StringBuilder builder = new StringBuilder();
		StringInputStream stream = new StringInputStream(getValue());
		builder.append('\"');
		while (stream.hasNext()) {
			char c = stream.read();
			switch (c) {
			case '\"':
				builder.append("\\\"");
				break;
			case '\\':
				builder.append("\\\\");
				break;
			case '/':
				builder.append("\\/");
				break;
			case '\b':
				builder.append("\\b");
				break;
			case '\f':
				builder.append("\\f");
				break;
			case '\n':
				builder.append("\\n");
				break;
			case '\t':
				builder.append("\\t");
				break;
			case '\r':
				builder.append("\\r");
				break;
			default:
				builder.append(c);
				break;
			}

		}
		builder.append('\"');
		return builder.toString();
	}

}
