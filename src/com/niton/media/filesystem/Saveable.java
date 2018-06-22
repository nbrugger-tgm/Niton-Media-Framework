package com.niton.media.filesystem;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is the Saveable Interface.
 * @author Nils
 * @version 2017-08-28
 */
public interface Saveable {
	/**
	 * Description : write all the data from the Object to the OS in a way that you can recreate the object based on the data you write
	 * @author Niton
	 * @version 2018-04-05
	 * @param file the Output Stream to write to
	 * @return true on success
	 */
	public boolean save(OutputStream file);
	
	/**
	 * Description : recreate a Saveable object from the IS
	 * @author Niton
	 * @version 2018-04-05
	 * @param file the source of the datas
	 * @return the readen Object
	 */
	public Saveable read(InputStream file);
}

