package com.unity.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the Server Class
 * @author Nils
 * @version 2018-06-22
 */
public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8888);
		Socket client = server.accept();
		InputStream stream = client.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(stream);
		System.out.println(ois.readObject());
		stream.close();
		server.close();
	}
}

