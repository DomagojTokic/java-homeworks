package hr.fer.zemris.java.simplecomp.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.simplecomp.impl.RegisterUtil;

@SuppressWarnings("javadoc")
public class SimpleCompTests {

	@Test
	public void testRegisterUtil1() {
		int register = 0x01FFFF00;
		assertEquals(-1, RegisterUtil.getRegisterOffset(register));
		assertTrue(RegisterUtil.isIndirect(register));
		assertEquals(0, RegisterUtil.getRegisterIndex(register));
	}

	@Test
	public void testRegisterUtil2() {
		int register = 0x0000AA0A;
		assertEquals(170, RegisterUtil.getRegisterOffset(register));
		assertFalse(RegisterUtil.isIndirect(register));
		assertEquals(10, RegisterUtil.getRegisterIndex(register));
	}

	@Test
	public void testUnpacking1() {
		int registerDescriptor = 0x1000102;
		assertEquals(2, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(1, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

	@Test
	public void testUnpacking2() {
		int registerDescriptor = 0x1FFFF02;
		assertEquals(2, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(-1, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

	@Test
	public void testUnpacking3() {
		int registerDescriptor = 0x18000FE;
		assertEquals(254, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(-32768, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

	@Test
	public void testUnpacking4() {
		int registerDescriptor = 0x17FFFFF;
		assertEquals(255, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(32767, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

}
