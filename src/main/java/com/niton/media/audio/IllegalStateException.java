package com.niton.media.audio;

public class IllegalStateException extends Exception {
	private static final long serialVersionUID = 1L;
	@Override
	public String getMessage() {
		return "The Player has an Illegal State";
	}
}
