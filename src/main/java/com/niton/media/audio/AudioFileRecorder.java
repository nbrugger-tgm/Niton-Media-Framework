package com.niton.media.audio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;

import com.niton.media.filesystem.NFile;

/**
 * This is the AudioFileRecorder Class
 * @author Niton
 * @version 2018-04-08
 */
public class AudioFileRecorder extends AudioRecorder {

	private final NFile target;
	
	public AudioFileRecorder(AudioQuality quality, NFile target) throws LineUnavailableException, FileNotFoundException {
		this(quality.getSampelRate(),quality.getSampleSize(),quality.getChannels(), target);
	}

	public AudioFileRecorder(float sampleRate, int sampleSizeInBits, int channels, NFile target)
			throws LineUnavailableException, FileNotFoundException {
		super(sampleRate, sampleSizeInBits, channels, target.getOutputStream());
		this.target = target;
	}

	public AudioFileRecorder(NFile target) throws LineUnavailableException, FileNotFoundException {
		this(AudioQuality.MEDIUM,target);
	}
	
	 /**
	 * @see com.niton.media.audio.AudioRecorder#record()
	 */
	@Override
	public void record() throws LineUnavailableException, IllegalStateException, IOException {
		synchronized (getLine()) {
			getLine().open(getFormat());
			getLine().start();
			AudioInputStream ain = new AudioInputStream(getLine());
			AudioSystem.write(ain, getType(), target.getFile());
		}
	}
}

