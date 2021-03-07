package com.niton.media.audio.nio.basic;
/**
 * Defines the current state of the player
 * @author Nils
 * @version 2017-06-22
 */
public enum PlayState {
	/**
	 * The Player is playing music at the moment
	 */
	PLAY,
	/**
	 * The Player is not playing music and waiting to continue
	 */
	PAUSED,
	/**
	 * The Player is not playing and will play from the beginning if you play it
	 */
	STOPPED,
	/**
	 * the player did it's task and played the whole stream
	 */
	FINISHED;
}
