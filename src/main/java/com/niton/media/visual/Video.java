package com.niton.media.visual;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import com.niton.media.IOUntility;
import com.niton.media.State;
import com.niton.media.filesystem.NFile;

/**
 * 
 * @author Nils
 *	courenly not working
 */
public class Video{
	private InputStream stream;
	private VideoMode mode = VideoMode.STREAM;
	private long frame = 0;
	private float seccondsPlayed = 0.0f;
	private State state = State.NOT_RUNNING;
	private boolean useBuffer = true;
	private boolean usePreload = false;
	private long preloadSize = 1024;
	private int bufferSize = 512;
	private byte[] buffer = new byte[bufferSize];
	
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
	 * @version 2017-08-16
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
	public float getSeccondsPlayed() {
		return seccondsPlayed;
	}
	/**
	 * @param seccondsPlayed the seccondsPlayed to set
	 */
	public synchronized void setSeccondsPlayed(float seccondsPlayed) {
		this.seccondsPlayed = seccondsPlayed;
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
	/**
	 * @return the useBuffer
	 */
	public boolean useBuffer() {
		return useBuffer;
	}
	/**
	 * @param useBuffer the useBuffer to set
	 */
	public synchronized void setUseBuffer(boolean useBuffer) {
		if(useBuffer) {
			buffer = new byte[getBufferSize()];
			try {
				stream.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.useBuffer = useBuffer;
	}
	/**
	 * @return the usePreload
	 */
	public synchronized boolean isUsePreload() {
		return usePreload;
	}
	/**
	 * @param usePreload the usePreload to set
	 */
	public synchronized void setUsePreload(boolean usePreload) {
		this.usePreload = usePreload;
	}
	/**
	 * @return the preloadSize
	 */
	public long getPreloadSize() {
		return preloadSize;
	}
	/**
	 * @param preloadSize the preloadSize to set
	 */
	public synchronized void setPreloadSize(long preloadSize) {
		this.preloadSize = preloadSize;
	}
	/**
	 * @return the bufferSize
	 */
	public int getBufferSize() {
		return bufferSize;
	}
	/**
	 * @param bufferSize the bufferSize to set
	 */
	public synchronized void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	/**
	 * @return the buffer
	 */
	public byte[] getBuffer() {
		return buffer;
	}
	public synchronized void reset() {
		setState(State.NOT_RUNNING);
		setFrame(0);
		setBufferSize(getBufferSize());
		setSeccondsPlayed(0);
	}
	
}