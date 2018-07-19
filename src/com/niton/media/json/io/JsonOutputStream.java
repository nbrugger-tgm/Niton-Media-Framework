package com.niton.media.json.io;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.niton.media.filesystem.NFile;
import com.niton.media.json.JsonSerializer;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.types.JsonSerialObject;
import com.niton.media.json.types.advanced.AdaptiveJsonValue;

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
	
	@Deprecated
	public OutputStream getOut() {
		return out;
	}
	public OutputStream getTarget() {
		return out;
	}
	@Override
	public void write(int arg0) throws IOException {
		out.write(arg0);
	}
	public <T> void write(T o) throws InstantiationException, IllegalAccessException, UnsupportedEncodingException, IOException {
		out.write(new AdaptiveJsonValue(o).getJson().getBytes("UTF-8"));
		flush();
	}
	public void write(JsonValue<?> json) throws UnsupportedEncodingException, IOException {
		out.write(json.getJson().getBytes("UTF-8"));
		flush();
	}
	/**
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		out.close();
		super.close();
	}
	/**
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException {
		out.flush();
		super.flush();
	}
}

