package com.niton.media.audio.nio.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
/**
 * Represent a Audio Player for MP3<br>
 * 
 * @author Nils, Alex
 * @see javazoom.jl.player.Player
 */
public class MP3Player extends com.niton.media.audio.nio.basic.Player{
	private InputStream file;
	private PlayState state = PlayState.STOPPED;
	private Player player;
	private int pos = 0;
	private File FILE;
	/**
	 * Creates an Instance of MP3Player based on a MP3 File
	 * @author Niton
	 * @version 2018-04-05
	 * @param path the path to the MP3 File as String
	 * @throws FileNotFoundException if the path does not points to a file we are able to read
	 * @throws JavaLayerException any other MP3 Encoding based Exception
	 */
	public MP3Player(String path) throws FileNotFoundException, JavaLayerException {
		this(Paths.get(path));
	}
	/**
	 * Creates an Instance of MP3Player based on a MP3 File
	 * @author Niton
	 * @version 2018-04-05
	 * @param path the path to the MP3 File
	 * @throws FileNotFoundException if the path does not points to a file we are able to read
	 * @throws JavaLayerException any other MP3 Encoding based Exception
	 */
	public MP3Player(Path file) throws FileNotFoundException, JavaLayerException {
		this(new FileInputStream(file.toFile()));
		FILE = file.toFile();
	}
	
	/**
	 * Creates an Instance of MP3Player based on a MP3 Stream.<br>
	 * May be an Server Stream for VOIP or a Music Streaming or a simple {@link FileInputStream}
	 * @author Niton
	 * @version 2018-04-05
	 * @param stream the path to the MP3 File as String
	 * @throws FileNotFoundException if the path does not points to a file we are able to read
	 * @throws JavaLayerException any other MP3 Encoding based Exception
	 */
	public MP3Player(InputStream stream) throws FileNotFoundException, JavaLayerException{
		super();
		setSource(stream);
		start();
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				while (true){
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						continue;
//					}
//					switch (state) {
//					case FINISHED:
//						break;
//					case PAUSED:
//						break;
//					case PLAY:
//						pos++;
//						Listeners.onTimeChange(pos);
//						break;
//					case STOPPED:
//						pos = 0;
//						break;
//					}
//				}
//			}
//		}).start();
	}
	
	/**
	 * @author Nils
	 */
	public synchronized void play(){
		pos = 0;
		createPlayer();
		state = PlayState.PLAY;
	}

	/**
	 * @author Nils
	 */
	public synchronized void stopPlay(){
		pos = 0;
		state = PlayState.STOPPED;
	}

	/**
	 * @author Nils
	 */
	public synchronized void pausePlay(){
		System.out.println("Wurde pausiert");
		state = PlayState.PAUSED;
	}
	
	/**
	 * @author Nils
	 */
	public synchronized void continuePlay(){
		System.out.println("Continue MP3");
		state = PlayState.PLAY;
	}
	
	@Override
	public void run() {
		while(true){
			switch (state) {
			case FINISHED:
				if(player != null){
					fireMusicEndEvent();
					player = null;
				}
				break;
			case PAUSED:
				break;
			case PLAY:
				try {
					if(player == null)
						createPlayer();
					if(player.isComplete())
						state = PlayState.FINISHED;
					player.play(1);
					fireTimeChangedEvent();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
				break;
			case STOPPED:
				if(player != null)
					player = null;
				break;
			}
			if(state != PlayState.PLAY){
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private synchronized void createPlayer(){
		try {
			player = new Player(getStream());
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
	
	public PlayState getPlayState() {
		return state;
	}
	public Player getPlayer() {
		return player;
	}
	public synchronized void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public int getCurentPosition() {
		return pos;
	}
	
	/**
	 * Do not call this often!! it is a really unperformed.<br>
	 * It will create an Temp file with the whole Stream goes to it.<br>
	 * This operation can take a long time for big streams!!!!<br>
	 * Except the stream comes from an File. In this case the file will not read completely or duplicated
	 * @see com.niton.media.audio.nio.basic.Player#getTotalLenght()
	 */
	@Override
	public synchronized int getTotalLenght() {
		try {
			if(file instanceof FileInputStream) {
				AudioFile f = AudioFileIO.read(FILE);
				return f.getAudioHeader().getTrackLength();
			}
			File fl = new File(System.getProperty("user.home")+System.getProperty("file.seperator")+"music.tmp");
			FileOutputStream fos = new FileOutputStream(fl);
			byte b;
			do {
				b = (byte) getStream().read();
				fos.write(b);
			}while(b != -1);
			fos.close();
			getStream().reset();
			AudioFile f = AudioFileIO.read(fl);
			fl.delete();
			return f.getAudioHeader().getTrackLength();
		} catch (CannotReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public InputStream getStream() {
		return file;
	}
	/**
	 * @param file the Source to set
	 */
	public void setSource(InputStream file) {
		this.file = file;
	}
	/**
	 * @see com.niton.media.audio.nio.basic.Player#setStream(java.io.InputStream)
	 */
	@Override
	public void setStream(InputStream source) {
		setSource(source);
	}
}

