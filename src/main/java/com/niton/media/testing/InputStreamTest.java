package com.niton.media.testing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * Class to perform tests for {@link InputStream} implementations.
 * To use this class create a class that extends this one
 */
public abstract class InputStreamTest<S extends FilterInputStream> {
	/**
	 * Creates the input stream to test
	 *
	 * @param source the source stream to use
	 * @return the input stream to test, has to use source
	 */
	public abstract S create(InputStream source);

	/**
	 * @return a map of datasamples where the key is the input and the value the expected output of
	 * the stream
	 */
	public abstract Map<byte[], byte[]> getDataSamples();

	@TestFactory
	@DisplayName("Single Byte reading")
	Stream<DynamicNode> testSingleByteRead() {
		return test(this::testSingleByteRead);
	}
	@TestFactory
	@DisplayName("Multi Byte reading")
	Stream<DynamicNode> testMultiByteRead(){
		return test(this::testMultiByteRead);
	}
	@TestFactory
	@DisplayName("Mixed Byte reading")
	Stream<DynamicNode> testMixedByteRead(){
		return test(this::testMixedByteRead);
	}
	@TestFactory
	@DisplayName("Mixed Byte reading (reverse)")
	Stream<DynamicNode> testMixedByteReadReverseOrder(){
		return test(this::testMixedByteReadOrder2);
	}
	@TestFactory
	@DisplayName("Return -1 at stream end (single-reads)")
	Stream<DynamicNode> testMinusOneAtEnd(){
		return test(this::testMinusOneAtEndSingleReads);
	}
	@TestFactory
	@DisplayName("Return -1 at stream end (multi-reads)")
	Stream<DynamicNode> testMinusOneAtEndMulti(){
		return test(this::testMinusOneAtEnd);
	}

	@TestFactory
	@DisplayName("mark() & reset()")
	Stream<DynamicNode> testMarkingSupport(){
		return test(this::markingSupport);
	}
	@TestFactory
	Stream<DynamicNode> skip(){
		return test(this::testSkip);
	}
	private void markingSupport(Map.Entry<byte[],byte[]> entry) throws IOException {
		InputStream is   = create(new ByteArrayInputStream(entry.getKey()));
		if(is.markSupported()){
			//The documentation says MIGHT throws an exception so i cant do this
			//assertThrows(IOException.class, is::reset,
			//             "Resetting an unmarked stream should throw an exception");
			assertDoesNotThrow(()->is.mark(5));
			is.read(new byte[entry.getValue().length-1]);
			is.reset();
			byte[] read = new byte[entry.getValue().length];
			assertEquals(entry.getValue().length, is.read(read),
			             "After resetting the read should read the same lenght");
			assertArrayEquals(entry.getValue(),read,
			                  "Re-reading after reset should not change the expected output");
			is.reset();
			is.mark(1);
			is.read(new byte[2]);
			//it again just "might" throws an exception -> nothing i can test for
			//assertThrows(IOException.class, is::reset,
			//             "Resetting after read limit was surpassed shouldn't work");
		}else //Sadly nothing i can do to test here
			;
	}

	private void testSkip(Map.Entry<byte[],byte[]> entry) throws IOException{
		InputStream stream = create(new ByteArrayInputStream(entry.getKey()));
		assertEquals(entry.getValue().length,stream.skip(entry.getValue().length),
		             "The stream should be able to skip its complete content");
		stream.close();
		stream = create(new ByteArrayInputStream(entry.getKey()));
		assertEquals(0,stream.skip(-12),"Using a negative number has to return 0");
	}


	void testSingleByteRead(Map.Entry<byte[], byte[]> entry) throws IOException {
       InputStream is   = create(new ByteArrayInputStream(entry.getKey()));
       int         read = 0;
       while (true) {
           int val = is.read();
           if (val == -1) {
               break;
           }
           assertEquals(entry.getValue()[read],
                        (byte) val,
                        "Stream byte at pos " + read + " was wrong");
           read++;
       }
       assertEquals(entry.getValue().length,
                    read,
                    "The stream read an unexpected number of bytes");

	}

	void testMultiByteRead(Map.Entry<byte[], byte[]> entry) throws IOException {
			                   InputStream is   = create(new ByteArrayInputStream(entry.getKey()));
			                   byte[]      read = new byte[entry.getValue().length];
			                   is.read(read);
			                   assertArrayEquals(entry.getValue(),
			                                read,
			                                "The stream reads a different result than expected");
	}

	void testMinusOneAtEnd(Map.Entry<byte[], byte[]> entry) {
		assertDoesNotThrow(() -> {
			                   InputStream is   = create(new ByteArrayInputStream(entry.getKey()));
			                   byte[]      read = new byte[entry.getValue().length];
			                   is.read(read);
			                   assertEquals(-1,
			                                is.read(read),
			                                "Reading after the stream is consumed should return -1");
		                   }
				, "Reading from an ByteArrayInputStream should not throw an exception"
		);
	}

	void testMinusOneAtEndSingleReads(Map.Entry<byte[], byte[]> entry) throws IOException {
           InputStream is = create(new ByteArrayInputStream(entry.getKey()));
           for (int i = 0; i < entry.getValue().length; i++) {
               is.read();
           }
           assertEquals(-1, is.read(), "Reading after the stream is consumed should return -1");
	}

	private void testMixedByteRead(Map.Entry<byte[], byte[]> entry) throws IOException {
		InputStream is   = create(new ByteArrayInputStream(entry.getKey()));
		int         read = 0;
		for (int i = 0; i < entry.getValue().length/2; i++) {
			int val = is.read();
			assertEquals(entry.getValue()[read],
			             (byte) val,
			             "Stream byte at pos " + read + " was wrong");
			read++;
		}
		byte[] remaining = new byte[entry.getValue().length-read];
		is.read(remaining);
		for (int i = read; i < entry.getValue().length; i++) {
			assertEquals(entry.getValue()[i],
			             remaining[i-read],
			             "Stream byte at pos " + read + " was wrong");
		}
		assertEquals(-1,is.read(),"After all bytes are read the stream should read -1");
	}

	private void testMixedByteReadOrder2(Map.Entry<byte[], byte[]> entry) throws IOException {
		InputStream is   = create(new ByteArrayInputStream(entry.getKey()));
		byte[] remaining = new byte[entry.getValue().length/2];
		is.read(remaining);
		for (int i = 0; i < remaining.length; i++) {
			assertEquals(entry.getValue()[i],
			             remaining[i],
			             "Stream byte at pos " + i + " was wrong");
		}
		int         read = remaining.length;
		for (int i = 0; i < entry.getValue().length/2; i++) {
			int val = is.read();
			assertEquals(entry.getValue()[read+i],
			             (byte) val,
			             "Stream byte at pos " + read + " was wrong");
		}

		assertEquals(-1,is.read(),"After all bytes are read the stream should read -1");
	}
	@FunctionalInterface
	interface SampleTest {
		void test(Map.Entry<byte[], byte[]> sample) throws IOException;
	}

	Stream<DynamicNode> test(SampleTest testFunction) {
		AtomicInteger i = new AtomicInteger(1);
		return
				getDataSamples()
						.entrySet()
						.stream()
						.map(
							(e) -> dynamicTest(
								     "Data sample " + (i.getAndIncrement()),
								     () -> assertDoesNotThrow(() -> testFunction.test(e),
								                              "Reading from an ByteArrayInputStream should not throw an exception")
							)
						)
		;
	}
}
