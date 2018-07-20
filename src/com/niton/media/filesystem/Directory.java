package com.niton.media.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * This is the Directory Class Representing a container of Files and other
 * Directorys
 * 
 * @author Nils
 * @version 2017-08-28
 */
public class Directory {
	private Path file;

	/**
	 * Creates an Direectory with a specific path 
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @param path
	 *            the path to the Directory
	 */
	public Directory(String path) {
		this.file = Paths.get(path);
	}

	/**
	 * Creates an Directory with an Java NIO Path
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @param path
	 *            the path to the Directory
	 */
	public Directory(Path path) {
		file = path;
	}

	/**
	 * Creates an Instance of Directory.java
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @param path
	 *            the old file object to loacate the File
	 */
	public Directory(File path) {
		file = path.toPath();
	}

	/**
	 * 
	 * Creates an Instance of Directory.java
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @param dir
	 *            the parent Directory of this Directory
	 * @param fullName
	 *            the name of the Directory
	 */
	public Directory(Directory dir, String fullName) {
		this.file = Paths.get(dir.getPathAsString(), fullName);
	}

	/**
	 * 
	 * Creates an Instance of Directory.java
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @param dir
	 *            the parent Directory of this Directory
	 * @param path
	 *            the following directories to create the path
	 */
	public Directory(Directory dir, String... paths) {
		String slash = System.getProperty("file.separator");
		String path = "";
		for (int i = 0; i < paths.length; i++) {
			path += paths[i] + (i + 1 < paths.length ? slash : "");
		}
		file = Paths.get(path);
		this.file = Paths.get(dir.getPathAsString(), path);
	}
	
	/**
	 * Returns the java NIO Path object used as base for NFile
	 * @author Nils
	 * @version 2018-06-30
	 * @return the NIO Path
	 */
	public Path getPath() {
		return file;
	}

	
	
	/**
	 * Description : Returns the absolute Path to the Directory on your computer
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @return the absolute Path
	 */
	public String getPathAsString() {
		return file.toString();
	}

	public Directory getParent() {
		return new Directory(getPath().getParent());
	}

	/**
	 * Description : <i>Mainly for windows.</i> <br>
	 * In case of the path : <b>D:\Data\ThisDir</b> the result will be : <b>D:</b>
	 * or <b>D:\</b>
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @return the root Directory of the Dir
	 */
	public Path getRoot() {
		return file.getRoot();
	}

	/**
	 * Example: <i>\home\niton\Schreibtisch\Dir</i> -> 3
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @return how deep the file is in the path
	 */
	public int getDeepnes() {
		return file.getNameCount();
	}

	public String getName() {
		return file.getFileName().toString();
	}

	/**
	 * <b><i>RECURSIVE</i></b> searches and returns all sub sub and sub sub
	 * Files and Folders
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @return ALL contained Files and Directory's
	 */
	public ArrayList<Path> getSubs() {
		return getSubFiles(this);
	}

	/**
	 * Description : Returns all children which are direct in the Folder<br>
	 * It is <i><b>not</b></i> recursive!
	 * @author Nils Brugger
	 * @version 2018-07-20
	 * @return the children files and directories
	 */
	public ArrayList<Path> getChildren() {
		ArrayList<Path> back = new ArrayList<Path>();
		for (File file : this.getPath().toFile().listFiles()) {
			back.add(file.toPath());
		}
		return back;
	}

	/**
	 * @author Niton
	 * @version 2018-04-05
	 * @return all files direct in the folder
	 */
	public NFile[] getSubFiles() {
		File[] files = this.getPath().toFile().listFiles();
		ArrayList<NFile> list = new ArrayList<NFile>();
		for (File file : files) {
			if (!file.isDirectory())
				list.add(new NFile(file));
		}

		NFile[] back = new NFile[list.size()];
		for (int i = 0; i < back.length; i++) {
			back[i] = list.get(i);
		}

		return back;
	}

	/**
	 * <b><i>RECURSIVE</i></b>
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @return the total RECURSIVE count of files and directorys
	 */
	public int getSubCount() {
		return getSubFiles(this).size();
	}

