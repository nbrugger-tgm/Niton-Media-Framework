package com.niton.media.audio;

import java.io.Serializable;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicRunner extends Thread implements Serializable  {

	/**
	 * <b>Type:</b> long<br> 
	 * <b>Description:</b><br>
	 */
	private static final long serialVersionUID = 1L;
	private Player player;
	private com.niton.media.State status;
	private int lastPos = 1000;
	private int lastCMD = 0;
	private static final int PLAY = 4; 
	private static final int PAUSE = 1; 
	private static final int CONTINOUE = 3;
	private static final int STOP = 2;  
	private Thread playThread;
	private Player iniedPlayer;
		
	public MusicRunner(Player player) {
		this.setPlayer(player);
		iniPlayer(1000,player);
	}
	
	private void iniPlayer(final int lastPosi, final Player player) {
		playThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					player.play(lastPosi);
					stopCourentMusic();
					lastPos = 1000;
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	@Override
	public void run()
	{
		while (true)
		{
			switch (lastCMD) {
				case PLAY:
					playMusic();
					lastCMD = 0;
					break;
				case PAUSE:
					pauseMusic();
					lastCMD = 0;
					break;
				case STOP:
					stopCourentMusic();
					lastCMD = 0;
					break;
				case CONTINOUE:
					continoueMusic();
					lastCMD = 0;
					break;
				default:
					continue;
			}
		}
	}
	
	private void continoueMusic() {
		playThread.start();
	}

	private void stopCourentMusic() 
	{
		player.close();
		player = iniedPlayer;
		iniPlayer(lastPos, player);
	}

	private void pauseMusic() {
		lastPos = player.getPosition();
		stopCourentMusic();
	}

	private void playMusic() {
		lastPos = 1000;
		playThread.start();
	}

	public void pause()
	{
		lastCMD = PAUSE;
	}
	
	public void continoue()
	{
		lastCMD = CONTINOUE;
	}
	public void play()
	{
		lastCMD = PLAY;
	}
	public void stopMusic(Player player2)
	{
		iniedPlayer = player2;
		lastCMD = STOP;
	}

	public int getLastPos() {
		return lastPos;
	}

	public void setLastPos(int lastPos) {
		this.lastPos = lastPos;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public com.niton.media.State getStatus() {
		return status;
	}

	public void setStatus(com.niton.media.State status) {
		this.status = status;
	}
}
