package com.niton.media.filesystem;

import com.niton.media.IOUntility;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NFile implements Serializable{
	private Path file;

	public NFile(String path) {
		this.file = Paths.get(path);
	}

	public NFile(String... paths) {
		String slash = System.getProperty("file.separator");
		String path = "";
		for (int i = 0; i < paths.length; i++) {
			path += paths[i] + (i + 1 < paths.length ? slash : "");
		}
		file = Paths.get(path);
	}

	public NFile(Path path) {
		file = path;
	}

	public NFile(File path) {
		file = path.toPath();
	}

	public NFile(Directory dir, String name, String ending) {
		String fullName = name + "." + ending;
		this.file = Paths.get(dir.getPathAsString(), fullName);
	}

	public NFile(Directory dir, String fullName) {
		this.file = Paths.get(dir.getPathAsString(), fullName);
	}

	public Path getPath() {
		return file;
	}

	public Path toPath() {
		return file;
	}

	public File toFile() {
		return file.toFile();
	}

	public String getPathAsString() {
		return getPath().toString();
	}

	public Directory getParent() {
		return new Directory(getPath().getParent());
	}

	public Path getRoot() {
		return file.getRoot();
	}

	/**
	 * Calculates how deep the file is in the file structure (how many folders you need to go thought to get to the file)
	 * eg.<br>
	 * D:\test\test\file.ext -> 2  <br>
	 * /ect/init.d/somother/idfk/test.bin -> 4
	 * @author Nils Brugger
	 * @version 2019-10-21
	 * @return the depth
	 */
	public int getDepth() {
		return file.getNameCount();
	}

	public String getName() {
		String filename = file.getFileName().toString();
		filename = filename.replace(getEnding(), "");
		filename = filename.substring(0, filename.length() - 1);
		return filename;
	}

	public String getEnding() {
		String filename = file.getFileName().toString();
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

	public String getText() throws IOException {
		String back = "";
		back = new String(Files.readAllBytes(file), "UTF-8");
		return back;
	}

	public void setText(String text) throws IOException {
		Files.write(file, text.getBytes("UTF-8"));
	}

	public void addText(String text) throws IOException {
		Files.write(file, (getText() + text).getBytes());
	}

	public void save() throws IOException {
		getParent().save();
		Files.createFile(getPath());
	}

	public void delete() throws IOException {
		Files.delete(getPath());
	}

	public boolean exisits() {
		return Files.exists(file);
	}

	public void copyReplace(NFile target) throws IOException {
		if (target.exisits())
			target.delete();
		copy(target);
	}

	public void copy(NFile target) throws IOException {
		target.save();
		target.write(getInputStream());
	}

	/**
	 * <b>Description :</b><br>
	 * 
	 * @author Nils Brugger
	 * @version 2019-10-21
	 * @param inputStream
	 * @throws FileNotFoundException 
	 */
	public void write(InputStream inputStream) throws FileNotFoundException {
		IOUntility.copyBigStream(inputStream, getOutputStream());
	}
	
	/**
	 * 
	 * @author Nils Brugger
	 * @version 2019-10-21
	 * @param out
	 * @throws FileNotFoundException
	 */
	public void read(OutputStream out) throws FileNotFoundException {
		IOUntility.copyBigStream(getInputStream(), out);
	}

	public void copyBig(NFile target) throws IOException {
		FileInputStream is = (FileInputStream) getInputStream();
		OutputStream stream = target.getOutputStream();
		FileChannel ch = is.getChannel();
		ByteBuffer bb = ByteBuffer.allocateDirect(1024 * 1000);
		byte[] barray = new byte[512 * 1000];
		int nRead, nGet;
		short i = 0;
		while ((nRead = ch.read(bb)) != -1) {
			i++;
			if (i == 20) {
				i = 0;
				System.gc();
			}
			if (nRead == 0)
				continue;
			bb.position(0);
			bb.limit(nRead);
			while (bb.hasRemaining()) {
				nGet = Math.min(bb.remaining(), 512 * 1000);
				bb.get(barray, 0, nGet);
				stream.write(barray);
			}
			bb.clear();
		}
	}

	public void moveReplace(NFile target) throws IOException {
		if (target.exisits())
			target.delete();
		move(target);
	}

	public void move(NFile target) throws IOException {
		if (target.exisits())
			throw new FileAlreadyExistsException(
					"Move file to existing file impossible! use NFile.moveReplace(NFile) instead");
		Files.move(file, target.file);
	}

	/**
	 * Opens and returns an stream FROM the file
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @return the stream FROM the NFile
	 * @throws FileNotFoundException if the file is not found you cannot open an
	 *                               Stream
	 */
	public FileInputStream getInputStream() throws FileNotFoundException {
		return new FileInputStream(getFile());
	}

	/**
	 * Opens and returns an stream TO the file
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @return the stream TO the NFile
	 * @throws FileNotFoundException if the file is not found you cannot open an
	 *                               Stream
	 */
	public FileOutputStream getOutputStream() throws FileNotFoundException {
		return new FileOutputStream(getFile());
	}

	/**
	 * Returns the object as File
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @return the file
	 */
	public File getFile() {
		return getPath().toFile();
	}

	/**
	 * Renames the file to the given name.
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param newName the new Name for the file
	 * @throws IOException                read write error
	 * @throws FileAlreadyExistsException if there is allready an file with this
	 *                                    name
	 */
	public void rename(String newName) throws IOException, FileAlreadyExistsException {
		move(new NFile(getParent(), newName));
	}

	/**
	 * Description : <br>
	 * Writes the bytes bytewhise to the file
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param content all bytes write to the file
	 * @throws IOException all errors my happen
	 */
	public void write(byte[] content) throws IOException {
		if (!exisits())
			save();
		FileOutputStream os = new FileOutputStream(getFile());
		BufferedOutputStream bos = new BufferedOutputStream(os);
		bos.write(content);
		bos.flush();
		bos.close();

	}

	/**
	 * Description : <br>
	 * Reads the bytes all at once from the file. We use the fastest way to read all
	 * bytes from the file at once.<br>
	 * File Reading: <a href=
	 * "http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly">http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly</a>
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @return the content redden from the file
	 * @throws IOException
	 */
	public byte[] read() throws IOException {
		return Files.readAllBytes(file);
	}

	/**
	 * Returns the path to the file.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getPathAsString();
	}

	public String getAbsolutePath() {
		return getPathAsString();
	}

	public void write(Serializable data) throws IOException {
		write(IOUntility.serialize(data));
	}

	public Serializable readData() throws ClassNotFoundException, IOException {
		return IOUntility.deSerialize(read());
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}

	/**
	 * Compares if the content od the stream matches the content of the file
	 * 
	 * @author Nils Brugger
	 * @version 2019-10-21
	 * @param otherSource
	 * @return true if content is the same
	 * @throws IOException
	 */
	public boolean compareContent(InputStream otherSource) throws IOException {
		InputStream myStream = getInputStream();
		int b = myStream.read();
		while (b != -1) {
			if (otherSource.read() != b)
				return false;
			b = myStream.read();
		}
		return true;
	}
	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
	{
		file = Paths.get(aInputStream.readUTF());
	}

	private void writeObject(ObjectOutputStream aOutputStream) throws IOException
	{
		aOutputStream.writeUTF(getAbsolutePath());
	}
}
