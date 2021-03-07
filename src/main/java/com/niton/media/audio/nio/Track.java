package com.niton.media.audio.nio;

import java.io.Serializable;

/**
 * <u><i><b>!!!Not working now!!!</b></i></u>
 * The Class for a PlayList.<br>
 * It represents a single Track on the list.<br>
 * Its name is the file name common.<br>
 * @author Nils
 */
@Deprecated
public class Track implements Serializable{
	private static final long serialVersionUID = 1168615795821171680L;
	private byte[] content;
	private String name;
	/**
	 * Generates the track with an default name
	 * @param name the name for the track<br>
	 * By default it is the file name<br>
	 *  For Example : <i>Avicii - Adicted to You.mp3</i> 
	 */
	public Track(String name) {
		super();
		this.setName(name);
	}
	/**
	 * get the content of the original MP3 File
	 * @return the content as byte[] -> all the bytes of the original file
	 */
	public byte[] getContent() {
		return content;
	}
	/**
	 * sets the content of the Track
	 * @param content the content as byte array<br>Everything included -> Audio, Meta, Header and Artwork.<br>By Default something like <pre>Files.readAllBytes(...);</pre>
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	/**
	 * Gives you the name of the Track
	 * @return the name used to identify it.
	 */
	public String getName() {
		return name;
	}
	/**
	 * sets the Name
	 * @param name The name the user will see in the playlist and used to search and identify the track in the list<br>
	 * <b>NEVER USE THE SAME NAME TWICE</b> 
	 */
	public void setName(String name) {
		this.name = name;
	}
}
