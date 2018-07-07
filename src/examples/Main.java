package examples;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

import com.niton.media.audio.AudioFileRecorder;
import com.niton.media.audio.AudioQuality;
import com.niton.media.audio.AudioRecorder;
import com.niton.media.audio.IllegalStateException;
import com.niton.media.audio.nio.PlayMode;
import com.niton.media.audio.nio.basic.MP3Player;
import com.niton.media.crypt.Cluster;
import com.niton.media.crypt.ClusterCryptedInputStream;
import com.niton.media.crypt.ClusterCryptedOutputStream;
import com.niton.media.filesystem.DataStore;
import com.niton.media.filesystem.NFile;
import com.niton.media.json.basic.JsonArray;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonString;
import com.niton.media.json.io.JsonInputStream;
import com.niton.media.json.io.JsonOutputStream;
import com.niton.media.json.io.StringInputStream;
import com.niton.media.json.types.JsonDouble;
import com.niton.media.json.types.JsonInt;
import com.niton.media.json.types.JsonSerialObject;
import com.niton.media.json.types.advanced.AdaptiveJsonValue;
import com.niton.media.visual.Canvas;
import com.niton.media.visual.JNetworkPanel;

import javazoom.jl.decoder.JavaLayerException;

public class Main {

	public static void main(String[] args) throws Exception {
	}


	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-06-04
	 * @throws IOException
	 */
	private static void testCryStream() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ClusterCryptedOutputStream cos = new ClusterCryptedOutputStream(new byte[] { 120, 2, 3 }, bos, 512);
		DataOutputStream dos = new DataOutputStream(cos);
		dos.writeUTF("I BIMS");
		dos.writeUTF("Doch nicht");
		dos.writeUTF("I BIMS JETZT ODER NICHT SAGEM ES MIR ENTTLICH ICH WILL NICHT"
				+ " MEHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"
				+ "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHERrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"
				+ "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		dos.close();
		byte[] out = bos.toByteArray();
		bos.close();
		System.out.println(new String(out));
		ByteArrayInputStream bis = new ByteArrayInputStream(out);
		ClusterCryptedInputStream cis = new ClusterCryptedInputStream(new byte[] { 120, 2, 3 }, bis, 512);
		DataInputStream dis = new DataInputStream(cis);
		System.out.println(dis.readUTF());
		System.out.println(dis.readUTF());
		System.out.println(dis.readUTF());
		dis.close();
	}

	/**
	 * Description :
	 * 
	 * @author Niton
	 * @version 2018-04-11
	 */
	private static void testCanvas() {

		Canvas c = new Canvas() {
			final double speed = 0.1;
			final Rectangle r = new Rectangle(20, 20, 50, 50);
			boolean grow = false;
			private Color c = Color.GREEN;

			@Override
			public void paint(Graphics2D g, int delta) {
				System.out.println(delta);
				System.out.println(r);
				if (grow) {
					r.setSize((int) (r.width + (delta * speed)), r.height);
					r.setLocation((int) (45 - (r.width / 2)), r.y);
				} else {
					r.setSize((int) (r.width - (delta * speed)), r.height);
					r.setLocation((int) (20 + ((50 - r.width) / 2)), r.y);
				}
				if (r.getWidth() <= 0)
					grow = true;
				if (r.getWidth() >= 50)
					grow = false;
				paintOval(r, g, 5, c);
				try {
					Thread.sleep(38);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		final JFrame frame = new JFrame("Net");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 1));
		frame.add(c);
		// frame.setUndecorated(true);
		frame.setLocation(0, 0);
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
	}

	/**
	 * Description :
	 * 
	 * @author Niton
	 * @version 2018-04-08
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws IOException
	 */
	private static void testRecord() throws InterruptedException, LineUnavailableException, IOException {
		NFile sound = new NFile("E:\\audio.wav");
		sound.save();
		AudioRecorder rec = new AudioFileRecorder(AudioQuality.VERY_LOW, sound);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					rec.record();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		Thread.sleep(10000);
		rec.stopReccord();
	}

	public static void testNetwork() {
		final JNetworkPanel jn = new JNetworkPanel();
		final JFrame frame = new JFrame("Net");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 1));
		frame.add(jn);
		frame.setUndecorated(true);
		frame.setLocation(0, 0);
		frame.setSize(1920, 1080);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		jn.spawnBallz(200);
		jn.setSpeed(3);
		jn.setConDist(200);
		jn.setBack1(Color.BLACK);
		jn.setBack2(Color.black);
		jn.setDots(Color.WHITE);
		new Thread(new Runnable() {
			int c = 0;

			@Override
			public void run() {
				while (frame.isVisible()) {
					if (c % 10 == 0) {
						jn.spawnBallz(1);
					}
					jn.repaint();
					c++;
					try {
						Thread.sleep(1000 / 30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
