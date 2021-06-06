package com.niton.media.audio.nio;

import com.niton.media.audio.nio.basic.PlayState;
import com.niton.media.audio.nio.basic.Player;

import java.io.InputStream;

/**
 * An Utility class to Play Music
 * 
 * @author Niton
 * @version 2018-04-05
 */
public class SoundManager {
	public static Player curentMusic;
	public static Player next;
	public static PlayMode mode = PlayMode.SINGLE;

	public static void setPlayer(Player player) {
		if (curentMusic != null)
			next = player;
		else
			curentMusic = player;
	}

	public static void setMusic(InputStream source) {
		try {
			if (next == null)
				if (curentMusic != null)
					if (curentMusic.getPlayState() == PlayState.STOPPED)
						curentMusic.setSource(source);
					else
						next.setSource(source);
		} catch (NullPointerException e) {
			System.out.println("There was no Player set for the music. Ignore call");
			throw new IllegalStateException("There was no Player set or ready for the music.");
		}
	}

	public static void pauseMusic() {
		curentMusic.pausePlay();
	}

	public static void startMusic() {
		if (next != null) {
			curentMusic.stopPlay();
			curentMusic = next;
			next = null;
		}
		curentMusic.play();
	}

	public static void stopMusic() {
		curentMusic.stopPlay();
		if (next != null)
			curentMusic = next;
		next = null;
	}

	public static void continueMusic() {
		curentMusic.continuePlay();
	}
	
	/**
	 * Switches between {@link PlayMode#LOOP} and {@link PlayMode#SINGLE}
	 * @author Niton
	 * @version 2018-04-05
	 */
	public static void togglePlaymode() {
		if (mode == PlayMode.LOOP) {
			mode = PlayMode.SINGLE;
		} else {
			mode = PlayMode.LOOP;
		}
	}

}
