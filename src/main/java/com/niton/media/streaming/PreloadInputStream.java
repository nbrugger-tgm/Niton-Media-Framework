package com.niton.media.streaming;

import com.niton.media.annotations.Coverage;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static java.lang.Math.min;

/**
 * A stream that preloads content. Other than the {@link java.io.BufferedInputStream} it holds multiple buffers that are consumed one after another
 *
 * ASCII visualisation
 * <pre>
 *     Preloaded chunks:
 *     +----+----+----+----+----+
 *     | C1 | C2 | C3 | C4 | C5 |
 *     +----+----+----+----+----+
 *
 *     Chunk in use
 *     +----+
 *     | C0 |
 *     +----+
 * </pre>
 * <i>There is a Queue of loaded chunks (C1 to C5). And one chunk that is used for reading (C0)</i>
 * <pre>
 *     Preloaded chunks:
 *     +----+----+----+----+----+
 *     | C2 | C3 | C4 | C5 | DL |
 *     +----+----+----+----+----+
 *
 *     Chunk in use
 *     +----+
 *     | C1 |
 *     +----+
 * </pre>
 * <i>As soon as the current reading chunk C0 is completely consumed the next chunk becomes the reading one. Also a new chunk is downloaded (DL) to replace the missing/consumed chunk</i>
 * <p>
 *     This way fluctuations in reading speed doesnt affects the output rate stability.
 * </p>
 * <b>
 *     <p>
 *         But keep in mind that this Preloading needs multithreading -> an additional thread that
 *         handles the downloading of new chunks.
 *         Using {@link #preloadChunks()} and {@link #preloadSingleChunk()} are <i>blocking</i> operations that will download
 *         the new chunk(s)
 *     </p>
 * </b>
 * <p>
 *     This class does support marking if the source supports it. But it has to be kept in mind
 *     that reseting invalidates all chunks so it will create a certain downtime
 * </p>
 * <h3>Performance:</h3>
 * <p>
 *     The reading performance will be better if there is a {@link java.io.BufferedInputStream}
 *     before and after this stream <b>with a buffer size matching the chunk size</b>
 * </p>
 */
@Coverage(methods = 0.91,lines = 0.87)
public class PreloadInputStream extends FilterInputStream {
	private final int                   chunkSize;
	private       byte[]                currentChunk;
	private       int                   currentChunkMarker = 0;
	private final BlockingDeque<byte[]> chunks;
	private       boolean               ended = false;
	/**
	 * Creates a Preload Stream with 50 x 0.1MB chunks
	 * @param source the stream to download from
	 */
	public PreloadInputStream(InputStream source){
		this(source,1024*100,50);
	}

	/**
	 * @param source the stream to use as source
	 * @param chunkSize the size of a chunk
	 * @param chunkCount the maximum number of preloaded chunks
	 */
	public PreloadInputStream(InputStream source, int chunkSize, int chunkCount){
		super(source);
		this.chunkSize  = chunkSize;
		if(chunkSize < 1)
			throw new IllegalArgumentException("chunk-size needs to be at least 1");
		chunks          = new LinkedBlockingDeque<>(chunkCount);
	}
	@Override
	public int read() throws IOException {
		if(currentChunk == null || currentChunkMarker == currentChunk.length) { //if current chunk is consumed
			if(currentChunk != null && currentChunk.length < chunkSize) {
				ended = true;
				return -1;   //if the current chunk is smaller than it should be (and not null) the stream ended -> -1
			}
			useNextChunk();
		}
		if(currentChunk.length == 0) {
			ended = true;
			return -1;
		}

		//This is the bit-whise operation to make a byte to an int
		return currentChunk[currentChunkMarker++]&0xff;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if(ended)
			return -1;
		int ac = 0;//accumulator -> bytes read
		int copySize; // outside of loop for performance
		while(len > 0){
			if(currentChunk == null || currentChunkMarker == currentChunk.length) { //if current chunk is consumed
				if(currentChunk != null && currentChunk.length < chunkSize) {
					ended = true;   //if the current chunk is smaller than it should be (and not null) the stream ended
					break;
				}
				useNextChunk();
			}
			if(currentChunk.length == 0){
				ended = true;
				break;
			}
			copySize = min(currentChunk.length-currentChunkMarker,len);
			System.arraycopy(currentChunk,currentChunkMarker,b,off+ac,copySize);
			ac += copySize;
			len -= copySize;
			currentChunkMarker += copySize;
		}
		if(ended && ac == 0)
			return -1;
		return ac;
	}

