package com.niton.media.filesystem;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The DataStrore is to store Objects into an File or Stream them
 * @author Niton
 * @version 2018-04-05
 */
public class DataStore<T> {
	private final NFile file;
	/**
	 * Creates an Instance of DataStore.java
	 * @author Niton
	 * @version 2018-04-05
	 * @param file the file to save the object in
	 */
	public DataStore(NFile file) {
		this.file = file;
	}
	
	public void save(T obejct) throws IOException {
		FileOutputStream fin = file.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(file.getOutputStream());
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obejct);
		oos.close();
		bos.close();
		fin.close();
	}
	public T read() throws IOException {
		FileInputStream fin = file.getInputStream();
		BufferedInputStream bin = new BufferedInputStream(fin);
		ObjectInputStream oin = new ObjectInputStream(bin);
		try{
			@SuppressWarnings("unchecked")
			T t = (T) oin.readObject();
			fin.close();
			return t;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		fin.close();
		return null;
	}
}

