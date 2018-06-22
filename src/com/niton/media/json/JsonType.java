package com.niton.media.json;

/**
 * This is the JsonType Class
 * 
 * @author Nils
 * @version 2018-06-06
 */
public enum JsonType {
	STRING('\"', '\"'),
	ARRAY('[', ']'),
	OBJECT('{','}'),
	PAIR(':',',');
	private final char openToken;
	private final char closeToken;

	private JsonType(char openToken, char closeToken) {
		this.openToken = openToken;
		this.closeToken = closeToken;
	}

	/**
	 * @return the openToken
	 */
	public char getOpenToken() {
		return openToken;
	}

	/**
	 * @return the closeToken
	 */
	public char getCloseToken() {
		return closeToken;
	}
}