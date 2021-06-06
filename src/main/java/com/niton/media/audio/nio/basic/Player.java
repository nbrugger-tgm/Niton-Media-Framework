package com.niton.media.audio.nio.basic;

import java.io.InputStream;
import java.util.ArrayList;
/**
 * This is the Player class.<br>
 * This interfaces provides all functiones a MusicPlayer needss to have.<br>
 * Basicly its made to play a certain type of music file.<br>
 * If you need to play an not MP3 file you have to write your own player.<br>
 * <b><i>All the methods in player do NOT wait until their task is done! They will not take the time as the stream takes to play.</i></b>
 * For this purpose this class extends Thread.
 * @author Niton
 * @version 2018-04-05
 */
public abstract class Player extends Thread{
	private ArrayList<MusicListener> listeners = new ArrayList<>();
	/**
	 * Plays the audio from start
	 * @author Alexander
	 */
	public abstract void play();
	
	/**
	 * stops playing the stream and reset to start
	 * @author Alexander
	 */
	public abstract void stopPlay();
	
	/**
	 * pauses the playing to continue later
	 * @author Alexander
	 */
	public abstract void pausePlay();
	
	/**
	 * continues the playing if paused
	 * @author Alexander
	 */
	public abstract void continuePlay();
	/**
	 * Description : Returns the state the player is in.
	 * @author Niton
	 * @version 2018-04-05
	 * @return the players current state
	 */
	public abstract PlayState getPlayState();
	/**
	 * Description : Returns the position from the music file or stream
	 * @author Niton
	 * @version 2018-04-05
	 * @return the positon mark from the music file or stream
	 */
	public abstract int getCurentPosition();
	/**
	 * Description : Returns the total length of the pice of music to play 
	 * @author Niton
	 * @version 2018-04-05
	 * @return the total length
	 */
	public abstract int getTotalLenght();
	/**
	 * Description : Returns the source of the music.<br>
	 * Don't read from this stream beacause this will stop the player working 
	 * @author Niton
	 * @version 2018-04-05
	 * @return the music source as InputStream
	 */
	public abstract InputStream getStream();
	
	/**
	 * Description : Changes the source of the music.<br>
	 * <b>ONLY USE IF THE PLAYER WASNT USED BEVORE</b><br>
	 * We recommend to create a new Player instead 
	 * @author Niton
	 * @version 2018-04-05
	 * @param source
	 */
	public abstract void setStream(InputStream source);
	
	public void setSource(InputStream source) {
		setStream(source);
	}

	/**
	 * @return the listeners
	 */
	public ArrayList<MusicListener> getListeners() {
		return listeners;
	}

	/**
	 * @param listener the listeners to add
	 */
	public void addListener(MusicListener listener) {
		this.listeners.add(listener);
	}
	/**
	 * @see MusicListener#onPlayFinished(Player)
	 * @author Niton
	 * @version 2018-04-05
	 */
	protected void fireMusicEndEvent() {
		for (MusicListener musicListener : listeners)
			musicListener.onPlayFinished(this);
	}
	/**
	 * @see MusicListener#onTimeChange(int, Player)
	 * @author Niton
	 * @version 2018-04-05
	 */
	protected void fireTimeChangedEvent() {
		for (MusicListener musicListener : listeners)
			musicListener.onTimeChange(getCurentPosition(), this);
	}
}
