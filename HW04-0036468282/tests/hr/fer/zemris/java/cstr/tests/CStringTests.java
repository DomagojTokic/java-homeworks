package hr.fer.zemris.java.cstr.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import hr.fer.zemris.java.cstr.CString;

@SuppressWarnings("javadoc")
public class CStringTests {

	@Test
	public void testConstructor1() {
		char[] array = { 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 't', 'e', 's',
				't' };
		CString string = new CString(array);
		assertEquals('T', string.charAt(0));
		assertEquals('t', string.charAt(11));
	}

	@Test
	public void testConstructor2() {
		char[] array = { 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 't', 'e', 's',
				't' };
		// string is "is test"
		CString string = new CString(array, 5, 7);
		assertEquals('i', string.charAt(0));
		assertEquals('t', string.charAt(6));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testOutOfBound() {
		char[] array = { 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 't', 'e', 's',
				't' };
		// string is "is te"
		CString string = new CString(array, 5, 5);
		assertEquals('i', string.charAt(0));
		assertEquals('t', string.charAt(5));
	}

	@Test
	public void testFromString() {
		CString string = CString.fromString("This is test");
		assertEquals('T', string.charAt(0));
		assertEquals('t', string.charAt(11));
		assertEquals(12, string.length());
	}

	@Test
	public void testToCharArray() {
		char[] array = { 'T', 'h', 'i', 's', ' ', 'i', 's', ' ', 't', 'e', 's',
				't' };
		// string is "is te"
		CString string = new CString(array, 5, 5);
		assertArrayEquals(new char[] { 'i', 's', ' ', 't', 'e' },
				string.toCharArray());
	}

	@Test
	public void testToString() {
		CString string = CString.fromString("This is test");
		assertEquals("This is test", string.toString());
	}

	@Test
	public void testEmptyCString() {
		CString string = new CString(new char[0]);
		CString string2 = new CString(new char[0], 0, 0);
		CString string3 = CString.fromString("");
		assertEquals(0, string.toCharArray().length);
		assertEquals("", string.toString());
		assertEquals(0, string2.toCharArray().length);
		assertEquals("", string2.toString());
		assertEquals(0, string3.toCharArray().length);
		assertEquals("", string3.toString());
	}

	@Test
	public void testIndexOf() {
		CString string = CString.fromString("This is test");
		assertEquals(0, string.indexOf('T'));
		assertEquals(1, string.indexOf('h'));
		assertEquals(4, string.indexOf(' '));
		assertEquals(8, string.indexOf('t'));
		assertEquals(-1, string.indexOf('x'));
	}

	@Test
	public void testStartsWith() {
		CString string = CString.fromString("This is test");
		CString string2 = CString.fromString("This");
		assertTrue(string.startsWith(string2));
		CString string3 = CString.fromString("this");
		assertFalse(string.startsWith(string3));
	}

	@Test
	public void testStartsWithEmptyString() {
		CString string = CString.fromString("This is test");
		CString string2 = CString.fromString("");
		assertTrue(string.startsWith(string2));
	}

	@Test
	public void testEndsWith() {
		CString string = CString.fromString("This is test");
		CString string2 = CString.fromString("test");
		assertTrue(string.endsWith(string2));
		CString string3 = CString.fromString("test.");
		assertFalse(string.endsWith(string3));
	}

	@Test
	public void testEndsWithEmptyString() {
		CString string = CString.fromString("This is test");
		CString string2 = CString.fromString("");
		assertTrue(string.endsWith(string2));
	}

	@Test
	public void testContains() {
		CString string = CString
				.fromString("This is unnecessarily long test string");
		assertTrue(string.contains(CString.fromString("long test string")));
		assertFalse(string.contains(CString.fromString("Long test string")));
	}

	@Test
	public void testContainsEmptyString() {
		CString string = CString.fromString("This is test");
		CString string2 = CString.fromString("");
		assertTrue(string.contains(string2));
	}

	@Test
	public void testSubstringWithToString() {
		CString string = CString.fromString("This is test");
		assertEquals("is test", string.substring(5, 12).toString());
	}

	@Test
	public void testSubstringWithToCharArray() {
		CString string = CString.fromString("This is test");
		CString string2 = CString.fromString("is test");
		assertArrayEquals(string2.toCharArray(),
				string.substring(5, 12).toCharArray());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testInvalidSubstring() {
		CString string = CString.fromString("This is test");
		assertEquals("is test", string.substring(5, 13).toString());
	}

	@Test
	public void testLeft() {
		CString string = CString.fromString("This is test");
		assertEquals("This is", string.left(7).toString());
	}

	@Test
	public void testRight() {
		CString string = CString.fromString("This is test");
		assertEquals("is test", string.right(7).toString());
	}

	@Test
	public void testAdd() {
		CString string = CString.fromString("This is test");
		CString addition = CString.fromString(" and addition");
		assertEquals("This is test and addition",
				string.add(addition).toString());
	}

	@Test
	public void testReplaceAllChars() {
		CString string = CString.fromString("This is test");
		assertEquals("Thxs xs test", string.replaceAll('i', 'x').toString());
	}

	@Test
	public void testReplaceAllStrings() {
		CString string = CString.fromString("This is test");
		CString oldStr = CString.fromString("is");
		CString newStr = CString.fromString("*replaced*");
		assertEquals("Th*replaced* *replaced* test",
				string.replaceAll(oldStr, newStr).toString());
	}

	@Test
	public void testReplaceEmptyStrings1() {
		CString string = CString.fromString("This is test");
		CString oldStr = CString.fromString("");
		CString newStr = CString.fromString("*");
		assertEquals("*T*h*i*s* *i*s* *t*e*s*t*",
				string.replaceAll(oldStr, newStr).toString());
	}

	@Test
	public void testReplaceEmptyStrings2() {
		CString string = CString.fromString("This is test");
		CString oldStr = CString.fromString("");
		CString newStr = CString.fromString("****");
		assertEquals(
				"****T****h****i****s**** ****i****s**** ****t****e****s****t****",
				string.replaceAll(oldStr, newStr).toString());
	}

	@Test
	public void testReplaceEmptyStrings3() {
		CString string = CString.fromString("This is test");
		CString oldStr = CString.fromString("");
		CString newStr = CString.fromString("ab");
		assertEquals("abTabhabiabsab abiabsab abtabeabsabtab",
				string.replaceAll(oldStr, newStr).toString());
	}
	
	@Test
	public void testReplaceEmptyStrings4() {
		CString string = CString.fromString("This is test");
		CString oldStr = CString.fromString("");
		CString newStr = CString.fromString("");
		assertEquals("This is test",
				string.replaceAll(oldStr, newStr).toString());
	}
	
	@Test
	public void testReplaceEmptyStrings5() {
		CString string = CString.fromString("This is test");
		CString oldStr = CString.fromString("i");
		CString newStr = CString.fromString("");
		assertEquals("Ths s test",
				string.replaceAll(oldStr, newStr).toString());
	}
	
	@Test
	public void testReplaceEmptyStrings6() {
		CString string = CString.fromString("ababab");
		CString oldStr = CString.fromString("ab");
		CString newStr = CString.fromString("abab");
		assertEquals("abababababab",
				string.replaceAll(oldStr, newStr).toString());
		assertEquals(12, string.replaceAll(oldStr, newStr).length());
	}
	
	@Test
    public void testRegularCreation() {
        CString string = new CString(String.valueOf("Štefica").toCharArray(), 1, 4);
        assertEquals("Invalid string.", "tefi", string.toString());
        assertEquals("Invalid length.", 4, string.length());
        assertEquals("Invalid character.", 'e', string.charAt(1));
 
        CString string2 = new CString(String.valueOf("Štefica").toCharArray());
        assertEquals("Expected 'Štefica'", "Štefica", string2.toString());
 
        CString string3 = new CString(string2);
        assertEquals("Expected 'Štefica'", "Štefica", string3.toString());
    }
 
    @Test(expected = NullPointerException.class)
    public void testNullInputOnFirstConstructor() {
        // must throw!
        new CString(null, 2, 5);
    }
 
    @Test(expected = IndexOutOfBoundsException.class)
    public void testInvalidMargins() {
        // must throw!
        new CString(String.valueOf("Štefica").toCharArray(), 2, 6);
    }
 
    @Test
    public void testValidMargins1() {
        CString string = new CString(String.valueOf("Štefica").toCharArray(), 1, 6);
        assertEquals("Expected 'tefica'.", "tefica", string.toString());
        assertEquals("Expected 't'.", 't', string.charAt(0));
        assertEquals("Expected 'f'.", 'f', string.charAt(2));
        assertEquals("Expected 'i'.", 'i', string.charAt(3));
        assertEquals("Expected 'a'.", 'a', string.charAt(5));
    }
 
    @Test
    public void testValidMargins2() {
        CString string = new CString(String.valueOf("Štefica").toCharArray(), 0, 3);
        assertEquals("Expected 'Šte'.", "Šte", string.toString());
        assertEquals("Expected 'Š'.", 'Š', string.charAt(0));
        assertEquals("Expected 'e'.", 'e', string.charAt(2));
        assertEquals("Expected 'e'.", 'e', string.charAt(string.length() - 1));
    }
 
    @Test
    public void testValidMargins3() {
        CString string = new CString(String.valueOf("Štefica").toCharArray(), 0, 7);
        assertEquals("Expected 'Šte'.", "Štefica", string.toString());
        assertEquals("Expected 'Š'.", 'Š', string.charAt(0));
        assertEquals("Expected 'e'.", 'e', string.charAt(2));
        assertEquals("Expected 'a'.", 'a', string.charAt(string.length() - 1));
    }
 
    @Test
    public void testLengthsStartsEnds() {
        CString string = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23);
        assertEquals("Expected 3x 'Štefica' with whitespaces.", "Štefica Štefica Štefica", string.toString());
        assertEquals("Expected 23.", 23, string.length());
        assertEquals("Expected 23.", 23, string.toCharArray().length);
        assertEquals("Expected ' '.", ' ', string.charAt(7));
 
        assertEquals("Expected 'true'", true,
                string.endsWith(new CString(String.valueOf("Štefica").toCharArray(), 0, 7)));
        assertEquals("Expected 'true'", true,
                string.startsWith(new CString(String.valueOf("Štefica").toCharArray(), 0, 7)));
 
        assertEquals("Expected 'false'", false,
                string.endsWith(new CString(String.valueOf("Šfetica").toCharArray(), 0, 7)));
        assertEquals("Expected 'false'", false,
                string.endsWith(new CString(String.valueOf("Šfe").toCharArray(), 0, 3)));
    }
 
    @Test
    public void testLeft2() {
        CString string = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23);
        assertEquals("Expected 'Šte'.", "Šte", string.left(3).toString());
        assertEquals("Expected 'Štefica '.", "Štefica ", string.left(8).toString());
        assertEquals("Expected ''.", "", string.left(0).toString());
        assertEquals("Expected 'Štefica Štefica Štefica'.", "Štefica Štefica Štefica", string.left(23).toString());
    }
 
