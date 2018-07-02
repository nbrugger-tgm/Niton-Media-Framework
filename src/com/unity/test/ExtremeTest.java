package com.unity.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.niton.media.State;
import com.niton.media.audio.AudioQuality;
import com.niton.media.audio.nio.basic.PlayState;
import com.niton.media.crypt.Cluster;
import com.niton.media.filesystem.NFile;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonString;

/**
 * This is the ExtremeTest Class
 * @author Nils
 * @version 2018-06-09
 */
public class ExtremeTest {
	private int x = 10;
	private boolean boll = false;
	private PlayState state = PlayState.FINISHED;
	private int[] array = {
			1,2,4
	};
	private Cluster c = new Cluster(3, (byte) 3);
	private ArrayList<State> list  = new ArrayList<>();
	private PlayState[] states = {
			PlayState.FINISHED,
			PlayState.PAUSED
	};
	private B b = new B();
	JsonString s = new JsonString("json");
	ExtremeTest t =  null;
	private byte[] empty = null;
	private HashMap<String, String> testMap = new HashMap<>();
	private Object[] obArray = {
			new B(),
			new A(),
			new JsonObject(),
			java.lang.Thread.State.BLOCKED
	};
	private ArrayList<Object> allArrayList = new ArrayList<>();
	
	/**
	 * Creates an Instance of ExtremeTest.java
	 * @author Nils
	 * @version 2018-06-09
	 */
	public ExtremeTest(String xxx) {
		list.add(State.NOT_RUNNING);
		list.add(State.PAUSED);
		testMap.put("nils", "brugger");
		testMap.put("anna", "hickel");
		allArrayList.add(new JsonString("abcdefString"));
		allArrayList.add(new A());
		allArrayList.add(new B());
	}
}

