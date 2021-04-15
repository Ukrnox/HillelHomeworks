package org.hillel;

import org.junit.Test;

import static org.hillel.IntToRoman.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class IntToRomanTest
{
    private static final int romanXXIII = 23;
    private static final int romanCXCII = 192;
    private static final int romanDLIV = 554;
    private static final int romanCMXLIX = 949;
    private static final int romanMCDXCII = 1492;
    private static final String roman23 = "XXIII";
    private static final String roman192 = "CXCII";
    private static final String roman554 = "DLIV";
    private static final String roman949 = "CMXLIX";
    private static final String roman1492 = "MCDXCII";

    @Test
    public void testIntToRoman_Exception()
    {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> intToRoman(4000));
        assertEquals("No numbers bigger than 3999!", exception.getMessage());
    }

    @Test
    public void testIntToRoman_I()
    {
        assertEquals("Expected is 'I'", "I", intToRoman(1));
    }

    @Test
    public void testIntToRoman_V()
    {
        assertEquals("Expected is 'V'", "V", intToRoman(5));
    }

    @Test
    public void testIntToRoman_VI()
    {
        assertEquals("Expected is 'VI'", "VI", intToRoman(6));
    }

    @Test
    public void testIntToRoman_VII()
    {
        assertEquals("Expected is 'VII'", "VII", intToRoman(7));
    }

    @Test
    public void testIntToRoman_VIII()
    {
        assertEquals("Expected is 'VIII'", "VIII", intToRoman(8));
    }

    @Test
    public void testIntToRoman_XXIII()
    {
        assertEquals("Expected is 'XXIII'", roman23, intToRoman(romanXXIII));
    }

    @Test
    public void testIntToRoman_CXC()
    {
        assertEquals("Expected is 'CXCII'", roman192, intToRoman(romanCXCII));
    }

    @Test
    public void testIntToRoman_DLIV()
    {
        assertEquals("Expected is 'DLIV'", roman554, intToRoman(romanDLIV));
    }

    @Test
    public void testIntToRoman_CMXLIX()
    {
        assertEquals("Expected is 'CMXLIX'", roman949, intToRoman(romanCMXLIX));
    }

    @Test
    public void testIntToRoman_MCDXCII()
    {
        assertEquals("Expected is 'MCDXCII'", roman1492, intToRoman(romanMCDXCII));
    }

    @Test
    public void testIntToRoman2_Exception()
    {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> intToRoman2(4000));
        assertEquals("No numbers bigger than 3999!", exception.getMessage());
    }

    @Test
    public void testIntToRoman2_CXC()
    {
        assertEquals("Expected is 'CXCII'", roman192, intToRoman2(romanCXCII));
    }

    @Test
    public void testIntToRoman2_DLIV()
    {
        assertEquals("Expected is 'DLIV'", roman554, intToRoman2(romanDLIV));
    }

    @Test
    public void testIntToRoman2_CMXLIX()
    {
        assertEquals("Expected is 'CMXLIX'", roman949, intToRoman2(romanCMXLIX));
    }

    @Test
    public void testIntToRoman2_MCDXCII()
    {
        assertEquals("Expected is 'MCDXCII'", roman1492, intToRoman2(romanMCDXCII));
    }

    @Test
    public void testRomanToInt_192()
    {
        assertEquals("Expected is '192'", romanCXCII, romanToInt(roman192));
    }

    @Test
    public void testRomanToInt_554()
    {
        assertEquals("Expected is '554'", romanDLIV, romanToInt(roman554));
    }

    @Test
    public void testRomanToInt_949()
    {
        assertEquals("Expected is '949'", romanCMXLIX, romanToInt(roman949));
    }

    @Test
    public void testRomanToInt_1492()
    {
        assertEquals("Expected is '1492'", romanMCDXCII, romanToInt(roman1492));
    }
}