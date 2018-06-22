package com.niton.media.audio;

import javax.sound.sampled.AudioSystem;

/**
 * This Exception appears when {@link AudioSystem#isLineSupported(javax.sound.sampled.Line.Info)} returns false for your Audio Line<br>
 * You can try to change the quality
 * @author Niton
 * @version 2018-04-08
 */
public class AudioLineNotSupportetException extends Exception {
	/**
	 * <b>Type:</b> long<br> 
	 * <b>Description:</b><br>
	 */
	private static final long serialVersionUID = 121620276048275891L;

	public AudioLineNotSupportetException() {

	}
}

