package com.niton.media.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

@Deprecated
public class DataSaver<T extends Serializable> {
	public void save(T T, NFile target) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(target.getOutputStream());
		oos.writeObject(T);
		oos.close();
	}

	public void save(T T, String target) throws IOException {
		save(T, new NFile(target));
	}

	public void save(T T, OutputStream target) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(target);
		oos.writeObject(T);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public T read(NFile path) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(path.getInputStream());
		T t = null;
		try {
			t = (T) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
		return t;
	}

	@SuppressWarnings("unchecked")
	public T read(InputStream path) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(path);
		T t = null;
		try {
			t = (T) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
		return t;
	}
}
