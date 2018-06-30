package com.niton.media.json.io;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import com.niton.media.filesystem.NFile;
import com.niton.media.json.JsonSerializer;
import com.niton.media.json.basic.JsonValue;

/**
 * This is the JsonOutputStream Class
 * @author Nils
 * @version 2018-06-30
 */
public class JsonOutputStream extends OutputStream {
	private OutputStream out;
	public JsonOutputStream(OutputStream output) {
		this.out = output;
	}
	public JsonOutputStream(NFile target) throws FileNotFoundException {
		out = new BufferedOutputStream(target.getOutputStream());
	}
	public OutputStream getOut() {
		return out;
	}
	@Override
	public void write(int arg0) throws IOException {
		out.write(arg0);
	}
	public <T> void write(T o) throws InstantiationException, IllegalAccessException {
		JsonValue<T> pure = (JsonValue<T>) JsonSerializer.getJsonFor(o.getClass()).newInstance();
		pure.setValue(o);
		write(pure);
	}
	public void write(JsonValue<?> json) {
		try {
			out.write(json.getJson().getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

