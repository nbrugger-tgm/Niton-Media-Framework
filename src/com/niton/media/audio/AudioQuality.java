package com.niton.media.audio;

/**
 * This is the AudioQuality Class.<br>
 * Provides different Step of audio Quality. Used for {@link AudioRecorder}
 * @author Niton
 * @version 2018-04-08
 */
public enum AudioQuality {
	VERY_HIGHT(128000,64,2),
	HIGH(64000,32,2),
	MEDIUM(32000,32,2),
	NORMAL(32000,16,2),
	LOW(16000,16,2),
	VERY_LOW(16000,8,2),
	MONO_HIGH(64000,64,1),
	MONO_NORMAL(32000,32,1),
	MONO_LOW(16000,16,1);
	
	private final long sampelRate;
	private final int sampleSize;
	private final int channels;
	private AudioQuality(long sampelRate, int sampleSize, int channels) {
		this.sampelRate = sampelRate;
		this.sampleSize = sampleSize;
		this.channels = channels;
	}
	/**
	 * @return the sampelRate
	 */
	public long getSampelRate() {
		return sampelRate;
	}
	/**
	 * @return the sampleSize
	 */
	public int getSampleSize() {
		return sampleSize;
	}
	/**
	 * @return the channels
	 */
	public int getChannels() {
		return channels;
	}


}

