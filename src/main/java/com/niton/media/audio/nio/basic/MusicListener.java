package com.niton.media.audio.nio.basic;

/**
 * This is the Listener class for Player (Music)<br>
 * This are listeners to events that happen in a {@link Player}
 * @author Niton
 * @version 2018-04-05
 */
public interface MusicListener {
	/**
	 * Called when the time on the {@link Player} changed.<br>
	 * This should be called permanently while the Players State is {@link PlayState#PLAY} 
	 * @author Niton
	 * @version 2018-04-05
	 * @param time the new position of the Player
	 * @param p the Player caused the event
	 */
	public void onTimeChange(int time,Player p);
	/**
	 * Description : This method should be called when the Stream has ended peacful.<br>
	 * The State of the Player should be {@link PlayState#FINISHED}
	 * @author Niton
	 * @version 2018-04-05
	 * @param p the Player caused the event
	 */
	public void onPlayFinished(Player p);
}

