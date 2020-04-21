package examples;

import com.niton.media.audio.AudioFileRecorder;
import com.niton.media.audio.AudioQuality;
import com.niton.media.audio.AudioRecorder;
import com.niton.media.audio.IllegalStateException;
import com.niton.media.crypt.ClusterCryptedInputStream;
import com.niton.media.crypt.ClusterCryptedOutputStream;
import com.niton.media.filesystem.Directory;
import com.niton.media.filesystem.NFile;
import com.niton.media.visual.Canvas;
import com.niton.media.visual.JNetworkPanel;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws Exception {
//		testFolderCopy();
//		ryanTest();
//		testCanvas();
		System.out.println("888b      88 88                                   88b           d88                     88 88                      db        88888888ba  88\r\n" + 
				"8888b     88 \"\"   ,d                              888b         d888                     88 \"\"                     d88b       88      \"8b 88\r\n" + 
				"88 `8b    88      88                              88`8b       d8'88                     88                       d8'`8b      88      ,8P 88\r\n" + 
				"88  `8b   88 88 MM88MMM ,adPPYba,  8b,dPPYba,     88 `8b     d8' 88  ,adPPYba,  ,adPPYb,88 88 ,adPPYYba,        d8'  `8b     88aaaaaa8P' 88\r\n" + 
				"88   `8b  88 88   88   a8\"     \"8a 88P'   `\"8a    88  `8b   d8'  88 a8P_____88 a8\"    `Y88 88 \"\"     `Y8       d8YaaaaY8b    88\"\"\"\"\"\"'   88\r\n" + 
				"88    `8b 88 88   88   8b       d8 88       88    88   `8b d8'   88 8PP\"\"\"\"\"\"\" 8b       88 88 ,adPPPPP88      d8\"\"\"\"\"\"\"\"8b   88          88\r\n" + 
				"88     `8888 88   88,  \"8a,   ,a8\" 88       88    88    `888'    88 \"8b,   ,aa \"8a,   ,d88 88 88,    ,88     d8'        `8b  88          88\r\n" + 
				"88      `888 88   \"Y888 `\"YbbdP\"'  88       88    88     `8'     88  `\"Ybbd8\"'  `\"8bbdP\"Y8 88 `\"8bbdP\"Y8    d8'          `8b 88          88");
		System.out.println("____________________________________________________________________________________________________________________________________________");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("GitHub Account : https://github.com/nbrugger-tgm");
		System.out.println("GitHub Repo    : https://github.com/nbrugger-tgm/Niton-Media-Framework");
		testCanvas();
	}

	/**
	 * Description : 
	 * @author Nils
	 * @version 2018-07-20
	 * @throws IOException 
	 */
	private static void testFolderCopy() throws IOException {
		Directory d = new Directory("D:\\Desktop\\from");
		Directory target = new Directory("D:\\Desktop\\backup");
//		Directory todel = target.addDir("backup");
//		todel.delete();
		d.copyReplace(target);
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
			final double speed = 0.075;
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
