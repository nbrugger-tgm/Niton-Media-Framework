package com.niton.media;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.niton.media.filesystem.NFile;
import com.niton.media.json.basic.JsonPair;
import com.niton.media.json.basic.JsonString;

/**
 * This is the ResurceLoader Class
 * 
 * @author Nils
 * @version 2017-08-16
 */
public final class IOUntility {
	/**
	 * Creates an input stream of an file in the Jar file The path have to start<br>
	 * with an '/' otherwise we add it! You can use this formats:<br>
	 * <br>
	 * <i> <b>"/path/image.png"</b><br>
	 * "path/image.png"<br>
	 * "path\\image.png"<br>
	 * "\\path\\image.png"<br>
	 * <br>
	 * </i> Bold is default
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param path
	 *            the path to the inner source
	 * @return the input stream to the <i>path</i> file
	 */
	public static final InputStream getInputStream(String path) {
		path = path.replace('\\', '/');
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		InputStream s = IOUntility.class.getResourceAsStream(path);
		return s;
	}

	/**
	 * Creates an input stream of an file in the Jar file.<br>
	 * The package uses '.' as file seperator have to start with an '/' otherwise we
	 * add it!<br>
	 * You can use this formats:<br>
	 * <br>
	 * <i> <b>"com.niton.sources", "image.png"</b><br>
	 * "com/niton/sources", "image.png"<br>
	 * "com\\niton\\sources", "image.png"<br>
	 * "\\com\\niton\\sources", "image.png"<br>
	 * "/com/niton/sources", "image.png"<br>
	 * <br>
	 * </i> Bold is default
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param pack
	 *            the Java package the file is included in
	 * @param filename
	 *            the name of the file itsself
	 * @return the input stream to the <i>path</i> file
	 */
	public static final InputStream getInputStream(String pack, String filename) {
		String path = pack + "/" + filename.replace('.', '?');
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		path = path.replace('.', '/');
		path = path.replace('\\', '/');
		path = path.replace('?', '.');
		InputStream s = IOUntility.class.getResourceAsStream(path);
		return s;
	}

