package hr.fer.zemris.java.tecaj.hw5.collections;
import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

@SuppressWarnings("javadoc")
public class SimpleHashtableTests {

	@Test
	public void putInMap() {
		SimpleHashtable<Integer, String> map = new SimpleHashtable<>();
		map.put(5, "Ivan");
		assertEquals(1, map.size());
		assertEquals("Ivan", map.get(5));
		assertTrue(map.containsKey(5));
		System.out.println(map.toString());
	}
	
	@Test
	public void putMultipleInMap(){
		SimpleHashtable<String, Double> map = new SimpleHashtable<>(20);
		map.put("Jedan"	, 1.00);
		map.put("Jedan i pol" , 1.5);
		map.put("Jedan i pol" , 2.0);
		map.put("Pedeset", 50.00);
		assertEquals(3, map.size());
		assertTrue(map.containsValue(2.0));
	}
	
	@Test
	public void constructorTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		assertEquals(0, table.size());
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void testDimensionLessThanOneConstructor() {
        new SimpleHashtable<>(0);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testPutElementKeyNull() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put(null, 10);
    }
	
	@Test
    public void testPutElements() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ana",10);
		assertEquals(1, table.size());
		assertEquals(10, Integer.parseInt(String.valueOf(table.get("Ana"))));
    }
	
	@Test
    public void testGetReturnsNull() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ana",null);
		assertEquals(null, table.get(null));
		assertEquals(null,table.get("Ana"));
		assertEquals(null,table.get("Matej"));
    }
	
	@Test
    public void testSize() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ana",null);
		assertEquals(1, table.size());
    }
	
	@Test
    public void testContainsKey() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ana",new Integer(10));
		assertTrue(table.containsKey("Ana"));
		assertFalse(table.containsKey("Matej"));
		assertFalse(table.containsKey(null));
    }
	
	@Test
	public void testContainsValue() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ana",new Integer(10));
		table.put("Matej",null);
		table.put("MArko",89);
		table.put("bauh d", 846);
		table.put("4atadcqdcej",534);
		table.put("Fatcsacaej",8);
		table.put("Xefgthvatej",22);
		assertTrue(table.containsValue(new Integer(10)));
		assertFalse(table.containsValue(new Integer(11)));
		assertTrue(table.containsValue(8));
		assertTrue(table.containsValue(534));
		assertTrue(table.containsValue(89));
		assertTrue(table.containsValue(22));
		assertTrue(table.containsValue(846));
		
		assertTrue(table.containsValue(null));
    }
	
	@Test
	public void testRemove() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ana",null);
		table.remove("Ana");
		assertEquals(0, table.size());
		assertFalse(table.containsKey("Ana"));
		table.put("Ana",null);
		table.remove(null);
		assertTrue(table.containsKey("Ana"));
    }
	
	@Test
	public void testIsEmpty() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		assertTrue(table.isEmpty());
		assertFalse(!table.isEmpty());
    }
	
	@Test
	public void testClear() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		table.put("Ana",null);
		table.put("Matej", new Integer(5));
		table.clear();
		assertEquals(0, table.size());
		assertTrue(table.isEmpty());
    }
	
	@Test
	public void testIterator() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put("Ana",new Integer(10));
		for (SimpleHashtable.TableEntry<String, Integer> pair : table) {
			assertEquals("Ana", pair.getKey());
		}
    }
	
	@Test
	public void testIteratorRemove() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put("Ana",new Integer(10));
		table.put("Matej",new Integer(10));
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ana")) {
				iter.remove();
			}
		}
		assertFalse(table.containsKey("Ana"));
    }
	
	@Test(expected = IllegalStateException.class)
	public void testIteratorTwoRemoves() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put("Ana",new Integer(10));
		table.put("Matej",new Integer(10));
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ana")) {
				iter.remove();
				iter.remove();
			}
		}
    }
	
	@Test(expected = ConcurrentModificationException.class)
	public void testIteratorExernalModification() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put("Ana",new Integer(10));
		table.put("Matej",new Integer(10));
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ana")) {
				table.remove("Ana");
			}
		}
    }
	
}
