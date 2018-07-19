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
 * 
 * @author Nils
 * @version 2018-06-06
 */
public class StringInputStream {
	private static Charset chars = Charset.forName("UTF-8");
	private Reader input;
	private int puffer = -2;
	private StringInputStream pre, after;

	public StringInputStream(String wholeData) {
		this(new ByteArrayInputStream(wholeData.getBytes(chars)));
	}

	public StringInputStream(InputStream stream) {
		input = new InputStreamReader(stream, chars);
	}

	public StringInputStream(NFile file) throws FileNotFoundException {
		this(new BufferedInputStream(file.getInputStream()));
	}

	public char readChar() throws IOException {
		if (pre != null) {
			if (pre.hasNext()) {
				char c = pre.readChar();
				return c;
			}else
				pre = null;
		}

		if (puffer != -2) {
			char c = (char) puffer;
			puffer = -2;
			return c;
		} else {
			puffer = input.read();
		}
		if (puffer == -1) {
			if (after != null)
				if(after.hasNext()) {
					char c =  after.readChar();
					return c;
				}else
					after = null;
			else
				return (char) -1;
		}
		char c = (char) puffer;
		puffer = -2;
		return c;
	}

	public boolean hasNext() throws IOException {
		if (pre != null) {
			if (!pre.hasNext())
				pre = null;
			else
				return true;
		}
		if (puffer == -2) {
			puffer = input.read();
		}
		if (puffer == -1) {
			if (after != null) {
				if(after.hasNext()) {
					puffer = -2;
					return true;
				}else {
					after = null;
					return false;
				}
			} else
				return false;
		}
		else
			return true;
	}

	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-06-30
	 * @throws IOException
	 */
	public void close() throws IOException {
		input.close();
	}

	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-07-05
	 * @param sis2
	 */
	public void setPreStream(StringInputStream sis2) {
		this.pre = sis2;
	}

	/**
	 * @param after the after to set
	 */
	public void setAfterStream(StringInputStream after) {
		this.after = after;
	}
	
	/**
	 * @return the after
	 */
	public StringInputStream getAfterStream() {
		return after;
	}
	
	/**
	 * @return the pre
	 */
	public StringInputStream getPreStream() {
		return pre;
	}
}
