package com.niton.media.json.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.spi.CharsetProvider;

import com.niton.media.filesystem.NFile;

/**
 * This is the StringInputStream Class
 * @author Nils
 * @version 2018-06-06
 */
public class StringInputStream{
	private static Charset chars = Charset.forName("UTF-8");
	private Reader input;
	int next = -2;
	public StringInputStream(String wholeData) {
		this(new ByteArrayInputStream(wholeData.getBytes(chars)));
	}
	public StringInputStream(InputStream stream)  {
		input = new BufferedReader(new InputStreamReader(stream, chars));
	}
	public StringInputStream(NFile file) throws FileNotFoundException {
		this(new BufferedInputStream(file.getInputStream()));
	}
	public char readChar() throws IOException {
		if(next == -2) {
			return (char) input.read();
		}
		else {
			char c = (char) next;
			next = -2;
			return c;
		}
	}
	public boolean hasNext() throws IOException {
		if(next == -2) {
			next = input.read();
		}
		return next != -1;
	}
	/**
	 * Description : 
	 * @author Nils
	 * @version 2018-06-30
	 * @throws IOException 
	 */
	public void close() throws IOException {
		input.close();
	}
}

