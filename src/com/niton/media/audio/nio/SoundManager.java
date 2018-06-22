package com.niton.media.audio.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import com.niton.media.audio.nio.basic.MP3Player;
import com.niton.media.audio.nio.basic.PlayState;
import com.niton.media.audio.nio.basic.Player;
import com.niton.media.filesystem.Directory;
import com.niton.media.filesystem.NFile;

import javazoom.jl.decoder.JavaLayerException;

/**
 * An Utility class to Play Music This is the SoundManager Class
 * 
 * @author Niton
 * @version 2018-04-05
 */
public class SoundManager {
	public static Player curentMusic;
	public static Player next;
	public static PlayMode mode = PlayMode.SINGLE;
	public final static NFile temp = new NFile(
			Paths.get(System.getProperty("user.home"), "JProgramms", "Media API", "tmp.mp3"));
	public final static NFile tempNext = new NFile(
			Paths.get(System.getProperty("user.home"), "JProgramms", "Media API", "tmpNext.mp3"));

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

	/**
	 * <u><i><b>!!!Not working now!!!</b></i></u>
	 * Generates an temporary File by the Track deletes the old if exisiting and
	 * uses setMusic(NFile) to setTheCurrentMusic
	 * 
	 * @param track
	 */
	@Deprecated
	public static void setMusic(Track track) {
		try {
			if (curentMusic == null) {
				temp.delete();
				temp.save();
				Files.write(temp.getPath(), track.getContent());
				setMusic(temp.getInputStream());
			} else {
				tempNext.delete();
				tempNext.save();
				Files.write(tempNext.getPath(), track.getContent());
				setMusic(tempNext.getInputStream());
			}
		} catch (IOException e) {
			System.err.println("Error Creating a temp File... :");
			e.printStackTrace();
		}
	}
}
