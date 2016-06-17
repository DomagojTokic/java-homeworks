package hr.fer.zemris.java.tecaj.hw07.crypto.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw07.crypto.Crypto;

@SuppressWarnings("javadoc")
public class HexToByteTest {

	
	@Test
	public void testHexToByte1() {
		// number 0 in ASCII hex is 30
		byte[] a = Crypto.hextobyte("30");
		assertArrayEquals("0".getBytes(), new String(a).getBytes());
	}
	
	@Test
	public void testHexToByte2() {
		// numbers 0-9 in ASCII hex are 30-39
		byte[] a = Crypto.hextobyte("30313233343536373839");
		assertArrayEquals("0123456789".getBytes(), new String(a).getBytes());
	}
	
	@Test
	public void testHexToByte3() {
		// using ASCII table, and testing case sensitivity
		byte[] a = Crypto.hextobyte("4a41564120746563616A");
		assertArrayEquals("JAVA tecaj".getBytes(), new String(a).getBytes());
	}
	
	@Test
	public void testHexToByteEmpty() {
		byte[] a = Crypto.hextobyte("");
		assertArrayEquals("".getBytes(), new String(a).getBytes());
	}
	
	@Test
	public void testHexToByteOutOfRadix() {
		byte[] a = Crypto.hextobyte("ZZ");
		assertArrayEquals(null, a);
	}
	
	@Test
	public void testHexToByteIgnoreLastChar() {
		// since it is uneven number of hex chars, the last one is being ignored
		byte[] a = Crypto.hextobyte("4a41564120746563616Af");
		assertArrayEquals("JAVA tecaj".getBytes(), new String(a).getBytes());
	}

}
