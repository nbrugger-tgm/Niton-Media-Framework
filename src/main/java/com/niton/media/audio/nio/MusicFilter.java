package com.niton.media.audio.nio;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import com.niton.media.filesystem.NFile;
/**
 * A File Filter for the File Types MP3 OOG WAV and M4A
 * @author Niton
 * @version 2018-04-05
 */
public class MusicFilter implements FileFilter{
	public static String[] extendiones = new String[]{"mp3","ogg","wav","m4a"};
	public NFile[] filter(NFile[] files) {
		ArrayList<NFile> musics = new ArrayList<NFile>();
		for (int i = 0; i < files.length; i++) {
			NFile nFile = files[i];
			extend:		
			for (int j = 0; j < extendiones.length; j++) {
				if(nFile.getPathAsString().toLowerCase().endsWith(extendiones[j])){
					musics.add(nFile);
					break extend;
				}
			}
		}
		NFile[] marray = new NFile[musics.size()];
		for (int i = 0; i < marray.length; i++) {
			marray[i] = musics.get(i);
		}
		return marray;
	}
	@Override
	public boolean accept(File pathname) {
		boolean typematch = pathname.isFile();
		if(typematch)
			for (String string : extendiones) 
				if(!pathname.getName().endsWith(string))
					typematch = false;
		return pathname.isDirectory() || typematch;
	}

}
