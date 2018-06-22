package com.unity.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.niton.media.audio.nio.basic.PlayState;
import com.niton.media.filesystem.NFile;

/**
 * This is the ExtremeTest Class
 * @author Nils
 * @version 2018-06-09
 */
public class ExtremeTest {
	private byte[] data = new byte[12];
	private String name;
	private ExtremeTest[] sub;
	private ExtremeTest[] sub2;
	private transient NFile file = new NFile("C:","test.txt");
	private ExtremeTest parent;
	private int[][] cluster = new int[2][3];
	public ArrayList<String> liste = new ArrayList<>();
	public HashMap<String, String> rating = new HashMap<>();
	private boolean amICool = false;
	private Object lock = new Object();
	private PlayState state = PlayState.PAUSED;
	
	/**
	 * Creates an Instance of ExtremeTest.java
	 * @author Nils
	 * @version 2018-06-09
	 */
	public ExtremeTest(String name) {
		Random r = new Random();
		r.nextBytes(data);
		this.name = name;
		sub = new ExtremeTest[4];
		liste.add("xnxx");
		liste.add("pornhub");
		rating.put("best", "xnxx");
		rating.put("2.Best", "pornhub");
		rating.put("3.Best", "xHamster");
	}
}

