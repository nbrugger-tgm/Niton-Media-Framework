package com.niton.media.json.basic;

import java.io.IOException;

import com.niton.media.json.JsonType;
import com.niton.media.json.exceptions.JsonEscapeException;
import com.niton.media.json.exceptions.JsonNotClosedException;
import com.niton.media.json.io.StringInputStream;

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
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		StringBuilder builder = new StringBuilder();
		int pos = 0;
		builder.append('\"');
		while (pos < getValue().length()) {
			char c = 0;
			c = getValue().charAt(pos);
			pos++;
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

	/**
	 * @throws IOException 
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		StringBuilder builder = new StringBuilder();
		boolean escape = false;
		while (sis.hasNext()) {
			char c = 0;
			c = sis.readChar();
			if (escape) {
				if (c == '\\' || c == '\"' || c == '/')
					builder.append(c);
				else if (c == 'b')
					builder.append('\b');
				else if (c == 'f')
					builder.append('\f');
				else if (c == 'n')
					builder.append('\n');
				else if (c == 'r')
					builder.append('\r');
				else if (c == 't')
					builder.append('\t');
				else
					throw new JsonEscapeException(c+"", true);
				escape = false;
			} else {
				escape = c == '\\';
				if (!escape) {
					if (c == JsonType.STRING.getCloseToken()) {
						setValue(builder.toString());
						return;
					}else if(c == '/' || c=='\b' || c == '\f' || c == '\n' || c == '\r' || c == '\t') {
						throw new JsonEscapeException(c+"", false);
					}else
						builder.append(c);
				}
			}
		}
		throw new JsonNotClosedException(JsonString.class);
	}
}
