package com.niton.media.streaming;

import com.niton.media.streaming.PreloadInputStream.Downloader;
import com.niton.media.testing.UnmodifyingInputStreamTest;
import com.niton.media.testing.UnreadableInputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PreloadInputStream")
public class PreloadInputStreamTest {


	@Nested
	@DisplayName("Chunks: 2, Size: 5, Input: 20")
	class ChunkConfigOne extends UnmodifyingInputStreamTest<PreloadInputStream> {
		@Override
		public PreloadInputStream create(InputStream source) {
			PreloadInputStream pis = new PreloadInputStream(source,5,2);
			pis.new Downloader().start();
			return pis;
		}

		@Override
		public int getSampleCount() {
			return 5;
		}

		@Override
		public int getSampleSize() {
			return 20;
		}
	}

	@Nested
	@DisplayName("Chunks: 3, Size: 21, Input: 1024")
	class ChunkConfigTwo extends UnmodifyingInputStreamTest<PreloadInputStream> {
		@Override
		public PreloadInputStream create(InputStream source) {
			PreloadInputStream pis = new PreloadInputStream(source,21,3);
			pis.new Downloader().start();
			return pis;
		}

		@Override
		public int getSampleCount() {
			return 5;
		}

		@Override
		public int getSampleSize() {
			return 1024;
		}
	}