	/**
	 * Description : Only returns the <i>Folders/Directorys</i> in this dir
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @return all dirs in this
	 */
	public ArrayList<Directory> getSubFolder() {
		ArrayList<Directory> list = new ArrayList<Directory>();
		for (File f : file.toFile().listFiles()) {
			if (f.isDirectory())
				list.add(new Directory(f));
		}
		return list;
	}

	/**
	 * Creates the folder and all parents if not existing
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @return true on success
	 */
	public boolean save() {
		try {
			Files.createDirectories(file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Removes all Files in it <b>RECURSIVLY</b>
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @throws IOException
	 */
	public void delete() throws IOException {
		for (NFile nf : getSubFiles()) {
			nf.delete();
		}
		for (Directory dir : getSubFolder()) {
			dir.delete();
		}
		Files.delete(file);
	}

	public NFile addAndSaveFile(String name, String ending) {
		NFile back = new NFile(this, name, ending);
		try {
			back.save();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return back;
	}

	public NFile addAndSaveFile(String name) {
		NFile back = new NFile(this, name);
		try {
			back.save();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return back;
	}

	public Directory addAndSaveDir(String name) throws IOException {
		Directory back = new Directory(this, name);
		back.save();
		return back;
	}

	public Directory addDir(String name) {
		Directory back = new Directory(this, name);
		return back;
	}

	public NFile addFile(String name, String ending) {
		NFile back = new NFile(this, name, ending);
		return back;
	}
	
	public NFile addFile(String name) {
		NFile back = new NFile(this, name);
		return back;
	}
	/**
	 * Changes the location of the folder in the file system.<br>
	 * The new location is the folder it will be afterwards.<br>
	 * <b>Example:</b>
	 * You are about to move the folder <code>D:\A</code> into the folder <code>D:\B</code><br>
	 * The new path to the folder A will be <code>D:\A\B</code>
	 * <pre>
	 * {@link Directory} toMove = new {@link Directory}("D:\A");
	 * {@link Directory} targetFolder = new {@link Directory}("D:\B");
	 * {@link Directory} folderAfter = targetFolder.addDir("A");
	 * toMove.move(folderAfter);
	 * </pre>
	 * this metthod uses recursion.<br>
	 * <b><i>DO NOT USE THIS METHOD IF YOU HAVE A LINK FOLDER IN THE FOLDER YOU WANT TO MOVE TO THE FOLDER ITSELF. IT WILL CAUSE A {@link StackOverflowError}</i></b>
	 * @author Nils
	 * @version 2018-07-20
	 * @param newLocation the new loccation of the folder its self
	 * @throws IOException 
	 */
	public void move(Directory newLocation) throws IOException {
		newLocation.save();
		ArrayList<Path> childs = getChildren();
		for (Path path : childs) {
			if(Files.isDirectory(path)) {
				Directory d = new Directory(file);
				d.move(newLocation.addDir(d.getName()));
			}else {
				NFile file = new NFile(path);
				file.move(newLocation.addFile(file.getName()+"."+file.getEnding()));
			}
		}
	}
	
	/**
	 * Changes the location of the folder in the file system. And overrides the newLocation if necessary (possible loss of data)<br>
	 * The new location is the folder it will be afterwards.<br>
	 * <b>Example:</b>
	 * You are about to move the folder <code>D:\A</code> into the folder <code>D:\B</code><br>
	 * The new path to the folder A will be <code>D:\A\B</code>
	 * <pre>
	 * {@link Directory} toMove = new {@link Directory}("D:\A");
	 * {@link Directory} targetFolder = new {@link Directory}("D:\B");
	 * {@link Directory} folderAfter = targetFolder.addDir("A");
	 * toMove.move(folderAfter);
	 * </pre>
	 * this metthod uses recursion.<br>
	 * <b><i>DO NOT USE THIS METHOD IF YOU HAVE A LINK FOLDER IN THE FOLDER YOU WANT TO MOVE TO THE FOLDER ITSELF. IT WILL CAUSE A {@link StackOverflowError}</i></b>
	 * @author Nils
	 * @version 2018-07-20
	 * @param newLocation the new loccation of the folder its self
	 * @throws IOException 
	 */
	public void moveReplace(Directory newLocation) throws IOException {
		newLocation.save();
		ArrayList<Path> childs = getChildren();
		for (Path path : childs) {
			if(Files.isDirectory(path)) {
				Directory d = new Directory(file);
				d.moveReplace(newLocation.addDir(d.getName()));
			}else {
				NFile file = new NFile(path);
				file.moveReplace(newLocation.addFile(file.getName(),file.getEnding()));
			}
		}
	}

	
	/**
	 * Copies the folder in the file system.<br>
	 * The new location is the folder it will be afterwards.<br>
	 * <b>Example:</b>
	 * You are about to move the folder <code>D:\A</code> into the folder <code>D:\B</code><br>
	 * The new path to the folder A will be <code>D:\A\B</code>
	 * <pre>
	 * {@link Directory} toCopy = new {@link Directory}("D:\A");
	 * {@link Directory} targetFolder = new {@link Directory}("D:\B");
	 * {@link Directory} folderAfter = targetFolder.addDir("A");
	 * toMove.copy(folderAfter);
	 * </pre>
	 * this metthod uses recursion.<br>
	 * <b><i>DO NOT USE THIS METHOD IF YOU HAVE A LINK FOLDER IN THE FOLDER YOU WANT TO MOVE TO THE FOLDER ITSELF. IT WILL CAUSE A {@link StackOverflowError}</i></b>
	 * @author Nils
	 * @version 2018-07-20
	 * @param newLocation a new loccation of a copy from folder its self
	 * @throws IOException 
	 */
	public void copy(Directory newLocation) throws IOException {
		newLocation.save();
		ArrayList<Path> childs = getChildren();
		for (Path path : childs) {
			if(Files.isDirectory(path)) {
				Directory d = new Directory(file);
				d.copy(newLocation.addDir(d.getName()));
			}else {
				NFile file = new NFile(path);
				file.copy(newLocation.addFile(file.getName()+"."+file.getEnding()));
			}
		}
	}
	
	/**
	 * Changes the location of the folder in the file system. And overrides the newLocation if necessary (possible loss of data)<br>
	 * The new location is the folder it will be afterwards.<br>
	 * <b>Example:</b>
	 * You are about to move the folder <code>D:\A</code> into the folder <code>D:\B</code><br>
	 * The new path to the folder A will be <code>D:\A\B</code>
	 * <pre>
	 * {@link Directory} toMove = new {@link Directory}("D:\A");
	 * {@link Directory} targetFolder = new {@link Directory}("D:\B");
	 * {@link Directory} folderAfter = targetFolder.addDir("A");
	 * toMove.move(folderAfter);
	 * </pre>
	 * this metthod uses recursion.<br>
	 * <b><i>DO NOT USE THIS METHOD IF YOU HAVE A LINK FOLDER IN THE FOLDER YOU WANT TO MOVE TO THE FOLDER ITSELF. IT WILL CAUSE A {@link StackOverflowError}</i></b>
	 * @author Nils
	 * @version 2018-07-20
	 * @param newLocation a new loccation of the copy from folder its self
	 * @throws IOException 
	 */
	public void copyReplace(Directory newLocation) throws IOException {
		newLocation.save();
		ArrayList<Path> childs = getChildren();
		for (Path path : childs) {
			if(Files.isDirectory(path)) {
				Directory d = new Directory(path);
				d.copyReplace(newLocation.addDir(d.getName()));
			}else {
				NFile file = new NFile(path);
				file.copyReplace(newLocation.addFile(file.getName(),file.getEnding()));
			}
		}
	}
	
	/**
	 * <b><i>RECURSIVE</i></b>
	 * @author Niton
	 * @version 2018-04-05
	 * @param d
	 * @return
	 */
	private static ArrayList<Path> getSubFiles(Directory d) {
		ArrayList<Path> back = new ArrayList<Path>();
		Path pd = d.getPath();
		File ff = pd.toFile();
		File[] list = ff.listFiles();
		if (list == null) {
			return back;
		}
		for (File f : list) {
			Path p = f.toPath();

			back.add(p);
			if (Files.isDirectory(p)) {
				for (Path subPath : getSubFiles(new Directory(p))) {
					back.add(subPath);
				}
			}
		}
		return back;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getPathAsString();
	}

}