    @Test
    public void testRight2() {
        CString string = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23);
        assertEquals("Expected 'ica'.", "ica", string.right(3).toString());
        assertEquals("Expected ' Štefica'.", " Štefica", string.right(8).toString());
        assertEquals("Expected ''.", "", string.right(0).toString());
        assertEquals("Expected 'Štefica Štefica Štefica'.", "Štefica Štefica Štefica", string.right(23).toString());
    }
 
    @Test(expected = IndexOutOfBoundsException.class)
    public void testLeftInvalidIndexes1() {
        // must throw!
        new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).left(-1);
 
    }
 
    @Test(expected = IndexOutOfBoundsException.class)
    public void testLeftInvalidIndexes2() {
        // must throw!
        new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).left(24);
    }
 
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRightInvalidIndexes1() {
        // must throw!
        new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).right(-1);
    }
 
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRightInvalidIndexes2() {
        // must throw!
        new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).right(24);
    }
 
    @Test(expected = NullPointerException.class)
    public void testStartsWithNullValue() {
        // must throw!
        new CString(String.valueOf(" Štefica ").toCharArray()).startsWith(null);
    }
 
    @Test(expected = NullPointerException.class)
    public void testEndsWithNullValue() {
        // must throw!
        new CString(String.valueOf("  Štefica  ").toCharArray()).endsWith(null);
    }
 
    @Test(expected = NullPointerException.class)
    public void testFromStringWithNullValue() {
        // must throw!
        CString.fromString(null);
    }
 
    @Test
    public void testFromStringWithRegularValue() {
        CString string1 = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 7, 9);
        CString string2 = new CString(String.valueOf(" Štefica i Marko  ").toCharArray());
        CString string3 = new CString(string1.right(7));
 
        assertEquals("Expected '0'", 0, string1.toString().compareTo(CString.fromString(" Štefica ").toString()));
        assertEquals("Expected '0'", 0,
                string2.toString().compareTo(CString.fromString(" Štefica i Marko  ").toString()));
        assertEquals("Expected '0'", 0, string3.toString().compareTo(CString.fromString("tefica ").toString()));
    }
 
    @Test
    public void testContainsWithRegularValue() {
        CString string = new CString(String.valueOf(" Štefica i Marko  ").toCharArray());
 
        CString test1 = CString.fromString("Štefica");
        CString test2 = CString.fromString("Marko");
        CString test3 = CString.fromString(" Štefica i Marko  ");
        CString test4 = CString.fromString(" Šte");
        CString test5 = CString.fromString("rko ");
        CString test6 = CString.fromString("Ante");
 
        assertEquals("Expected 'true'", true, string.contains(test1));
        assertEquals("Expected 'true'", true, string.contains(test2));
        assertEquals("Expected 'true'", true, string.contains(test3));
        assertEquals("Expected 'true'", true, string.contains(test4));
        assertEquals("Expected 'true'", true, string.contains(test5));
        assertEquals("Expected 'true'", false, string.contains(test6));
 
    }
 
    @Test(expected = NullPointerException.class)
    public void testContainsWithNullValue() {
        // must throw!
        new CString(String.valueOf(" Štefica i Marko  ").toCharArray()).contains(null);
    }
 
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubstringWithInvalidArguments1() {
        // must throw!
        CString.fromString("Štefica i Marko").substring(-1, 3);
    }
 
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubstringWithInvalidArguments2() {
        // must throw!
        CString.fromString("Štefica i Marko").substring(2, 1);
    }
 
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSubstringWithInvalidArguments3() {
        // must throw!
        CString.fromString("Štefica i Marko").substring(2, 16);
    }
 
    @Test
    public void testSubstringWithValidArguments() {
        CString string = CString.fromString(" Štefica i Marko ");
 
        assertEquals("Expected that they are the same.", 0, " Štefica".compareTo(string.substring(0, 8).toString()));
        assertEquals("Expected that they are the same.", 0, "Marko ".compareTo(string.substring(11, 17).toString()));
        assertEquals("Expected that they are the same.", 0, "ica".compareTo(string.substring(5, 8).toString()));
        assertEquals("Expected that they are the same.", 0, " ".compareTo(string.substring(0, 1).toString()));
    }
 
    @Test(expected = NullPointerException.class)
    public void testAllWithNullValue() {
        // must throw!
        CString.fromString("Marko").add(null);
    }
 
    @Test
    public void testAllWithValidArgumetns() {
        CString string1 = CString.fromString("Štefica").add(CString.fromString(" i "));
        CString string2 = CString.fromString("Marko");
 
        assertEquals("Expected 'Štefica i '", "Štefica i ", string1.toString());
        assertEquals("Expected 'Štefica i Marko'.", "Štefica i Marko", string1.add(string2).toString());
    }
 
    @Test
    public void testReplaceAllWithCharsAsArguments() {
        CString string1 = CString.fromString("# Štefica# i Marko # se vole. # #");
        CString string2 = string1.replaceAll('#', '*');
        CString string3 = string2.replaceAll('$', '*');
        CString string4 = string3.replaceAll(' ', '$');
 
        assertEquals("I should be full of stars.", "* Štefica* i Marko * se vole. * *", string2.toString());
        assertEquals("I should be also full of stars.", "* Štefica* i Marko * se vole. * *", string3.toString());
        assertEquals("I should be also full of stars and dollaz.", "*$Štefica*$i$Marko$*$se$vole.$*$*",
                string4.toString());
    }
 
    @Test(expected = NullPointerException.class)
    public void testReplaceAllWithNullAsValue1() {
        // must throw!
        CString.fromString("Štefica i Marko šetaju se šumom.").replaceAll(null, CString.fromString("zeko"));
 
    }
 
    @Test(expected = NullPointerException.class)
    public void testReplaceAllWithNullAsValue2() {
        // must throw!
        CString.fromString("Štefica i Marko šetaju se šumom.").replaceAll(CString.fromString("Marko"), null);
    }
 
    @Test(expected = NullPointerException.class)
    public void testReplaceAllWithNullAsValue3() {
        // must throw!
        CString.fromString("Štefica i Marko šetaju se šumom.").replaceAll(null, null);
    }
 
    @Test
    public void testReplaceAllWithCStringsAsArguments() {
        CString string1 = CString.fromString("Štefica i Marko šetaju šumom.\n" + "Marko i Štefica drže se za ruke.\n"
                + "Štefici se sviđa Marko.\n" + "Marku se sviđa Štefica.");
        CString result1 = string1.replaceAll(CString.fromString("Štefica"), CString.fromString("Marica"));
        CString result2 = string1.replaceAll(CString.fromString("Marko"), CString.fromString("Ivica"));
        CString result3 = string1.replaceAll(CString.fromString("medvjed"), CString.fromString("zeko"));
 
        CString compare1 = CString.fromString("Marica i Marko šetaju šumom.\n" + "Marko i Marica drže se za ruke.\n"
                + "Štefici se sviđa Marko.\n" + "Marku se sviđa Marica.");
        CString compare2 = CString.fromString("Štefica i Ivica šetaju šumom.\n" + "Ivica i Štefica drže se za ruke.\n"
                + "Štefici se sviđa Ivica.\n" + "Marku se sviđa Štefica.");
 
        assertEquals("We should be the same.", 0, result1.toString().compareTo(compare1.toString()));
        assertEquals("We should be the same.", 0, result2.toString().compareTo(compare2.toString()));
        assertEquals("We should be the same.", 0, result3.toString().compareTo(string1.toString()));
    }
    
    @Test
	public void testToCharArray2(){
    	CString string = CString.fromString("Testiranje programa");
		CString sub = string.substring(4, 19);
		assertEquals(sub.length(), 15);		
	}

}
