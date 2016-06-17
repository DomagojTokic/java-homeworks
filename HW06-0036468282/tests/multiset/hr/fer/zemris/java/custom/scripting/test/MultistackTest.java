package hr.fer.zemris.java.custom.scripting.test;

import static org.junit.Assert.*;

import java.awt.geom.Area;
import java.util.EmptyStackException;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

@SuppressWarnings("javadoc")
public class MultistackTest {

	@SuppressWarnings("unused")
	@Test
	public void testWrapperArg1() {
		ValueWrapper wrapper1 = new ValueWrapper(null);
		ValueWrapper wrapper2 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper wrapper3 = new ValueWrapper(Double.valueOf(5.5));
		ValueWrapper wrapper = new ValueWrapper("string");
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testWrapperArg2() {
		ValueWrapper wrapper = new ValueWrapper(new Area());
	}

	@Test
	public void testClassCasting1() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(5));
		Double number = Double.valueOf(5.5);
		wrapper.increment(number);
		assertTrue(wrapper.getValue() instanceof Double);
	}

	@Test
	public void testClassCasting2() {
		ValueWrapper wrapper = new ValueWrapper(Integer.valueOf(5));
		wrapper.increment(6);
		assertTrue(wrapper.getValue() instanceof Integer);
	}

	@Test
	public void testClassCasting3() {
		ValueWrapper wrapper = new ValueWrapper("55.98");
		wrapper.increment(6);
		assertTrue(wrapper.getValue() instanceof Double);
	}

	@Test
	public void testClassCasting4() {
		ValueWrapper wrapper = new ValueWrapper("55.98E-5");
		wrapper.increment(6);
		assertTrue(wrapper.getValue() instanceof Double);
	}

	@Test
	public void testClassCasting5() {
		ValueWrapper wrapper = new ValueWrapper("55.98E-5");
		wrapper.increment("6E-4");
		assertTrue(wrapper.getValue() instanceof Double);
	}

	@Test
	public void testCompare() {
		ValueWrapper wrapper = new ValueWrapper("55.98");
		assertTrue(wrapper.numCompare("60") < 0);
		assertTrue(wrapper.numCompare("1") > 0);
		assertTrue(wrapper.numCompare("55.98") == 0);
	}
	
	@Test
	public void testIncrement(){
		ValueWrapper wrapper = new ValueWrapper(10);
		wrapper.increment(60);
		assertEquals(70, wrapper.getValue());
		assertTrue(wrapper.getValue() instanceof Integer);
	}
	
	@Test
	public void testDecrement(){
		ValueWrapper wrapper = new ValueWrapper("-10");
		wrapper.decrement(60);
		assertEquals(-70, wrapper.getValue());
		assertTrue(wrapper.getValue() instanceof Integer);
	}
	
	@Test
	public void testMultiply(){
		ValueWrapper wrapper = new ValueWrapper("7.5");
		wrapper.multiply(-4);
		assertEquals(-30.0, wrapper.getValue());
		assertTrue(wrapper.getValue() instanceof Double);
	}
	
	@Test
	public void testDivide(){
		ValueWrapper wrapper = new ValueWrapper("55.5");
		wrapper.divide(5);
		assertEquals(11.1, wrapper.getValue());
		assertTrue(wrapper.getValue() instanceof Double);
	}
	
	@Test (expected = ArithmeticException.class)
	public void testDivideByZero1() {
		ValueWrapper wrapper = new ValueWrapper(10);
		wrapper.divide(0.0);
	}
	
	@Test (expected = ArithmeticException.class)
	public void testDivideByZero2() {
		ValueWrapper wrapper = new ValueWrapper(10);
		wrapper.divide(0);
	}
	
	@Test (expected = ArithmeticException.class)
	public void testDivideByZero3() {
		ValueWrapper wrapper = new ValueWrapper(10);
		wrapper.divide(null);
	}	
	
	@Test
	public void testPushAndPop() {
		ObjectMultistack multiStack = new ObjectMultistack();

		multiStack.push("X", new ValueWrapper("1"));
		multiStack.push("X", new ValueWrapper("2"));
		multiStack.push("X", new ValueWrapper("3"));

		multiStack.push("Y", new ValueWrapper("4"));
		multiStack.push("Y", new ValueWrapper("5"));
		multiStack.push("Y", new ValueWrapper("6"));

		assertEquals("3", multiStack.pop("X").getValue());
		assertEquals("2", multiStack.pop("X").getValue());
		assertEquals("1", multiStack.pop("X").getValue());
		assertTrue(multiStack.isEmpty("X"));

		assertEquals("6", multiStack.pop("Y").getValue());
		assertEquals("5", multiStack.pop("Y").getValue());
		assertEquals("4", multiStack.pop("Y").getValue());
		assertTrue(multiStack.isEmpty("Y"));

	}

	@Test(expected = EmptyStackException.class)
	public void testIllegalPop() {
		ObjectMultistack multiStack = new ObjectMultistack();

		multiStack.push("X", new ValueWrapper("1"));
		multiStack.pop("X");
		multiStack.pop("X");
	}
	
	@Test
	public void testPeekAndChange() {
		ObjectMultistack multiStack = new ObjectMultistack();
		
		multiStack.push("X", new ValueWrapper(50.5));
		multiStack.peek("X").divide(5);
		assertEquals(10.1, multiStack.peek("X").getValue());
	}
	
	@Test
	public void testConstructor() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		assertEquals(Integer.valueOf(2000), year.getValue());
		
		ValueWrapper one = new ValueWrapper(Double.valueOf(2000.99));
		assertEquals(Double.valueOf(2000.99), one.getValue());
		
		ValueWrapper two = new ValueWrapper(null);
		assertEquals(null, two.getValue());
	}
	
	@Test
	public void testSetValue() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		year.setValue("Bezveze");
		assertEquals("Bezveze", year.getValue());
	}
	
	@Test
	public void testIncrementTwoIntegers() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		year.increment(Integer.valueOf(50));
		assertEquals(Integer.valueOf(2050), year.getValue());
	}
	
	@Test
	public void testIncrementTwoDoubles() {
		ValueWrapper year = new ValueWrapper(Double.valueOf(25.41));
		year.increment(Double.valueOf(0.09));
		assertEquals(Double.valueOf(25.50), year.getValue());
	}
	
	@Test
	public void testIncrementDoubleAndInteger() {
		ValueWrapper year = new ValueWrapper(Double.valueOf(25.41));
		year.increment(Integer.valueOf(5));
		assertEquals(Double.valueOf(30.41), year.getValue());
	}
	
	@Test
	public void testIncrementNullAndInteger() {
		ValueWrapper year = new ValueWrapper(null);
		year.increment(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), year.getValue());
	}
	
	@Test
	public void testIncrementTwoNull() {
		ValueWrapper year = new ValueWrapper(null);
		year.increment(null);
		assertEquals(Integer.valueOf(0), year.getValue());
	}
	
	@Test
	public void testIntegerAndString() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(5));
		year.increment(String.valueOf(25));
		assertEquals(Integer.valueOf(30), year.getValue());
	}
	
	@Test
	public void testIncrementStringAndDouble() {
		ValueWrapper year = new ValueWrapper(Double.valueOf(25.41));
		year.increment("0.09");
		assertEquals(Double.valueOf(25.50), year.getValue());
	}
	
	@Test
	public void testIncrementStringEAndDouble() {
		ValueWrapper year = new ValueWrapper(Double.valueOf(25.41));
		year.increment("20E-5");
		assertEquals(Double.valueOf(25.41 + 20E-5), year.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void testIncrementStringParseError() {
		ValueWrapper year = new ValueWrapper(Double.valueOf(25.41));
		year.increment("bez");
	}
	
	@Test(expected=RuntimeException.class)
	public void testIncrementStringParseError2() {
		ValueWrapper year = new ValueWrapper(Double.valueOf(25.41));
		year.increment("2.2.3");
	}
	
	@Test(expected=RuntimeException.class)
	public void testWrongType() {
		ValueWrapper year = new ValueWrapper(Long.valueOf(32));
		year.increment("2");
	}
	
	@Test
	public void testDecrement2() {
		ValueWrapper year = new ValueWrapper(Double.valueOf(25.49));
		year.decrement("0.09");
		assertEquals(Double.valueOf(25.40), year.getValue());
	}
	
	@Test
	public void testMultiply2() {
		ValueWrapper year = new ValueWrapper(Double.valueOf(25.49));
		year.multiply("0.09");
		assertEquals(Double.valueOf(2.2941), year.getValue());
	}
	
	@Test
	public void testDivInteger() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(20));
		year.divide("3");
		assertEquals(Integer.valueOf(6), year.getValue());
	}
	
	@Test
	public void testCompareTwoIntegers() {
		ValueWrapper year = new ValueWrapper(Integer.valueOf(25));
		assertTrue(year.numCompare("24") > 0);
		assertTrue(year.numCompare("26") < 0);
		assertTrue(year.numCompare("25") == 0);
	}
	
	@Test
	public void testCompareTwoDoubles() {
		ValueWrapper year = new ValueWrapper(Double.valueOf(25.47));
		assertTrue(year.numCompare("25.46") > 0);
		assertTrue(year.numCompare("25.48") < 0);
		assertTrue(year.numCompare("25.47") == 0);
	}
	
	@Test
	public void testCompareTwoNull() {
		ValueWrapper year = new ValueWrapper(null);
		assertTrue(year.numCompare(null) == 0);
	}
	
	@Test
	public void testMultistackPush() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		assertEquals(false, multistack.isEmpty("year"));
	}
	
	@Test
	public void testMultistackPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		assertEquals(Integer.valueOf(2000), multistack.peek("year").getValue());
		assertEquals(false, multistack.isEmpty("year"));
	}
	
	@Test
	public void testMultistackPushMore() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		
		assertEquals(Integer.valueOf(1900), multistack.peek("year").getValue());
		assertEquals(Integer.valueOf(1900), multistack.pop("year").getValue());
		assertEquals(Integer.valueOf(2000), multistack.peek("year").getValue());
		assertEquals(Integer.valueOf(2000), multistack.pop("year").getValue());
		assertEquals(true, multistack.isEmpty("year"));
	}

}
