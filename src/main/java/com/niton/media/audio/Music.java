package com.niton.media.audio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.nio.file.Path;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.Port.Info;

import com.niton.media.State;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Music implements Serializable {
	private static final long serialVersionUID = -5800476406160332048L;
	private Path file;
	private Player player;
	private com.niton.media.State status;
	private int lastPos;
	private MusicRunner thread;
	private String name;

	public Music(Path file) {
		setFile(file);
		setStatus(com.niton.media.State.NOT_RUNNING);
		iniPlayer();
		thread = new MusicRunner(player);
		thread.start();
	}

	private void iniPlayer() {
		try {
			FileInputStream stream = new FileInputStream(file.toString());
			player = new Player(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		thread.play();
	}

	public void stopMusic() {
		iniPlayer();
		thread.stopMusic(player);
	}

	public void pause() {
		thread.pause();

	}

	public void continoue() {
		thread.continoue();
	}

	public void loopPlaying() {
		while (status != State.STOPPED) {
			thread.play();
		}
	}

	/**
	 * @param value
	 */
	// public void changeVolume(float value)
	// {
	// Info source = Port.Info.SPEAKER;
	// // source = Port.Info.LINE_OUT;
	// // source = Port.Info.HEADPHONE;
	//
	// if (AudioSystem.isLineSupported(source))
	// {
	// try
	// {
	// Port outline = (Port) AudioSystem.getLine(source);
	// outline.open();
	// volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
	// System.out.println("Old Value: "+volumeControl.getValue());
	// volumeControl.setValue(value);
	// System.out.println("New Value: "+volumeControl.getValue());
	// }
	// catch (LineUnavailableException ex)
	// {
	// System.err.println("source not supported");
	// ex.printStackTrace();
	// }
	// }
	// }

	public boolean isEnded() {
		return player.isComplete();
	}

	public int getCourentPos() {
		return player.getPosition();
	}

	public Path getFile() {
		return file;
	}

	public void setFile(Path file) {
		this.file = file;
	}

	public void setStatus(com.niton.media.State status) {
		this.status = status;
	}

	public com.niton.media.State getStatus() {
		return status;
	}

	public int getLastPos() {
		return lastPos;
	}

	public void setLastPos(int lastPos) {
		this.lastPos = lastPos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}