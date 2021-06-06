package com.niton.media.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This is the AudioRecorder Class
 * 
 * @author Nils Brugger
 * @version 2018-04-08
 */
public class AudioRecorder {
	private final static boolean signed = true;
	private final static boolean bigEndian = true;
	private final static AudioFileFormat.Type type = AudioFileFormat.Type.WAVE;
	private float sampleRate;
	private int sampleSizeInBits;
	private int channels;
	private final AudioFormat format;
	private final OutputStream target;
	private final TargetDataLine line;

	/**
	 * Creates an Instance of AudioRecorder with {@link AudioQuality#MEDIUM}
	 * quality @author Nils Brugger @version 2018-04-08 @throws
	 * LineUnavailableException @throws
	 */
	public AudioRecorder(OutputStream target) throws LineUnavailableException {
		this(AudioQuality.MEDIUM, target);
	}

	/**
	 * Creates an Instance of AudioRecorder.java
	 * 
	 * @author Niton
	 * @version 2018-04-08
	 * @param quality
	 *            the quality of the Audio output
	 * @param target2
	 * @throws AudioLineNotSupportetException
	 * @throws LineUnavailableException
	 */
	public AudioRecorder(AudioQuality quality, OutputStream target) throws LineUnavailableException {
		this(quality.getSampelRate(), quality.getSampleSize(), quality.getChannels(), target);
	}

	/**
	 * Creates an Instance of AudioRecorder.java
	 * 
	 * @author Nils Brugger
	 * @version 2018-04-08
	 * @param sampleRate
	 *            samples per seccond
	 * @param sampleSizeInBits
	 *            the size of a single sample in BITS (8 Bits -> 1 Byte)
	 * @param channels
	 *            the channel count (1 for Mono 2 for Stereo and more for Room
	 *            sound)
	 * @throws LineUnavailableException
	 */
	public AudioRecorder(float sampleRate, int sampleSizeInBits, int channels, OutputStream target)
			throws LineUnavailableException {
		format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		// checks if system supports the data line
		if (!AudioSystem.isLineSupported(info)) {
			throw new LineUnavailableException();
		}
		this.target = target;
		line = (TargetDataLine) AudioSystem.getLine(info);

	}

	/**
	 * Description : Starts recording and the method leaves if you call
	 * stopRecording<br>
	 * <i><b>Its <u>NECESSARY</u> that you use this in a thread, otherwise your code
	 * will stop end after a long time it will end if the output Stream can't
	 * receive any more bytes.
	 * 
	 * @author Nils Brugger
	 * @version 2018-04-08
	 * @throws LineUnavailableException
	 *             try to use another quality
	 * @throws IllegalStateException
	 *             if the audio line is already recording
	 * @throws IOException
	 *             any IO Exception. Will manly be caused by he target OutputStream
	 */
	public void record() throws LineUnavailableException, IllegalStateException, IOException {
		synchronized (line) {
			line.open(format);
			line.start();
			AudioInputStream ain = new AudioInputStream(line);
			AudioSystem.write(ain, type, target);
		}
	}

	/**
	 * Description :
	 * 
	 * @author Niton
	 * @version 2018-04-08
	 */
	public void stopRecord() {
		line.stop();
		line.close();
	}

	/**
	 * @return the channels
	 */
	public int getChannels() {
		return channels;
	}

	/**
	 * @return the format
	 */
	public AudioFormat getFormat() {
		return format;
	}

	/**
	 * @return the sampleRate
	 */
	public float getSampleRate() {
		return sampleRate;
	}

	/**
	 * @return the sampleSizeInBits
	 */
	public int getSampleSizeInBits() {
		return sampleSizeInBits;
	}

	/**
	 * @return the target
	 */
	public OutputStream getTarget() {
		return target;
	}

	/**
	 * @param channels
	 *            the channels to set
	 */
	public void setChannels(int channels) {
		this.channels = channels;
	}

	/**
	 * @param sampleRate
	 *            the sampleRate to set
	 */
	public void setSampleRate(float sampleRate) {
		this.sampleRate = sampleRate;
	}

	/**
	 * @param sampleSizeInBits
	 *            the sampleSizeInBits to set
	 */
	public void setSampleSizeInBits(int sampleSizeInBits) {
		this.sampleSizeInBits = sampleSizeInBits;
	}

	/**
	 * @return the type
	 */
	public static AudioFileFormat.Type getType() {
		return type;
	}

	/**
	 * @return the line
	 */
	public TargetDataLine getLine() {
		return line;
	}
}
