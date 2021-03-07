package com.niton.media.crypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

public class SimpleCluster {
	public static byte[] encrypt(byte[] key, byte[] data) {
		int blockSideSize = (key[0]);
		if(blockSideSize < 0)
			blockSideSize *= -2;
		if(blockSideSize == 0)
			blockSideSize += 2;
		int blockSize = blockSideSize * blockSideSize;
		int dataSize = data.length;
		int padding = blockSize - (dataSize % blockSize);
		if (padding == 0)
			padding = blockSize;
		int paddedSize = dataSize + padding;
		int blockCount = paddedSize / blockSize;
		byte[] paddedData = new byte[paddedSize];
		Random r = new Random();
		for (int i = 0; i < paddedSize; i++) {
			paddedData[i] = (byte) (i < data.length ? data[i] : r.nextInt(Byte.MAX_VALUE));
		}
		int times = 1;
		while (padding > 0) {
			byte toAdd;
			if (padding == Byte.MAX_VALUE) {
				paddedData[paddedSize - times] = (byte) padding;
				times++;
				paddedData[paddedSize - times] = 0;
				padding = 0;
				break;
			} else if (padding > Byte.MAX_VALUE)
				toAdd = Byte.MAX_VALUE;
			else
				toAdd = (byte) padding;
			paddedData[paddedSize - times] = toAdd;
			times++;
			padding -= toAdd;
		}
		int abs = 0;
		int keyPos = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		for (int i = 0; i < blockCount; i++) {
			// Initialisize Block
			byte[] block = new byte[blockSize];
			for (int j = 0; j < block.length; j++) {
				block[j] = paddedData[abs];
				abs++;
			}
			// Chipher Block
			Cluster c = new Cluster(block);
			for (int j = 0; j < blockSideSize * 2; j++) {
				if (j % 2 == 0)
					c.pushColum(j / 2, key[keyPos] + 1);
				else
					c.pushRow((j - 1) / 2, key[keyPos] + 1);
				keyPos++;
				keyPos %= key.length;
			}
			block = c.getByteArray();
			block = chipher(block, key[keyPos]);
			keyPos++;
			keyPos %= key.length;
			try {
				bos.write(block);
			} catch (IOException e) {
			}
		}
		byte[] encrypted = bos.toByteArray();
		encrypted = chipher(encrypted, key[key.length - 1]);
		return encrypted;
	}

	public static byte[] chipher(byte[] block, byte key) {
		byte[] orig = new byte[block.length];
		for (int i = 0; i < orig.length; i++) {
			orig[i] = block[i];
		}
		for (int i = 0; i < orig.length; i++) {
			block[i] = (byte) (orig[i] + orig[(i + 1) % orig.length]);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(block);
		} catch (IOException e) {
		}
		bos.write(key + orig[0]);
		return bos.toByteArray();
	}

	public static byte[] decrypt(byte[] key, byte[] data) {
		try {
			data = deChipher(data, key[key.length - 1]);
			int sideSize = (key[0]);
			if(sideSize < 0)
				sideSize *= -2;
			if(sideSize == 0)
				sideSize += 2;
			int blockSize = sideSize * sideSize;
			int blockCount = data.length / (blockSize + 1);
			int abs = data.length-1;
			int keyPos = (blockCount * ((sideSize * 2) + 1)) % key.length;
			byte[] bos = new byte[blockCount*blockSize];
			int bosMark = bos.length-1;
			for (int i = 0; i < blockCount; i++) {
				byte[] block = new byte[blockSize + 1];
				for (int j = block.length-1; j >= 0 ; j--) {
					block[j] = data[abs];
					abs--;
				}
				keyPos--;
				if (keyPos < 0)
					keyPos = key.length - 1;
				block = deChipher(block, key[keyPos]);
				// keyPos--;
				// if(keyPos < 0)
				// keyPos = key.length-1;
				Cluster c = new Cluster(block);
				for (int j = sideSize * 2; j > 0; j--) {
					keyPos--;
					if (keyPos < 0)
						keyPos = key.length - 1;
					if (j % 2 == 0) {
						c.pushRow(((j - 1) / 2), (key[keyPos] + 1) * -1);
					} else {
						c.pushColum(((j - 1) / 2), (key[keyPos] + 1) * -1);
					}
				}
				block = c.getByteArray();
				for(int j = block.length-1;j>=0;j--) {
					bos[bosMark] = block[j]; 
					bosMark--;
				}
			}
			data = bos;
			long padding = 0;
			int pos = data.length - 1;
			while (data[pos] == Byte.MAX_VALUE) {
				padding += Byte.MAX_VALUE;
				pos--;
			}
			padding += data[pos];
			byte[] depaddedData = new byte[(int) (data.length - padding)];
			for (int i = 0; i < depaddedData.length; i++) {
				depaddedData[i] = data[i];
			}
			return depaddedData;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Arrays.toString(key));
			System.exit(0);
			return null;
		}
	}

	public static byte[] deChipher(byte[] block, byte key) {
		byte[] deChiph = new byte[block.length - 1];
		block[block.length - 1] = (byte) (block[block.length - 1] - key);
		for (int i = deChiph.length - 1; i > -1; i--) {
			deChiph[i] = (byte) (block[i] - block[i + 1]);
			block[i] = deChiph[i];
		}
		return deChiph;
	}
}