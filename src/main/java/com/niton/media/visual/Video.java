package com.niton.media.visual;

import com.niton.media.IOUntility;
import com.niton.media.State;
import com.niton.media.filesystem.NFile;

import java.io.*;
import java.nio.file.Path;

/**
 * 
 * @author Nils
 *	courenly not working
 */
public abstract class Video{
	private InputStream stream;
	private VideoMode   mode         = VideoMode.STREAM;
	private long        frame        = 0;
	private float       secondPlayed = 0.0f;
	private State       state        = State.NOT_RUNNING;
	
	/**
	 * Creates an Instance of Video.java
	 * @author Nils
	 * @version 2017-08-16
	 */
	public Video(NFile file) {
		this(file.getPath());
	}
	/**
	 * Creates an Instance of Video.java
	 * @author Nils
	 * @version 2017-08-16
	 */
	public Video(Path p) {
		this(p.toFile());
	}
	
	/**
	 * Creates an Instance of Video.java
	 * @author Nils
	 * @version 2017-08-16
	 */
	public Video(File f) {
		try {
			stream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Creates an Instance of Video.java
	 * @author Nils
	 * @param inJar if true the path will be interpreted relative to the executed JAR file location
	 * @param path the path to the video file to load
	 */
	public Video(String path,boolean inJar) {
		if(inJar)
			stream = IOUntility.getInputStream(path);
		else
			try {
				stream = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Creates an Instance of Video.java
	 * @author Nils
	 * @version 2017-08-16
	 */
	public Video(InputStream stream) {
		this.stream = stream;
	}
	
	/**
	 * Creates an Instance of Video.java
	 * @author Nils
	 * @version 2017-08-16
	 */
	public Video(byte[] vidContent) {
		stream = new ByteArrayInputStream(vidContent);

	}
	/**
	 * @return the stream
	 */
	public InputStream getStream() {
		return stream;
	}
	/**
	 * Resets the player and changes the source/stream
	 * @param stream the stream to set
	 */
	public synchronized void setStream(InputStream stream) {
		reset();
		this.stream = stream;
	}
	/**
	 * @return the mode
	 */
	public VideoMode getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public synchronized void setMode(VideoMode mode) {
		reset();
		this.mode = mode;
	}
	/**
	 * @return the frame
	 */
	public long getFrame() {
		return frame;
	}
	/**
	 * @param frame the frame to set
	 */
	public synchronized void setFrame(long frame) {
		this.frame = frame;
	}
	/**
	 * @return the seccondsPlayed
	 */
	public float getSecondsPlayed() {
		return secondPlayed;
	}
	/**
	 * @param secondsPlayed the secondsPlayed to set
	 */
	public synchronized void setSecondsPlayed(float secondsPlayed) {
		this.secondPlayed = secondsPlayed;
	}
	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public synchronized void setState(State state) {
		this.state = state;
	}
	public synchronized void reset() {
		setState(State.NOT_RUNNING);
		setFrame(0);
		setSecondsPlayed(0);
	}
	
}