	/**
	 * <b>Only for internal use</b>
	 * <p>
	 *     Switches to the next chunk and waits if no one is available
	 * </p>
	 */
	private void useNextChunk() {
		try {
			synchronized (chunks) {
				currentChunk = chunks.take();
			}
			currentChunkMarker = 0;
		} catch (InterruptedException e) {
			throw new RuntimeException("A reading thread using PreloadInputStream was interrupted");//TODO: better error handling concept?
		}
	}

	/**
	 * Fills up all missing chunks. <i>blocking & synchronized</i>
	 * @return the number of chunks loaded, negative if the stream ended (eg. -6 means 6 chunks loaded and the source stream ended).
	 */
	public int preloadChunks() throws IOException {
		int i = 0;
		synchronized (chunks) {
			while (chunks.remainingCapacity() > 0) {
				if (!preloadSingleChunk())
					return (i+1)*-1;
				i++;
			}
		}
		return i;
	}

	/**
	 * Fills up one missing chunk and blocks if it is there are already enough chunks
	 * @return false if the source stream ended
	 */
	public boolean preloadSingleChunk() throws IOException {
		byte[] chunk = new byte[chunkSize];
		int resSize = super.in.read(chunk);
		try {
			if(resSize == -1)
				chunks.put(new byte[0]);
			else if(resSize != chunkSize)
				chunks.put(Arrays.copyOf(chunk,resSize));
			else
			chunks.put(chunk);
		} catch (InterruptedException e) {
			throw new RuntimeException(
					"A thread downloading for a PreloadInputStream was interrupted"
			);
		}
		return resSize == chunkSize;
	}

	/**
	 * @return the count of preloaded -> ready to use chunks
	 */
	public int getPreloadedChunkCount(){
		return chunks.size();
	}
	public int getMaxChunks(){
		return chunks.size()+chunks.remainingCapacity();
	}
	public int getChunkSize() {
		return chunkSize;
	}

	/**
	 * Skips n bytes. Unlike with other stream types it has not too much benefit of doing it as the
	 * amount of skipped bytes is most likely already read so there is no saving of bandwidth for
	 * example
	 * @return the amount of bytes that was actually skipped
	 */
	@Override
	public long skip(long n){
		long skipped = 0;
		while(n > 0){
			if(currentChunk == null || currentChunkMarker == currentChunk.length) { //if current chunk is consumed
				if(currentChunk != null && currentChunk.length < chunkSize)
					ended = true;   //if the current chunk is smaller than it should be (and not null) the stream ended
				useNextChunk();
			}
			if(currentChunk.length == 0)
				ended = true;
			if(ended)
				break;
			long skip = min(n,currentChunk.length-currentChunkMarker);
			n -= skip;
			skipped += skip;
			currentChunkMarker += skip;
		}
		return skipped;
	}

	@Override
	public int available() throws IOException {
		synchronized (chunks) {
			int bytes = currentChunk == null ? 0 : currentChunk.length-currentChunkMarker;
			if (chunks.size() == 0)
				return bytes;
			if (chunks.size() == 1)
				return bytes + chunks.peekLast().length;
			return bytes + (chunks.size() - 1) * chunkSize + chunks.peekLast().length;
		}
	}

	@Override
	public boolean markSupported() {
		return false; //Todo: implement limited markSupport
	}

	@Override
	public synchronized void mark(int readlimit) {
		if(markSupported())
			throw new RuntimeException("Not implemented");
		else
			throw new RuntimeException("Marking not supported");
	}

	private boolean closed;
	@Override
	public void close() throws IOException {
		super.close();
		closed = true;
	}

	private static int N = 1;
	/**
	 * The thread that does the preloading
	 */
	public class Downloader extends Thread {
		private Exception exception = null;
		private final Object lock = new Object();
		private boolean pause = false;
		public Downloader(){
			super("Preload Stream Downloader "+N++);
		}
		public Downloader(String name){
			super(name);
		}

		@Override
		public void run() {
			try {
				while(!closed && preloadSingleChunk()){
					if(pause) {
						pause = false;
						synchronized (lock){
							lock.wait();
						}
					}
				}
			} catch (IOException | InterruptedException e) {
				exception = e;
			}
		}

		public void pause(){
			pause = true;
		}
		public void unpause(){
			pause = false;
			synchronized (lock) {
				lock.notify();
			}
		}

		/**
		 * @return null if no exception was thrown, elsewhen the exception that ended the thread
		 */
		public Exception getEndException() {
			return exception;
		}
	}
}
