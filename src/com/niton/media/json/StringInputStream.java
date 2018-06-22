package com.niton.media.json;

/**
 * This is the StringInputStream Class
 * @author Nils
 * @version 2018-06-06
 */
public class StringInputStream {
	private String wholeData;
	private int position;
	public StringInputStream(String wholeData) {
		super();
		this.wholeData = wholeData;
		position = 0;
	}
	public char read() {
		return wholeData.charAt(position++);
	}
	public boolean hasNext() {
		return wholeData.length()>position;
	}
}