	/**
	 * Reads the input stream bitewhise and returns the complete content<br>
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param stream
	 *            the source of the data
	 * @return the complete content of the stream
	 * @throws IOException
	 *             if there is any exception reading the stream
	 */
	public final static byte[] getContent(InputStream stream) throws IOException {
		synchronized (stream) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte b = 0;
			while (b >= 0) {
				b = (byte) stream.read();
				bos.write(b);
			}
			byte[] content = bos.toByteArray();
			return content;
		}
	}

	/**
	 * Serializes the given object and returns it as an byte array!<br>
	 * The object has to be <i>Serializeable</i>
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param serial
	 *            the object to serialize
	 * @return the serialized object
	 * @throws IOException
	 *             if there is an error writing into streams
	 */
	public static final byte[] serialize(Serializable serial) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(serial);
		return baos.toByteArray();
	}
	
	/**
	 * Serializes the given object and writes it into the given stream!<br>
	 * The object has to be <i>Serializeable</i>
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param serial
	 *            the object to serialize
	 * @return the serialized object
	 * @throws IOException
	 *             if there is an error writing into streams
	 */
	public static final void serialize(Serializable serial,OutputStream stream) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(stream);
		oos.writeObject(serial);
		oos.flush();
		oos.close();
	}

	/**
	 * Creates an Serializable object out of an byte Array!
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param serialized
	 *            the serialized object
	 * @return the object of the data
	 * @throws IOException
	 *             if there is an error reading/writing in streams
	 * @throws ClassNotFoundException
	 *             if the class to deserialize is not found
	 */
	public static final Serializable deSerialize(byte[] serialized) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bin = new ByteArrayInputStream(serialized);
		ObjectInputStream ois = new ObjectInputStream(bin);
		Serializable ser = (Serializable) ois.readObject();
		ois.close();
		return ser;
	}

	/**
	 * Creates an Serializable object out of an byte Array!
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param serialized
	 *            the serialized object
	 * @return the object of the data
	 * @throws IOException
	 *             if there is an error reading/writing in streams
	 * @throws ClassNotFoundException
	 *             if the class to deserialize is not found
	 */
	@SuppressWarnings("unchecked")
	public  static  final <T extends Serializable> T deSerialize(InputStream stream) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(stream);
		Serializable ser = (Serializable) ois.readObject();
		ois.close();
		return (T) ser;
	}
	
	/**
	 * Copy a file from the JAR to the hard disk.<br>
	 * Reading and writing byte-wise.<br>
	 * Only use '/' no '\' and not use '.'
	 * 
	 * @param target
	 *            the target where the file should be saved at
	 * @param source
	 *            the name of the resource (have to be an file) starting with an '/'
	 * @throws Exception
	 *             if something went wrong during copying
	 */
	public static void copyFromJar(NFile target, String source) throws Exception {
		InputStream stream = getInputStream(source);
		try{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buffer = new byte[0xFFFF];
			for (int len; (len = stream.read(buffer)) != -1;)
				os.write(buffer, 0, len);
			os.flush();
			byte[] array = os.toByteArray();
			Files.write(target.getPath(), array);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * <b>Description :</b> Copys from in to out without storing more than 512 Kb in
	 * the RAM
	 * 
	 * @author Nils
	 * @version 2017-08-28
	 * @param in
	 * @param out
	 */
	public static void copyBigStream(InputStream in, OutputStream out) {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(out);
			BufferedInputStream bin = new BufferedInputStream(in);
			byte[] buffer = new byte[512 * 1000];
			short i = 0;
			int b = bin.read(buffer);
			while (b != -1) {
				i++;
				if (i == 20) {
					i = 0;
					System.gc();
				}
				bos.write(buffer);
				b = in.read(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads a resurce out of the Jar File <b>byte-whise</b><br>
	 * Only use '/' no '\' or '.'
	 * 
	 * @param source
	 * @return the content of the file as byte[]
	 * @author Nils
	 */
	public static byte[] readOutOfJarFile(String source) {
		InputStream stream = getInputStream(source);
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buffer = new byte[0xFFFF];
			for (int len; (len = stream.read(buffer)) != -1;)
				os.write(buffer, 0, len);
			os.flush();
			byte[] array = os.toByteArray();
			return array;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Reads an image icon at the path<br>
	 * <i><b>OUT OF THE JAR</b></i><br>
	 * To read a image from the Hard Disk use:
	 * 
	 * <pre>
	 * IconImage imageFromDisk = getImage(new NFile(path));
	 * </pre>
	 * 
	 * @param path
	 *            the inner absolute path of the image
	 * @return the read image or <b>null</b> on Error/Exception
	 */
	public static ImageIcon getImage(String path) {
		ImageIcon icon;
		try {
			icon = new ImageIcon(readOutOfJarFile(path));
		} catch (Exception e) {
			return null;
		}
		return icon;
	}

	/**
	 * Reads an image from the <b>Hard Disk</b>.<br>
	 * Do not throw any exception. Returns <b>null</b> instead.<br>
	 * 
	 * @param file
	 *            the NFile on the Disk to read from
	 * @return the read Image or null on error such as {@link FileNotFoundException}
	 */
	public static ImageIcon getImage(NFile file) {
		ImageIcon icon;
		try {
			icon = new ImageIcon(Files.readAllBytes(file.getPath()));
		} catch (Exception e) {
			return null;
		}
		return icon;
	}

	/**
	 * Reads an image optional from the hard disk or from the jar.<br>
	 * This method do NOT defines the location of the image by the path.<br>
	 * It returns an scaled instance of the image by given width and height.<br>
	 * Do not use negative values.<br>
	 * 
	 * @param path
	 *            the path to the Image
	 * @param inJar
	 *            true if the file is located in the jar file (in the project)
	 * @param width
	 *            the width the image is scaled to in px
	 * @param height
	 *            the height the image is scaled to in px
	 * @return the scaled instance of the Image
	 * @throws Exception
	 *             any error is thrown even {@link NullPointerException}
	 */
	public static ImageIcon getImage(String path, boolean inJar, int width, int height) throws Exception {
		ImageIcon icon;
		try {
			if (inJar)
				icon = getImage(path);
			else
				icon = getImage(new NFile(path));
			ImageIcon scaled = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			icon = scaled;
		} catch (Exception e) {
			throw e;
		}
		return icon;
	}

	/**
	 * Reads an image optional from the hard disk or from the jar.<br>
	 * This method do NOT defines the location of the image by the path.<br>
	 * It returns an scaled instance of the image by given the scale factor.<br>
	 * Do not use negative values.<br>
	 * 
	 * @param path
	 *            the path to the Image
	 * @param inJar
	 *            true if the file is located in the jar file (in the project)
	 * @param scale
	 *            the factor which is used as <code>height*scale</code> and
	 *            <code>width*scale</code>
	 * @return the scaled instance of the Image
	 * @throws Exception
	 *             any error is thrown even {@link NullPointerException}
	 */
	public static ImageIcon getImage(String path, boolean inJar, double scale) throws Exception {
		ImageIcon icon;
		try {
			if (inJar)
				icon = getImage(path);
			else
				icon = getImage(new NFile(path));
			int width = (int) (icon.getIconWidth() * scale);
			int height = (int) (icon.getIconHeight() * scale);
			ImageIcon scaled = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
			icon = scaled;
		} catch (Exception e) {
			throw e;
		}
		return icon;
	}

	/**
	 * Scales the image to the width and height
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param original
	 *            the image to scale
	 * @param width
	 *            the new width of the image
	 * @param height
	 *            the new height of the image
	 * @return the scaled instance
	 */
	public static Image scaleImage(Image original, int width, int height) {
		return original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	/**
	 * Scales the image by the scale Factor
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @param original
	 *            the image to scale
	 * @param factor
	 *            the factor to multiply with (1 = original size) (2 = double size)
	 *            and 0.5 is half size
	 * @return the scaled instance
	 */
	public static Image scaleImage(Image original, double factor) {
		return original.getScaledInstance((int)(original.getWidth(null)*factor), (int)(original.getHeight(null)*factor), Image.SCALE_SMOOTH);
	}

	/**
	 * Reads and parses an XML Document inside a jar<br>
	 * 
	 * @param path
	 *            is used in the method {@link IOUntility#getInputStream(String)}
	 *            to get the File as Stream
	 * @return the parsed XML Document or null on error
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static Document getXML(String path) throws SAXException, IOException, ParserConfigurationException {
		return getXML(getInputStream(path));
	}

	/**
	 * Reads and parses an XML Document outside a jar<br>
	 * 
	 * @param path
	 *            is used in the method {@link IOUntility#getInputStream(String)}
	 *            to get the File as Stream
	 * @return the parsed XML Document or null on error
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 */
	public static Document getXML(NFile path) throws IOException, SAXException, ParserConfigurationException {
		return getXML(path.getInputStream());
	}
	/**
	 * Reads and parses an XML Document from an Stream<br>
	 * 
	 * @param path
	 *            is used in the method {@link IOUntility#getInputStream(String)}
	 *            to get the File as Stream
	 * @return the parsed XML Document or null on error
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static Document getXML(InputStream in) throws SAXException, IOException, ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
		return doc;
	}

	public static String getXMLValue(Document dom, String... paths) {
		ArrayList<String> path = new ArrayList<String>();
		for (String string : paths) {
			path.add(string);
		}
		String value = "No Value";
		Node parent = dom.getElementsByTagName(path.get(0)).item(0);
		path.remove(0);
		String[] np = (String[]) path.toArray(new String[path.size()]);
		value = getXMLValue(parent, np);
		if (value == null)
			value = "No Value";
		return value;
	}

	public static String getXMLValue(Node n, String... paths) {
		if (paths.length == 0) {
			return n.getNodeValue();
		}
		NodeList list = n.getChildNodes();
		ArrayList<String> path = new ArrayList<String>();
		for (String string : paths) {
			path.add(string);
		}
		String value = null;
		String toSearch = path.get(0);
		path.remove(0);
		paths = (String[]) path.toArray(new String[path.size()]);
		for (int i = 0; i < list.getLength(); i++) {
			Node child = list.item(i);
			if (child.getNodeName().equalsIgnoreCase(toSearch))
				value = getXMLValue(child, paths);
		}
		return value;
	}

	/**
	 * Returns the path to the .jar file
	 * 
	 * @author Nils
	 * @version 2017-08-18
	 * @return the executed file
	 */
	public static File getThisFile() {

		try {
			return new File(IOUntility.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
