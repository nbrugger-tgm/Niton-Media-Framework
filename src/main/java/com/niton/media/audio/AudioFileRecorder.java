package com.niton.media.audio;

import com.niton.media.filesystem.NFile;

import javax.sound.sampled.LineUnavailableException;
import java.io.FileNotFoundException;

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
}

