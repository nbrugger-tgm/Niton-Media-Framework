package com.unity.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.niton.media.audio.AudioQuality;
import com.niton.media.audio.nio.basic.PlayState;
import com.niton.media.crypt.Cluster;
import com.niton.media.filesystem.NFile;

/**
 * This is the ExtremeTest Class
 * @author Nils
 * @version 2018-06-09
 */
public class ExtremeTest {
//	private byte[] data = new byte[12];
	public ArrayList<String> liste = new ArrayList<>();
	public ArrayList<AudioQuality> qualitys = new ArrayList<>();
	public ArrayList<Cluster> clusters = new ArrayList<>();
	
	/**
	 * Creates an Instance of ExtremeTest.java
	 * @author Nils
	 * @version 2018-06-09
	 */
	public ExtremeTest(String name) {
//		Random r = new Random();
//		r.nextBytes(data);
		liste.add("xnxx");
		liste.add("pornhub");
		qualitys.add(AudioQuality.HIGH);
		qualitys.add(AudioQuality.VERY_LOW);
		clusters.add(new Cluster(2, (byte) 1));
	}
}

