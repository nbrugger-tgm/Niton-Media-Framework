package com.niton.media.audio.nio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.niton.media.audio.nio.basic.PlayState;
import com.niton.media.audio.nio.basic.Player;
import com.niton.media.filesystem.NFile;


/**
 * <u><i><b>!!!Not working now!!!</b></i></u>
 * 
 * A Playlist storing the complete data. Its a Array List of
 * <i>at.niton.ichzocke.model.Track</i>
 * 
 * @author Nils
 * @see java.util.ArrayList
 * @see at.niton.ichzocke.model.Track
 */
@Deprecated
public class PlayList extends ArrayList<Track> implements Serializable{
	private static final long serialVersionUID = -4424055798156234860L;
	private String name;
	private String genere;
	private String interpret;
	private int currentIndex = -1;
	private PlayState state = PlayState.STOPPED;
	private PlayListMode mode = PlayListMode.LoopAll;
	/**
	 * Resolves the File or path reads it generates an Track out of the data and
	 * adds it per
	 * 
	 * @see java.util.ArrayList
	 * @see at.niton.ichzocke.model.Track
	 * @param nf
	 */
	public void add(NFile nf) {
		add(nf.getPath());
	}

	/**
	 * @throws IOException 
	 * @see java.lang.ArrayList.add
	 * @see at.niton.ichzocke.model.Track
	 */
	public void add(Player player,String name) throws IOException {
		add(player.getStream(),name);
	}

	/**
	 * Description : 
	 * @author Nils
	 * @version 2017-08-18
	 * @param stream
	 * @throws IOException 
	 */
	public void add(InputStream stream,String name) throws IOException {
		Track t = new Track(name);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte b;
		do {
			b = (byte) stream.read();
			bos.write(b);
		} while (b !=  -1);
		t.setContent(bos.toByteArray());
		add(new Track(name));
	}

	/**
	 * @see java.lang.ArrayList.add
	 * @see at.niton.ichzocke.model.Track
	 */
	public void add(String path) {
		add(new File(path));
	}

	/**
	 * @see java.lang.ArrayList.add
	 * @see at.niton.ichzocke.model.Track
	 */
	public void add(Path path) {
		byte[] cont = null;
		try {
			cont = Files.readAllBytes(path);
			String[] dirs = path.toAbsolutePath().toString().split(System.getProperty("file.seperator"));
			Track t = new Track(dirs[dirs.length - 1]);
			t.setContent(cont);
			add(t);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see java.lang.ArrayList.add
	 * @see at.niton.ichzocke.model.Track
	 */
	public void add(File file) {
		add(file.toPath());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getInterpret() {
		return interpret;
	}

	public void setInterpret(String interpret) {
		this.interpret = interpret;
	}
}