	@Test
	@DisplayName("Blocking")
	void blockOnNoDownload(){
		PreloadInputStream is = new PreloadInputStream(new ByteArrayInputStream(new byte[0]));
		Thread localThread = new Thread(()->{
				assertDoesNotThrow(()->{
						assertEquals(-1, is.read(), "Read on empty stream should be -1");
				},"Reading from ByteArrayInputStream cant throw exception.");
			}
		);
		localThread.start();
		try {
			localThread.join(70);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(localThread.isAlive(),"Thread should be blocked and therefore still be alive");
		Downloader d = is.new Downloader();
		d.start();
		try {
			d.join(70);
			assertFalse(d.isAlive(),"Download thread should die after downloading");
			localThread.join(70);
			assertFalse(localThread.isAlive(),"Thread should die after block is released");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Download pausing")
	void downloadPausing() throws IOException {
		PreloadInputStream is = new PreloadInputStream(new ByteArrayInputStream(new byte[1024*1024]),1024,5);
		Thread localThread = new Thread(()->{
			assertDoesNotThrow(()->{
				is.read();
			},"Reading from ByteArrayInputStream cant throw exception.");
		}
		);
		localThread.start();
		try {
			localThread.join(70);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(localThread.isAlive(),"Thread should be blocked and therefore still be alive");
		Downloader d = is.new Downloader();
		d.start();
		try {
			d.join(70);
			localThread.join(70);
			assertFalse(localThread.isAlive(),"Thread should die after block is released");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		d.pause();
		System.out.println(is.read(new byte[is.available()]));
		System.out.println(is.read(new byte[is.available()]));
		localThread = new Thread(()->{
			assertDoesNotThrow(()->{
				is.read();
			},"Reading from ByteArrayInputStream cant throw exception.");
		}
		);
		localThread.start();
		try {
			localThread.join(70);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(localThread.isAlive(),"Thread should be blocked and therefore still be alive");
		d.unpause();
		try {
			localThread.join(70);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(localThread.isAlive(),"Reading should not block after downloading is unpaused");
		assertNull(d.getEndException());
	}

	@Test
	void closing() throws InterruptedException, IOException {
		PreloadInputStream is = new PreloadInputStream(new ByteArrayInputStream(new byte[1024*1024]),1024,5);
		Downloader d = is.new Downloader();
		d.start();
		d.join(30);
		is.close();
		is.read(new byte[is.available()]);
		is.read(new byte[is.available()]);
		d.join(70);
		assertFalse(d.isAlive());
	}

	@Test
	@DisplayName("Manual chunk loading")
	void preloadChunks() {
		PreloadInputStream is = new PreloadInputStream(new ByteArrayInputStream(new byte[1024]),100,9);
		assertEquals(0,is.getPreloadedChunkCount(),"An initial stream should not have chunks loaded");
		AtomicInteger loaded = new AtomicInteger();
		assertDoesNotThrow(()-> loaded.set(is.preloadChunks()));
		assertEquals(loaded.get(),is.getPreloadedChunkCount(),"The amount of chunks should equal the amount returned on loading");
		assertEquals(9,loaded.get(),"All chunks were empty so 9 chunks should have been loaded");
		try {
			is.read(new byte[900]);//consume all preloaded bytes
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertDoesNotThrow(()->loaded.set(is.preloadChunks()));
		assertEquals(-2,loaded.get(),"A full chunk and a partial (24 size) chunk should have been loaded -> 2 Chunks -> stream end -> -2");
	}

	@Test
	void available(){
		PreloadInputStream is = new PreloadInputStream(new ByteArrayInputStream(new byte[1001]),100,9);
		try {
			assertEquals(100, is.getChunkSize());
			assertEquals(9, is.getMaxChunks());
			is.preloadSingleChunk();
			assertEquals(100,is.available());
			assertEquals(9, is.getMaxChunks());
			is.preloadChunks();
			assertEquals(900,is.available());
			assertEquals(9, is.getMaxChunks());
			is.skip(50);
			assertEquals(850,is.available(),"After skipping bytes, less should be available");
			is.read();
			assertEquals(849,is.available(),"After reading a byte, less should be available");
			is.read(new byte[9]);
			assertEquals(840,is.available(),"After reading 9 bytes, less should be available");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void errorHandling() throws InterruptedException {
		PreloadInputStream pis = new PreloadInputStream(new UnreadableInputStream(),1,1);
		Downloader down = pis.new Downloader();
		down.start();
		down.join();
		assertEquals(IOException.class,down.getEndException().getClass());
	}

	/*
	@ParameterizedTest(name = "Buffering Performance {0} MB")
	@ValueSource(ints = {50,60,90,100,120})
	void bufferingPerformance(int mb) throws IOException, InterruptedException {
		int dataSize                           = mb*1024*1024;//MB
		long baseSpeed = 0,bufferWrong = 0,bufferRight = 0;
		byte[] readBuf                         = new byte[dataSize];
		byte[] src                             = new byte[dataSize];
		for (int i = 0; i < src.length; i++) {
			src[i] = (byte) i;
		}

		for(int i : range(1,5).toArray()) {
			PreloadInputStream is = new PreloadInputStream(new ByteArrayInputStream(src),
			                                               8 * 1024,
			                                               50);
			PreloadInputStream.Downloader down;

			down = is.new Downloader();
			down.pause();
			down.start();
			Thread.sleep(50);//ik this is quick and dirty but 50ms ensures the downloader is started
			long start = System.currentTimeMillis();
			down.unpause();
			is.read(readBuf);
			baseSpeed += System.currentTimeMillis() - start;

			is    = new PreloadInputStream(new BufferedInputStream(new ByteArrayInputStream(src),
			                                                       8 * 1024), 8 * 1024, 50);
			down = is.new Downloader();
			down.pause();
			down.start();
			Thread.sleep(50);//ik this is quick and dirty but 50ms ensures the downloader is started
			start = System.currentTimeMillis();
			down.unpause();
			is.read(readBuf);
			bufferRight += System.currentTimeMillis() - start;

			is    = new PreloadInputStream(new BufferedInputStream(new ByteArrayInputStream(src),
			                                                       8 * 1025), 8 * 1024, 50);
			down = is.new Downloader();
			down.pause();
			down.start();
			Thread.sleep(50);//ik this is quick and dirty but 50ms ensures the downloader is started
			start = System.currentTimeMillis();
			down.unpause();
			is.read(readBuf);
			bufferWrong += System.currentTimeMillis() - start;
		}

		System.out.println(
				"Base      : "+baseSpeed+" ms\n"+
				"Buf. Wrong: "+bufferWrong+" ms\n"+
				"Buf. Right: "+bufferRight+" ms"
		);

		assertTrue(baseSpeed>=bufferRight,"Buffering should be faster than baseline usage");
		assertTrue(bufferWrong>=bufferRight,"Buffering with the right size should improve performance");
	}
 */
}
